package com.springboot.webprogrammingmp.refrigerator.Controller;

import com.springboot.webprogrammingmp.refrigerator.DTO.IngredientForm;
import com.springboot.webprogrammingmp.refrigerator.Entity.Ingredient;
import com.springboot.webprogrammingmp.refrigerator.Repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/refrigerator")
public class RefrigeratorController {
    private final IngredientRepository ingredientRepository;
    public RefrigeratorController(IngredientRepository ingredientRepository) {this.ingredientRepository = ingredientRepository;}

    @GetMapping("/ingredients")
    public String ingredientList(@RequestParam(defaultValue = "latest") String sort, Model model) {
        List<Ingredient> ingredientList;
        if("expiryDate".equals(sort)){
            ingredientList = ingredientRepository.findAll();
            ingredientList.sort(Comparator.comparing((Ingredient i) -> {
                String exp = i.getExpiryDate();
                return Integer.parseInt(exp.substring(0, 2));
            }).thenComparing(i -> {
                String exp = i.getExpiryDate();
                return Integer.parseInt(exp.substring(3, 5));
            }).thenComparing(i -> {
                String exp = i.getExpiryDate();
                return Integer.parseInt(exp.substring(6));
            }));
        }
        else{
            ingredientList = ingredientRepository.findAllByOrderByCreatedAtDesc();
        }
        model.addAttribute("ingredientList", ingredientList);
        return "Refrigerator/main";
    }

    @GetMapping("/ingredients/add")
    public String add() {
        return "Refrigerator/ingredients";
    }

    @PostMapping("/ingredients/add")
    public String addIngredient(IngredientForm ingredientForm) {
        Ingredient ingredient = ingredientForm.toEntity();
        ingredient.logInfo();

        Ingredient saved = ingredientRepository.save(ingredient);
        saved.logInfo();

        return "redirect:/refrigerator/ingredients";
    }

    @GetMapping("/ingredient/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {

        Ingredient ingredientEntity = ingredientRepository.findById(id).orElse(null);
        model.addAttribute("ingredient", ingredientEntity);

        return "Refrigerator/edit";
    }

    @PostMapping("/ingredients/update")
    public String update(IngredientForm ingredientForm) {
        Ingredient ingredient = ingredientForm.toEntity();
        Ingredient target = ingredientRepository.findById(ingredient.getId()).orElse(null);

        if(target != null) {
            ingredientRepository.save(ingredient);
        }
        return "redirect:/refrigerator/ingredients";
    }
    @GetMapping("/ingredient/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        Ingredient target = ingredientRepository.findById(id).orElse(null);
        if(target != null) {
            ingredientRepository.deleteById(id);
        }
        return "redirect:/refrigerator/ingredients";
    }
}
