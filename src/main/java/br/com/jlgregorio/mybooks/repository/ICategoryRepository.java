package br.com.jlgregorio.mybooks.repository;

import br.com.jlgregorio.mybooks.model.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryModel, Long> {

    Page<CategoryModel> findByNameContainingIgnoreCase(String name, Pageable pageable);


}
