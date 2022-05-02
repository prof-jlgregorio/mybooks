package br.com.jlgregorio.mybooks.service;

import br.com.jlgregorio.mybooks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
