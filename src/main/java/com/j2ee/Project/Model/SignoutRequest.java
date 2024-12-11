package com.j2ee.Project.Model;

public class SignoutRequest {
    String token;


    @Override
    public String toString() {
        return "SignoutRequest{" +
                "token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SignoutRequest() {
    }

    public SignoutRequest(String token) {
        this.token = token;
    }
}
