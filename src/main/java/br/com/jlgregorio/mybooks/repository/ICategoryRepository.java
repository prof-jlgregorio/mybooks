package br.com.jlgregorio.mybooks.repository;

import br.com.jlgregorio.mybooks.model.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryModel, Long> {

    public Page<CategoryModel> findByNameContainingIgnoreCase(String name, Pageable pageable);


}
