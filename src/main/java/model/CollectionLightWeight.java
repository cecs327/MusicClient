package model;

public class CollectionLightWeight {// extends Collection

    // A container to hold all json objects related to the same releaseName id

    private String artistName;
    private String songName;
    private String releaseName;
    private long idNum;

    public CollectionLightWeight() {
    }

    public CollectionLightWeight(long id, String songName, String artistName, String releaseName) {
        this.releaseName = releaseName;
        this.artistName = artistName;
        this.songName = songName;
        this.idNum = id;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSongTitle() {
        return songName;
    }

    public long getId() {
        return idNum;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setId(long id) {
        this.idNum = id;
    }
}

