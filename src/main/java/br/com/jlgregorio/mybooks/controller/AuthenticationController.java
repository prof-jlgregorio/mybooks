package br.com.jlgregorio.mybooks.controller;

import br.com.jlgregorio.mybooks.repository.UserRepository;
import br.com.jlgregorio.mybooks.security.AccountCredentialsVO;
import br.com.jlgregorio.mybooks.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO credentials){
        try {
            var userName = credentials.getUserName();
            var passwd = credentials.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, passwd));

            var user = userRepository.findByUserName(userName);
            var token = "";

            if(user != null){
                token = jwtTokenProvider.createToken(userName, user.getRoles());
            } else {
                throw new UsernameNotFoundException("User name " + userName + " not found!");
            }

            Map<Object, Object> model = new HashMap<>();

            model.put("userName", userName);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e ){
            throw new BadCredentialsException("Invalid UserName and/or Password!");
        }
    }


}
