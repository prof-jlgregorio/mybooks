package br.com.jlgregorio.mybooks.repository;

import br.com.jlgregorio.mybooks.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long> {

    //..Query methods
    public UserModel findByUserNameEqualsIgnoreCase(String userName);

}
