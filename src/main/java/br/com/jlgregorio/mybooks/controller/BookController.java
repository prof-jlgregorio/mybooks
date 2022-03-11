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
        //..typecast List to CollectionModel
        CollectionModel<BookModel> books = CollectionModel.of(service.findAll());
        //..adding HATEOAS support for each BookModel
        for (final BookModel book : books) {
            buildEntityLink(book);
        }
        //create the link to collection
        buildCollectionLink(books);
        return books;
    }

    @GetMapping(value = "/find", produces = {"application/json", "application/xml"})
    public CollectionModel<BookModel> findByTitleOrAuthor(@RequestParam Optional<String> title,
                                                          @RequestParam Optional<String> authorName) {
        //..creates a list
        List<BookModel> books = new ArrayList<BookModel>();
        //..creates a link
        Link link = null;
        //..if the parameter 'title' is present, perform the search by title
        if (title.isPresent()) {
            books = service.findByTitle(title.get());
            //..the link is defined
            link = WebMvcLinkBuilder.linkTo(BookController.class)
                    .slash("?title="+title.get()).withRel("query");
        }
        //..if the parameter 'authorName' is present, perform the search by authorName
        if (authorName.isPresent()) {
            books = service.findByAuthor(authorName.get());
            //..the link is defined with parameter
            link = WebMvcLinkBuilder.linkTo(BookController.class)
                    .slash("?authorName="+authorName.get()).withRel("query");
        }
        //..iterate the books to create the links
        for (final BookModel bookModel : books
        ) {
            buildEntityLink(bookModel);
        }
        //..create the CollectionModel
        CollectionModel<BookModel> bookCollection = CollectionModel.of(books);
        //..create the link to collection
        buildCollectionLink(bookCollection);
        //..add the link to parametrized method
        bookCollection.add(link);
        //..returns the collection
        return bookCollection;
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

    private void buildCollectionLink(CollectionModel<BookModel> books) {
        books.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BookController.class).findAll()
        ).withRel(IanaLinkRelations.COLLECTION));
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
