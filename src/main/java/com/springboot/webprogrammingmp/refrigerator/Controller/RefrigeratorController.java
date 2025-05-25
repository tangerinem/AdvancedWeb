package com.springboot.webprogrammingmp.refrigerator.Controller;

import com.springboot.webprogrammingmp.api.GeminiApiClient;
import com.springboot.webprogrammingmp.refrigerator.DTO.IngredientForm;
import com.springboot.webprogrammingmp.refrigerator.Entity.Ingredient;
import com.springboot.webprogrammingmp.refrigerator.Repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/refrigerator")
public class RefrigeratorController {
    private final GeminiApiClient apiClient;
    private final IngredientRepository ingredientRepository;
    public RefrigeratorController(IngredientRepository ingredientRepository, GeminiApiClient apiClient) {
        this.ingredientRepository = ingredientRepository;
        this.apiClient = apiClient;
    }

    @GetMapping("/ingredients")
    public String ingredientList(@RequestParam(defaultValue = "latest") String sort, Model model) {
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        List<String> expIngredientList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        LocalDate today = LocalDate.now();

        //정렬 부분
        if("expiryDate".equals(sort)){
            ingredientList.sort(Comparator.comparing((Ingredient i) -> {
                LocalDate exp = LocalDate.parse(i.getExpiryDate(), formatter);
                return exp;
            }));
        }
        else{
            ingredientList.sort(Comparator.comparing((Ingredient i) -> {
                LocalDate date = LocalDate.parse(i.getDate(), formatter);
                return date;
            }));
        }

        //alert 부분
        for(Ingredient i : ingredientList){
            LocalDate ingredientExp = LocalDate.parse(i.getExpiryDate(), formatter);
            if(today.plusDays(10).isAfter(ingredientExp)){
                expIngredientList.add(i.getIngredientName());
            }
        }
        if(!expIngredientList.isEmpty()){
            String expIngredient = String.join(",", expIngredientList);
            model.addAttribute("expIngredient", expIngredient);
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
    @PostMapping("/chat")
    @ResponseBody
    public String handleChatMessage(@RequestParam String message) {
        try {
            System.out.println("Gemini API 호출 시작: " + message);

            String geminiResponse = apiClient.generateText(message);
            geminiResponse = geminiResponse.replaceAll("\n", "<br>");

            System.out.println("Gemini API 호출 완료: " + geminiResponse);

            return geminiResponse;
        } catch (Exception e) {
            return "Gemini API 호출 오류: " + e.getMessage();
        }
    }
}
