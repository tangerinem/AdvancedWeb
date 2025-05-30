package com.springboot.webprogrammingmp.refrigerator.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity @ToString @Slf4j @NoArgsConstructor @AllArgsConstructor @Getter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String ingredientName;
    private int quantity;
    private String date;
    private String expiryDate;
    private String memo;


    public Ingredient(String ingredientName, int quantity, String date, String expiryDate, String memo) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.date = date;
        this.expiryDate = expiryDate;
        this.memo = memo;
    }

    public void logInfo(){
        log.info("id: {}, ingredientName: {}, quantity: {}, date: {}, expiryDate: {}, memo: {}", id, ingredientName, quantity, date, expiryDate, memo);
    }


}
