package com.example.quizz5;

//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;


public class Flight {
    private Long ID;
    private String direction;
    private LocalDate date;
    private int number;
    private float price;

    public Flight(String direction, LocalDate date, int number, float price) {
        this.direction = direction;
        this.date = date;
        this.number = number;
        this.price = price;
    }

    public Flight(String direction, int number, float price) {
        this.direction = direction;
        this.number = number;
        this.price = price;
    }

    public Long getID() {
        return ID;
    }

    public String getDirection() {
        return direction;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getNumber() {
        return number;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "ID=" + ID +
                ", direction='" + direction + '\'' +
                ", date=" + date +
                ", number=" + number +
                ", price=" + price +
                '}';
    }
}
