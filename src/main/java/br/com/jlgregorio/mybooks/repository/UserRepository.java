package br.com.jlgregorio.mybooks.repository;

import br.com.jlgregorio.mybooks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.userName = :userName")
    User findByUserName(@Param("userName") String userName);




}
