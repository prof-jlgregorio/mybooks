package br.com.jlgregorio.mybooks.service;

import br.com.jlgregorio.mybooks.exception.NotFoundException;
import br.com.jlgregorio.mybooks.model.AuthorModel;
import br.com.jlgregorio.mybooks.repository.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private IAuthorRepository repository;

    public AuthorModel findById(long id){
        return repository.findById(id).orElseThrow(() -> new NotFoundException(null));
    }

    public List<AuthorModel> findAll(){
        return repository.findAll();
    }

    public List<AuthorModel> findByName(String name){
        return repository.findByName(name);
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
