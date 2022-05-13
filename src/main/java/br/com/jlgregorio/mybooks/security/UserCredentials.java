package br.com.jlgregorio.mybooks.security;

import java.io.Serializable;

public class UserCredentials implements Serializable {

    private String userName;
    private String password;

    public UserCredentials() {
    }

    public UserCredentials(String userName, String passwork) {
        this.userName = userName;
        this.password = passwork;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
