package com.example.jwtauth.repository;


import com.example.jwtauth.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findArtistByName(String name);

    @Query("SELECT a from Artist a where a.name = ?1")
    Artist getByName(String name);

    @Query(value = "SELECT * from artist a where a.name = ?1", nativeQuery = true)
    Artist getByQueryName(String name);
}
