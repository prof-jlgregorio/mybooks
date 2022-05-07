package br.com.jlgregorio.mybooks.controller;

import br.com.jlgregorio.mybooks.model.UserModel;
import br.com.jlgregorio.mybooks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public Optional<UserModel> findById(@PathVariable("id") long id){
        return service.findById(id);
    }

    @PostMapping
    public UserModel save(@RequestBody UserModel user){
        return service.save(user);
    }




}
