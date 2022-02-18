package br.com.jlgregorio.mybooks.repository;

import br.com.jlgregorio.mybooks.model.AuthorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAuthorRepository extends JpaRepository<AuthorModel, Long> {

    @Query(value = "SELECT * FROM author ORDER BY name", nativeQuery = true)
    public List<AuthorModel> findAll();

    @Query(value = "SELECT * FROM author WHERE upper(name) like upper(:name) ORDER BY name ", nativeQuery = true)
    public List<AuthorModel> findByName(@Param("name") String name);

}
