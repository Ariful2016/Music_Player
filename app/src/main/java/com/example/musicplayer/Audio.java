package com.example.musicplayer;

import android.net.Uri;

public class Audio {

    String name;
    Uri audioUri;

    public Audio(String name, Uri audioUri) {
        this.name = name;
        this.audioUri = audioUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getAudioUri() {
        return audioUri;
    }

    public void setAudioUri(Uri audioUri) {
        this.audioUri = audioUri;
    }

    @Override
    public String toString() {
        return "Audio{" +
                "name='" + name + '\'' +
                ", audioUri=" + audioUri +
                '}';
    }
}
