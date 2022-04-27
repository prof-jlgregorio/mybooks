package br.com.jlgregorio.mybooks.controller;

import br.com.jlgregorio.mybooks.model.CategoryModel;
import br.com.jlgregorio.mybooks.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/category/v1")
public class CategoryController {

    @Autowired
    private CategoryService service;
    private Optional<CategoryModel> category;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public Optional<CategoryModel> findById(@PathVariable long id){
        Optional<CategoryModel> optional = service.findById(id);
        if(optional.isPresent()){
            buildHateoas(optional.get());
        }
        return optional;
    }

//    @GetMapping(produces = {"application/json", "application/xml"})
//    public CollectionModel<CategoryModel> findAll(){
//        CollectionModel<CategoryModel> categories =  CollectionModel.of(service.findAll());
//        for (CategoryModel category : categories){
//            buildEntityLink(category);
//        }
//        buildCollectionLink(categories);
//        return categories;
//    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<PagedModel<CategoryModel>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "`0") int size,
                                                             @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                             PagedResourcesAssembler<CategoryModel> assembler){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
        Page<CategoryModel> categories = null;
        categories = service.findAll(pageable);
        for (final CategoryModel categoryModel : categories
        ) {
            buildHateoas(categoryModel);
        }
        return new ResponseEntity(assembler.toModel(categories), HttpStatus.OK);
    }

    public ResponseEntity<PagedModel<CategoryModel>> findByName(@RequestParam(value = "name", defaultValue = "%") String name,
                                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                                @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                PagedResourcesAssembler<CategoryModel> assembler){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
        Page<CategoryModel> categories = null;
        categories = service.findByName(name, pageable);
        for (final CategoryModel categoryModel : categories
        ) {
            buildHateoas(categoryModel);
        }
        return new ResponseEntity(assembler.toModel(categories), HttpStatus.OK);

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
//    private void buildHateoas(Optional<CategoryModel> category){
//        if(category.isPresent()){
//            category.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.
//                    methodOn(CategoryController.class).findById(category.get().getId())).withSelfRel());
//        }
//    }

    private void buildHateoas(CategoryModel category){
            category.add(WebMvcLinkBuilder.linkTo(CategoryController.class).
                    slash(category.getId()).withSelfRel());
    }

//    private void buildCollectionLink(CollectionModel<CategoryModel> categories){
//        categories.add(
//                WebMvcLinkBuilder.linkTo(
//                        WebMvcLinkBuilder.methodOn(CategoryController.class).findAll()
//                ).withRel(IanaLinkRelations.COLLECTION)
//        );
//    }


}
