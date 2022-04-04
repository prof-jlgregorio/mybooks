package br.com.jlgregorio.mybooks.controller;

import br.com.jlgregorio.mybooks.model.AuthorModel;
import br.com.jlgregorio.mybooks.model.BookModel;
import br.com.jlgregorio.mybooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
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

    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<PagedModel<BookModel>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                         PagedResourcesAssembler<BookModel> assembler){
        Pageable pageable = PageRequest.of(page, size);
        Page<BookModel> books = null;
        Link link = null;
        books = service.findAll(pageable);

        link = WebMvcLinkBuilder.linkTo(BookController.class).
                slash("?page="+page+"&size="+size).withRel("query");

        for(final BookModel book : books){
            buildEntityLink(book);
        }

        return new ResponseEntity(assembler.toModel(books), HttpStatus.OK);

    }

    @GetMapping(value = "/find", produces = {"application/json", "application/xml"})
    public ResponseEntity<PagedModel<BookModel>> findByTitle(@RequestParam(value = "title", defaultValue = "%") String title,
                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                                             @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                             PagedResourcesAssembler<BookModel> assembler) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));

        //..creates a page
        Page<BookModel> books = null;
        //..creates a link
        Link link = null;

        books = service.findByTitle(title, pageable);
        //..the link is defined
        link = WebMvcLinkBuilder.linkTo(BookController.class)
                .slash("?title=" + title).withRel("query");

        //..iterate the books to create the links
        for (final BookModel bookModel : books
        ) {
            buildEntityLink(bookModel);
        }
        return new ResponseEntity(assembler.toModel(books), HttpStatus.OK);

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

    private void buildEntityLink(BookModel book) {
        //..add a self link
        book.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        BookController.class).findById(book.getId())
        ).withSelfRel());
        //..add the link of relatioships
        if (!book.getCategory().hasLinks()) {
            Link categoryLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(
                            CategoryController.class).findById(book.getCategory().getId())).withSelfRel();
            book.getCategory().add(categoryLink);
        }
        if (!book.getAuthor().hasLinks()) {
            Link authorLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(
                            AuthorController.class).findById(book.getAuthor().getId())).withSelfRel();
            book.getAuthor().add(authorLink);
        }
    }

}
