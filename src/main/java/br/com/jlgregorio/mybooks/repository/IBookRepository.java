package br.com.jlgregorio.mybooks.repository;

import br.com.jlgregorio.mybooks.model.BookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends JpaRepository<BookModel, Long> {

//    @Query(value = "SELECT * FROM book ORDER BY title", nativeQuery = true)
//    public List<BookModel> findAll();

    Page<BookModel> findByTitleContainingIgnoreCase(String title, Pageable pageable);

//    @Query(value = "SELECT b.* from book b, author a WHERE b.author_id = a.id AND " +
//            " UPPER(a.name) LIKE UPPER(:authorName) ORDER BY title", nativeQuery = true)
//    public List<BookModel> findByAuthorName(@Param("authorName") String authorName);

    @Query(value = "SELECT b.* from book b, author a WHERE b.author_id = a.id AND  UPPER(a.name) LIKE UPPER(:authorName) ORDER BY title", nativeQuery = true)
    public Page<BookModel> findByAuthorName(@Param("authorName") String authorName,
                                            Pageable pageable);



}
