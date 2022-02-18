package br.com.jlgregorio.mybooks.controller;

import br.com.jlgregorio.mybooks.model.CategoryModel;
import br.com.jlgregorio.mybooks.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category/v1")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public CategoryModel findById(@PathVariable long id){
        return service.findById(id);
    }

    @GetMapping(value = "/", produces = {"application/json", "application/xml"})
    public List<CategoryModel> findAll(){
        return service.findAll();
    }

    @PostMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public CategoryModel save(@RequestBody CategoryModel model){
        return service.save(model);
    }

    @PutMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public CategoryModel update(@RequestBody CategoryModel model){
        return service.update(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
