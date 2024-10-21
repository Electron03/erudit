package com.game.erudut.DTO;

import org.springframework.stereotype.Component;


public class PlayerRequest {
    private String name;
    public PlayerRequest(String name){
        this.name=name;
    }
    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
