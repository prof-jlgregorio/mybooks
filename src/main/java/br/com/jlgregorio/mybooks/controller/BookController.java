package br.com.jlgregorio.mybooks.controller;

import br.com.jlgregorio.mybooks.model.BookModel;
import br.com.jlgregorio.mybooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book/v1")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public BookModel findById(@PathVariable long id) {
        BookModel book = service.findById(id);
        //..adding HATEOAS Support
        buildEntityLink(book);
        return book;
    }

    @GetMapping(value = "/", produces = {"application/json", "application/xml"})
    public CollectionModel<BookModel> findAll() {
        CollectionModel<BookModel> books = CollectionModel.of(service.findAll());
        //..adding HATEOAS support for each BookModel
        for(final BookModel book : books){
            buildEntityLink(book);
        }
        buildCollectionLink(books);
        return books;
    }

    @GetMapping(value = "/find", produces = {"application/json", "application/xml"})
    public List<BookModel> findByTitle(@RequestParam Optional<String> title,
                                       @RequestParam Optional<String> authorName) {
        List<BookModel> books = new ArrayList<>();
        if (title.isPresent()) {
            books = service.findByTitle(title.get());
        }
        if (authorName.isPresent()) {
            books = service.findByAuthor(authorName.get());
        }
        return books;
    }

    @PostMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public BookModel save(@RequestBody BookModel model) {
        return service.save(model);
    }

    @PutMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public BookModel update(@RequestBody BookModel model) {
        return service.update(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private void buildCollectionLink(CollectionModel<BookModel> books){
        books.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BookController.class).findAll()
        ).withRel(IanaLinkRelations.COLLECTION));
    }

    private void buildEntityLink(BookModel book){
        book.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        BookController.class).findById(book.getId())
        ).withSelfRel());

       if(!book.getCategory().hasLink(IanaLinkRelations.SELF)) {
           Link categoryLink = WebMvcLinkBuilder.linkTo(
                   WebMvcLinkBuilder.methodOn(
                           CategoryController.class).findById(book.getCategory().getId())).withSelfRel();
           book.getCategory().add(categoryLink);
       }

       if(!book.getAuthor().hasLinks()) {
           Link authorLink = WebMvcLinkBuilder.linkTo(
                   WebMvcLinkBuilder.methodOn(
                           AuthorController.class).findById(book.getAuthor().getId())).withSelfRel();
           book.getAuthor().add(authorLink);
       }



    }



}
