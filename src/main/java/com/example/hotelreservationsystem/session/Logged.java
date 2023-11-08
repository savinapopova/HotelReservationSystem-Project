package com.example.hotelreservationsystem.session;

import com.example.hotelreservationsystem.model.entity.Person;

public class Logged<U extends Person> {

    private long id;

    private String username;

    public void login(Person person) {
        this.id = person.getId();
        this.username = person.getUsername();
    }

    public void logout() {
        this.id = 0;
        this.username = null;
    }

    public long getId() {
        return id;
    }

    public Logged<U> setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Logged<U> setUsername(String username) {
        this.username = username;
        return this;
    }
}
