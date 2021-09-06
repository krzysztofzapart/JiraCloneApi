package pl.kzapart.todoList.RESTapi.email;

import org.springframework.stereotype.Component;


public interface EmailSender {
    void send(String to, String email);
}