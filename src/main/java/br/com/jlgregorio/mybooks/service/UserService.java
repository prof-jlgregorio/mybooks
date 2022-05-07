package br.com.jlgregorio.mybooks.service;

import br.com.jlgregorio.mybooks.model.UserModel;
import br.com.jlgregorio.mybooks.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService  {

    @Autowired
    IUserRepository repository;

    public Optional<UserModel> findById(long id){
        return repository.findById(id);
    }

    public List<UserModel> findAll(){
        return repository.findAll();
    }

    public UserModel save(UserModel user){
        String passwd = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(passwd);
        return repository.save(user);
    }

    public Optional<UserModel> update(UserModel user){
        Optional<UserModel> found = findById(user.getId());
        if(found.isPresent()){
            found.get().setFullName(user.getFullName());
            found.get().setUserName(user.getUserName());
            found.get().setPermissions(user.getPermissions());
            found.get().setAccountNonExpired(user.isAccountNonExpired());
            found.get().setAccountNonLocked(user.isAccountNonLocked());
            found.get().setCredentialsNonExpired(user.isCredentialsNonExpired());
            found.get().setEnabled(user.isEnabled());
        }
        return found;
    }

    public void delete(long id){
        Optional<UserModel> found = findById(id);
        if(found.isPresent()){
            repository.delete(found.get());
        }
    }

}
