package com.springboot.webprogrammingmp.Repository;

import com.springboot.webprogrammingmp.Entity.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    @Override
    List<Ingredient> findAll();
    List<Ingredient> findAllByOrderByCreatedAtDesc();
}
