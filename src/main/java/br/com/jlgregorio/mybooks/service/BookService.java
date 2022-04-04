package br.com.jlgregorio.mybooks.service;

import br.com.jlgregorio.mybooks.exception.NotFoundException;
import br.com.jlgregorio.mybooks.model.BookModel;
import br.com.jlgregorio.mybooks.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private IBookRepository repository;

    public Page<BookModel> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Page<BookModel> findByTitle(String title, Pageable pageable){
        return repository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Page<BookModel> findByAuthor(String authorName, Pageable pageable){
        return repository.findByAuthorName("%" + authorName + "%", pageable);
    }

    public BookModel findById(long id){
        return repository.findById(id).orElseThrow(() -> new NotFoundException(null));
    }

    public BookModel save(BookModel model){
        return repository.save(model);
    }

    public BookModel update(BookModel model){
        BookModel found = repository.findById(model.getId()).orElseThrow(() -> new NotFoundException(null));
        found.setTitle(model.getTitle());
        found.setCategory(model.getCategory());
        found.setAuthor(model.getAuthor());
        return  repository.save(model);
    }

    public void delete(long id){
        BookModel found = repository.findById(id).orElseThrow(() -> new NotFoundException(null));
        repository.delete(found);
    }

}
