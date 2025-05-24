package com.springboot.webprogrammingmp.refrigerator.DTO;

import com.springboot.webprogrammingmp.refrigerator.Entity.Ingredient;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Slf4j
public class IngredientForm {

    private Long id;
    private String ingredientName;
    private int quantity;
    private String date;
    private String expiryDate;
    private String memo;

    public Ingredient toEntity(){
        return new Ingredient(id, ingredientName, quantity, date, expiryDate, memo);
    }
    public void logInfo(){
        log.info("ingredientName: {}, quantity: {}, date: {}, expiryDate: {}, memo: {}", ingredientName, quantity, date, expiryDate, memo);
    }
}
