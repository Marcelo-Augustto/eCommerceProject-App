package com.example.projeto;

public class Response {
    private String status;
    private String error;
    private String logged;

    public Response(String status, String error, String logged) {
        this.status = status;
        this.error = error;
        this.logged = logged;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String isLogged() {
        return logged;
    }
}
