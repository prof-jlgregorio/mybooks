package br.com.jlgregorio.mybooks.service;

import br.com.jlgregorio.mybooks.exception.NotFoundException;
import br.com.jlgregorio.mybooks.model.AuthorModel;
import br.com.jlgregorio.mybooks.repository.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private IAuthorRepository repository;

    public AuthorModel findById(long id){
        return repository.findById(id).orElseThrow(() -> new NotFoundException(null));
    }

//    public List<AuthorModel> findAll(Pageable pageable){
//        var authors = repository.findAll(pageable).getContent();
//        return authors;
//    }

    public Page<AuthorModel> findAll(Pageable pageable){
        var page = repository.findAll(pageable);
        return page;
    }

    public Page<AuthorModel> findByName(String name, Pageable pageable){
        var page = repository.findByNameContainingIgnoreCase(name, pageable);
        return  page;
    }

    public AuthorModel save(AuthorModel model){
        return repository.save(model);
    }

    public AuthorModel update(AuthorModel model){
        AuthorModel found = repository.findById(model.getId()).orElseThrow(() -> new NotFoundException(null));
        found.setName(model.getName());
        found.setGender(model.getGender());
        found.setCountry(model.getCountry());
        return repository.save(found);
    }

    public void delete(long id){
        AuthorModel found = repository.findById(id).orElseThrow(() -> new NotFoundException(null));
        repository.delete(found);
    }


}
