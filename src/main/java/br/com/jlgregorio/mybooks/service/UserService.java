package br.com.jlgregorio.mybooks.service;

import br.com.jlgregorio.mybooks.model.User;
import br.com.jlgregorio.mybooks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        var user = repository.findByUserName(userName);
        if(user != null){
            return user;
        } else {
            throw new UsernameNotFoundException("User Name " + userName + " was not found!");
        }
    }

    public Optional<User> findById(long id){
        return repository.findById(id);
    }


}
