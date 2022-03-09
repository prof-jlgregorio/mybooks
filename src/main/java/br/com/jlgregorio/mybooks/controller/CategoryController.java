package br.com.jlgregorio.mybooks.controller;

import br.com.jlgregorio.mybooks.model.CategoryModel;
import br.com.jlgregorio.mybooks.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category/v1")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public CategoryModel findById(@PathVariable long id){
        CategoryModel model =  service.findById(id);
        //create the link before return
        buildEntityLink(model);
        return model;
    }

    @GetMapping(value = "/", produces = {"application/json", "application/xml"})
    public CollectionModel<CategoryModel> findAll(){
        CollectionModel<CategoryModel> categories =  CollectionModel.of(service.findAll());
        for (CategoryModel category : categories){
            buildEntityLink(category);
        }
        buildCollectionLink(categories);
        return categories;
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

    //..converts to model
    private void buildEntityLink(CategoryModel category){
        //..add the link
        category.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(CategoryController.class).findById(category.getId())
                ).withSelfRel()
        );
    }

    private void buildCollectionLink(CollectionModel<CategoryModel> categories){
        categories.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(CategoryController.class).findAll()
                ).withRel(IanaLinkRelations.COLLECTION)
        );
    }


}
