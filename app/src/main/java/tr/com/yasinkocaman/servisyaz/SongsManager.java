package tr.com.yasinkocaman.servisyaz;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {
    // SDCard Path
    final String MEDIA_PATH = new String("/sdcard/");
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    // Constructor
    public SongsManager() {

    }

    /**
     * Function to read all mp3 files from sdcard
     * and store the details in ArrayList
     */
    public ArrayList<HashMap<String, String>> getPlayList(Context ctx) {
        //  File home = new File(MEDIA_PATH);
        //  File home = Environment.getExternalStorageDirectory();


        //   ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
        String[] STAR = {"*"};
        //Context context = null;
        Cursor cursor;
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] cursor_cols = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION};

        String selection = MediaStore.Audio.Media.IS_MUSIC + "=1";
        //   String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        //cursor = CursorLoader(uri, STAR, selection, null, null);
        cursor = ctx.getContentResolver().query(uri, STAR, selection, null, null);

        Log.d("SONG MANAGER","Yasin KOCAMAN 0");

        if (cursor != null) {
            Log.d("SONG MANAGER","Yasin KOCAMAN 1");

            if (cursor.moveToFirst()) {
                Log.d("SONG MANAGER","Yasin KOCAMAN 2");

                do {
                    String songName = cursor
                            .getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    Log.d("SONG MANAGER","Songname:"+ songName);

                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));


                  /*  String albumName = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    int albumId = cursor
                            .getInt(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));*/

                    HashMap<String, String> song = new HashMap<String, String>();
//                    song.put("songTitle", albumName + " " + songName + "___" + albumId);
                    song.put("songTitle",  songName );
                    song.put("songPath", path);
                    songsList.add(song);

                } while (cursor.moveToNext());


            }

        }


//
//        if (home.listFiles(new FileExtensionFilter()).length > 0) {
//            for (File file : home.listFiles(new FileExtensionFilter())) {
//                HashMap<String, String> song = new HashMap<String, String>();
//                song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
//                song.put("songPath", file.getPath());
//
//                // Adding each song to SongList
//                songsList.add(song);
//            }
//        }
        // return songs list array
        return songsList;
    }

    /**
     * Class to filter files which are having .mp3 extension
     */
    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }
}