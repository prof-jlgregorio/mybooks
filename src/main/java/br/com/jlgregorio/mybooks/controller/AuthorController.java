package br.com.jlgregorio.mybooks.controller;

import br.com.jlgregorio.mybooks.model.AuthorModel;
import br.com.jlgregorio.mybooks.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author/v1")
public class AuthorController {

    @Autowired
    AuthorService service;

    private PagedResourcesAssembler<AuthorModel> pagedResourcesAssembler;

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<PagedModel<AuthorModel>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                           PagedResourcesAssembler<AuthorModel> assembler){

        //add a Direction to sort the results
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        //add a Pageable object to paginate the results
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));


        Page<AuthorModel> authors = service.findAll(pageable);
        for (AuthorModel author : authors){
            buildEntityLink(author);
        }

        return new ResponseEntity(assembler.toModel(authors), HttpStatus.OK);

    }

    @GetMapping(value = "/find/{name}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public CollectionModel<AuthorModel> findByName(@PathVariable("name") String name){
        CollectionModel<AuthorModel> authors = CollectionModel.of(service.findByName("%" + name + "%" ));
        for (AuthorModel author : authors){
            buildEntityLink(author);
        }
        //buildCollectionLink(authors);
        return authors;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public AuthorModel findById(@PathVariable("id") long id){
        AuthorModel author = service.findById(id);
        buildEntityLink(author);
        return author;
    }

    @PostMapping(produces = {"application/xml", "application/json", "application/x-yaml"},
                 consumes = {"application/xml", "application/json", "application/x-yaml"})
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

//    public void buildCollectionLink(CollectionModel<AuthorModel> authors){
//        authors.add(
//                WebMvcLinkBuilder.linkTo(
//                        WebMvcLinkBuilder.methodOn(AuthorController.class).findAll()).withRel(IanaLinkRelations.COLLECTION)
//                );
//    }

}
