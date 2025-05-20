package com.springboot.webprogrammingmp.DTO;

import com.springboot.webprogrammingmp.Entity.Ingredient;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Slf4j
public class IngredientForm {

    private Long id;
    private String ingredientName;
    private int quantity;
    private String date;
    private String expiryDate;
    private String memo;
    private LocalDateTime createdAt;

    public Ingredient toEntity(){
        return new Ingredient(id, ingredientName, quantity, date, expiryDate, memo, createdAt);
    }
    public void logInfo(){
        log.info("ingredientName: {}, quantity: {}, date: {}, expiryDate: {}, memo: {}, createdAt: {}", ingredientName, quantity, date, expiryDate, memo, createdAt);
    }
}
