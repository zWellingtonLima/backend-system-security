package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.entities.Users;
import com.group1.gestao_seguranca.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UsersRepository usersRepo;

    @PostMapping
    public Boolean createUser(@RequestBody Users user) {
        usersRepo.save(user);

        return true;
    }

    @GetMapping
    public List<Users> getUsers(){
        return usersRepo.findAll();
    }
}
