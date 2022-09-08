package com.example.jwtauth.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artist_generator")
    @SequenceGenerator(name = "artist_generator", sequenceName = "artist_id_seq", allocationSize = 1)
    @Column(name = "artistId")
    private long artistId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Album> albumEntityList;

    public List<Album> getAlbumEntityList() {
        return albumEntityList;
    }

    public void setAlbumEntityList(List<Album> albumEntityList) {
        this.albumEntityList = albumEntityList;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", name='" + name + '\'' +
                '}';
    }
}
