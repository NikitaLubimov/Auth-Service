package ru.gb.fitnessclub.authservice.api;


import lombok.AllArgsConstructor;

import lombok.Data;


public class ClientInfoRequest {

    private String login;

    private  String username;

    private String phone;

    private  String email;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private ClientInfoRequest(Builder builder){
        setLogin(builder.login);
        setUsername(builder.username);
        setPhone(builder.phone);
        setEmail(builder.email);

    }

    public static Builder getBuilder(){
        return new Builder();
    }


    public static final class Builder{
        private String login;

        private  String username;

        private String phone;

        private  String email;


        public Builder login(String val){
            login=val;
            return this;
        }

        public Builder username(String val){
            username=val;
            return this;
        }

        public Builder phone(String val){
            phone=val;
            return this;
        }

        public Builder email(String val){
            email=val;
            return this;
        }

        public ClientInfoRequest build(){
            return new ClientInfoRequest(this);
        }

    }

}
