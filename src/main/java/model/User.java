package model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    transient public Profile userProfile; // user's profile containing their playlists

    public User() {
    }

    public User(final String username, final String email, final String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, Profile profile) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userProfile = profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Profile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(final Profile userProfile) {
        this.userProfile = userProfile;
    }
}
