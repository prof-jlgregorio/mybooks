package br.com.jlgregorio.mybooks.controller;

import br.com.jlgregorio.mybooks.exception.NotFoundException;
import br.com.jlgregorio.mybooks.model.AuthorModel;
import br.com.jlgregorio.mybooks.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author/v1")
public class AuthorController {

    @Autowired
    AuthorService service;

    @GetMapping(produces = {"application/json", "application/xml"})
    public CollectionModel<AuthorModel> findAll(){
        CollectionModel<AuthorModel> authors = CollectionModel.of(service.findAll());
        for (AuthorModel author : authors){
            buildEntityLink(author);
        }
        buildCollectionLink(authors);
        return authors;
    }

    @GetMapping(value = "/find/{name}", produces = {"application/json", "application/xml"})
    public CollectionModel<AuthorModel> findByName(@PathVariable("name") String name){
        CollectionModel<AuthorModel> authors = CollectionModel.of(service.findByName("%" + name + "%" ));
        for (AuthorModel author : authors){
            buildEntityLink(author);
        }
        buildCollectionLink(authors);
        return authors;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public AuthorModel findById(@PathVariable("id") long id){
        AuthorModel author = service.findById(id);
        buildEntityLink(author);
        return author;
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

    public void buildEntityLink(AuthorModel author){
        author.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                        AuthorController.class).findById(author.getId())).withSelfRel()
                );
    }

    public void buildCollectionLink(CollectionModel<AuthorModel> authors){
        authors.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AuthorController.class).findAll()).withRel(IanaLinkRelations.COLLECTION)
                );
    }

}
