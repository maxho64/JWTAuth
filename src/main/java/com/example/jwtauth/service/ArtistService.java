package com.example.jwtauth.service;


import com.example.jwtauth.entity.Artist;
import com.example.jwtauth.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    private final ArtistRepository repository;

    @Autowired
    public ArtistService(ArtistRepository repository) {
        this.repository = repository;
    }

    public List<Artist> getAll(){
        return repository.findAll();
    }

    public Optional<Artist> getById(long id){
        return repository.findById(id);
    }


    @Transactional
    public void save(Artist artist){
        repository.save(artist);
    }

    @Transactional
    public void update(Artist artist){
        repository.save(artist);
    }

    @Transactional
    public void delete(Artist artist){
        repository.delete(artist);
    }

    public long count(){ return repository.count();}

    public List<Artist> getPage(int page, int size){
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<Artist> getSorted(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}
