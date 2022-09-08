package com.example.jwtauth.service;

import com.example.jwtauth.entity.auth.Role;
import com.example.jwtauth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role findByName(String name){
        return repository.findByName(name);
    }
}
