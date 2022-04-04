package br.com.jlgregorio.mybooks.repository;

import br.com.jlgregorio.mybooks.model.AuthorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAuthorRepository extends JpaRepository<AuthorModel, Long> {

    Page<AuthorModel> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
