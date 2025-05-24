package com.springboot.webprogrammingmp.refrigerator.Repository;

import com.springboot.webprogrammingmp.refrigerator.Entity.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    @Override
    List<Ingredient> findAll();
}
