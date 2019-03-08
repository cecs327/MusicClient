package Gui.PlaylistList;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import data.UserSession;
import model.CollectionLightWeight;
import model.Playlist;
import model.Profile;
import rpc.CECS327InputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class PlaylistListModel {

    private HashMap<String, Playlist> playlists = new HashMap<>();

    public HashMap<String, Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(CECS327InputStream is) throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            String playlistName = reader.nextName();
            Playlist p = new Playlist(playlistName);
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                reader.nextName();
                p.addToPlaylist(gson.fromJson(reader, CollectionLightWeight.class));
                reader.endObject();
            }
            reader.endArray();
            reader.endObject();
            if (p != null) playlists.put(playlistName, p);

        }
        reader.endArray();
        UserSession.getCurrentSession().setUserProfile(new Profile(playlists));
    }

    /**
     * Adds the Playlist to the HashMap of all Playlists
     *
     * @param playlist - Playlist object to be added
     */
    void addPlaylist(Playlist playlist) {
        if (playlist != null) playlists.put(playlist.getName(), playlist);
        // TODO: Tell server to add a new playlist
    }

    /**
     * Deletes the Playlist from the HashMap of all Playlists
     *
     * @param playlist - Playlist object to be deleted
     */
    void deletePlaylist(Playlist playlist) {
        playlists.remove(playlist.getName());
        // TODO: Tell server to delete the Playlist
    }
}
