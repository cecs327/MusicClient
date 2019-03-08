//package data;
//
//import model.Collection;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//
//public class Resources {
//    /**
//     * A list of ALL songs (from the Music JSON).
//     */
//    private static List<Collection> musicDatabase;
//
//    /**
//     * A container of references for PLAYABLE songs
//     * (i.e. the song's mp3 file exists in the music directory).
//     */
//    private static HashMap<Integer,String> playableSongs; // key: song release ID, value: mp3 file name
//
//    /**
//     * A dictionary of the user's music library (as Collection objects).
//     */
//    private static HashMap<Integer,Collection> userLibrary; // key: song release ID, value: corresponding Collection object
//
//    /**
//     * The song release IDs present in the music directory.
//     * Used for quick lookup of available music.
//     */
//    private static HashSet<Integer> ownedIDs;
//
//
////    private static List<User> userList;
////    protected static HashMap<String,User> userDictionary;
//
//
//    public static List<Collection> getMusicDatabase() {
//        return musicDatabase;
//    }
//
//    public static void setMusicDatabase(final List<Collection> musicDatabase) {
//        Resources.musicDatabase = musicDatabase;
//    }
//
//    public static HashMap<Integer, String> getPlayableSongs() {
//        return playableSongs;
//    }
//
//    public static void setPlayableSongs(final HashMap<Integer, String> playableSongs) {
//        Resources.playableSongs = playableSongs;
//    }
//
//    public static HashMap<Integer, Collection> getUserLibrary() {
//        return userLibrary;
//    }
//
//    public static void setUserLibrary(final HashMap<Integer, Collection> userLibrary) {
//        Resources.userLibrary = userLibrary;
//    }
//
//    public static HashSet<Integer> getOwnedIDs() {
//        return ownedIDs;
//    }
//
//    public static void setOwnedIDs(final HashSet<Integer> ownedIDs) {
//        Resources.ownedIDs = ownedIDs;
//    }
//}
