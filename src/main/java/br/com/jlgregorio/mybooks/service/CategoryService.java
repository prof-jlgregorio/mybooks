package br.com.jlgregorio.mybooks.service;

import br.com.jlgregorio.mybooks.exception.NotFoundException;
import br.com.jlgregorio.mybooks.model.CategoryModel;
import br.com.jlgregorio.mybooks.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private ICategoryRepository repository;

    public CategoryModel findById(long id){
        return repository.findById(id).orElseThrow(() -> new NotFoundException(null));
    }

//    public List<CategoryModel> findAll(){
//        return repository.findAll();
//    }

    public Page<CategoryModel> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Page<CategoryModel> findByName(String name, Pageable pageable){
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }


    public CategoryModel save(CategoryModel model){
        return repository.save(model);
    }

    public CategoryModel update(CategoryModel model){
        CategoryModel found = repository.findById(model.getId()).orElseThrow(() -> new NotFoundException(null));
        found.setName(model.getName());
        return repository.save(model);
    }

    public void delete(long id){
        CategoryModel found = repository.findById(id).orElseThrow(() -> new NotFoundException(null));
        repository.delete(found);
    }


}
