package br.com.jlgregorio.mybooks.controller;

import br.com.jlgregorio.mybooks.model.BookModel;
import br.com.jlgregorio.mybooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public BookModel findById(@PathVariable long id){
        return service.findById(id);
    }

    @GetMapping(value = "/", produces = {"application/json", "application/xml"})
    public List<BookModel> findAll(){
        return  service.findAll();
    }

    @GetMapping(value = "/find", produces = {"application/json", "application/xml"})
    public List<BookModel> findByTitle(@RequestParam Optional<String> title,
                                       @RequestParam Optional<String> authorName){
        List<BookModel> result = new ArrayList<>();
        if(title.isPresent()){
            result = service.findByTitle(title.get());
        }
        if(authorName.isPresent()){
            result = service.findByAuthor(authorName.get());
        }
        return result;
    }

    @PostMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public BookModel save(@RequestBody BookModel model){
        return service.save(model);
    }

    @PutMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public BookModel update(@RequestBody BookModel model){
        return service.update(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }



}
