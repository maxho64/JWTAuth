package com.example.jwtauth.controller;

import com.example.jwtauth.entity.Artist;
import com.example.jwtauth.service.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("artist")
public class ArtistController {

    private final ArtistService service;

    public ArtistController(ArtistService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Iterable<Artist> getAll(){
        return service.getAll();
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Iterable<Artist> getPage(@RequestParam(name="page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name="limit", required = false, defaultValue = "10") int limit){
        return service.getPage(page - 1, limit);
    }

    @GetMapping("/sorted")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Iterable<Artist> getSorted(){
        return service.getSorted();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Artist> save(@RequestBody Artist artist){
        service.save(artist);
        return new ResponseEntity<>(artist, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> update(@PathVariable long id,
                                         @RequestBody Artist artist){
        Optional<Artist> optionalArtist = service.getById(id);
        if(optionalArtist.isEmpty()){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        Artist dbArtist = optionalArtist.get();
        dbArtist.setName(artist.getName());
        service.update(dbArtist);
        return new ResponseEntity<>("Entity was updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable long id){
        Optional<Artist> optionalArtist = service.getById(id);
        if(optionalArtist.isEmpty()){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        Artist dbArtist = optionalArtist.get();
        service.delete(dbArtist);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
