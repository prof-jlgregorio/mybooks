package br.com.jlgregorio.mybooks.controller;

import br.com.jlgregorio.mybooks.exception.NotFoundException;
import br.com.jlgregorio.mybooks.model.AuthorModel;
import br.com.jlgregorio.mybooks.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author/v1")
public class AuthorController {

    @Autowired
    AuthorService service;

    @GetMapping(produces = {"application/json", "application/xml"})
    public List<AuthorModel> findAll(){
        return service.findAll();
    }

    @GetMapping(value = "/find/{name}", produces = {"application/json", "application/xml"})
    public List<AuthorModel> findByName(@PathVariable("name") String name){
        return service.findByName("%" + name + "%" );
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public AuthorModel findById(@PathVariable("id") long id){
        return service.findById(id);
    }

    @PostMapping(produces = {"application/xml", "application/json"},
                 consumes = {"application/xml", "application/json"})
    public AuthorModel save(@RequestBody AuthorModel model){
        return service.save(model);
    }

    @PutMapping(produces = {"application/xml", "application/json"},
            consumes = {"application/xml", "application/json"})
    public AuthorModel update(@RequestBody AuthorModel model){
        return  service.update(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
