package br.com.jlgregorio.mybooks.security;

import java.io.Serializable;

public class AccountCredentialsVO implements Serializable {

    private String userName;
    private String password;

    public AccountCredentialsVO() {
    }

    public AccountCredentialsVO(String userName, String passwork) {
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
