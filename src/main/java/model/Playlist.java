package model;

import java.util.ArrayList;

public class Playlist {

    private String id;
    private String name;
    private ArrayList<CollectionLightWeight> songList;

    /**
     * Default constructor for creating a new playlist. Instantiates a new ArrayList for songList.
     *
     * @param id   - ID number to reference the Playlist
     * @param name - Name of the Playlist as given by the User
     */
    public Playlist(String id, String name) {
        this.id = id;
        this.name = name;
        this.songList = new ArrayList<>();
    }

    public Playlist(String name) {
        this.id = "001";
        this.name = name;
        this.songList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CollectionLightWeight> getSongList() {
        return this.songList;
    }

    public boolean addToPlaylist(CollectionLightWeight song) {    // TODO: Change to a Song POJO
        boolean added = false;

        if (song != null) {
            this.songList.add(song);
            added = true;
            System.out.printf("%s added to playlist\n", song.getSongTitle());
        }

        return added;
    }
}
