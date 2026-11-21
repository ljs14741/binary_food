package com.example.food.controller;

import org.springframework.web.servlet.view.RedirectView;
import org.springframework.http.HttpStatus;
import com.example.food.dto.FoodCategoryDTO;
import com.example.food.dto.FoodDTO;
import com.example.food.entity.Food;
import com.example.food.entity.FoodCategory;
import com.example.food.service.FoodCategoryService;
import com.example.food.service.FoodService;
import com.example.food.service.VisitorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FoodController {
    @Autowired
    private FoodCategoryService foodCategoryService;

    @Autowired
    private FoodService foodService;

    private final VisitorService visitorService;

    // 소개·문의·개인정보는 메인 한 곳에서 관리함 (영구 이동)
    @GetMapping({"/about", "/contact", "/privacy-policy"})
    public RedirectView legalPage(HttpServletRequest request) {
        RedirectView view = new RedirectView("https://binaryworld.kr" + request.getRequestURI());
        view.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return view;
    }

    @GetMapping("/")
    public String foodMain(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<FoodCategoryDTO> categories = foodCategoryService.getAllCategories();
        model.addAttribute("categories", categories);
        visitorService.countVisitIfNeeded(request, response, "food");
        return "food";
    }

    @GetMapping("/food/randomCategory")
    @ResponseBody
    public FoodCategoryDTO getRandomCategory() {
        return foodCategoryService.getRandomCategoryDTO();
    }

    @GetMapping("/food/randomFood")
    @ResponseBody
    public FoodDTO getRandomFood(@RequestParam(required = false) Long categoryId) {
        Food randomFood;
        if (categoryId != null) {
            FoodCategory category = foodCategoryService.getCategoryById(categoryId);
            randomFood = foodService.getRandomFoodByCategory(category);
        } else {
            randomFood = foodService.getRandomFood();
        }
        return foodService.convertToDTO(randomFood);
    }

    @GetMapping("/food/randomLunch")
    @ResponseBody
    public FoodDTO getRandomLunch() {
        Food randomFood = foodService.getRandomFoodByType(Food.FoodType.점심);
        return foodService.convertToDTO(randomFood);
    }

    @GetMapping("/food/randomSnacks")
    @ResponseBody
    public FoodDTO getRandomSnacks() {
        Food randomFood = foodService.getRandomFoodByType(Food.FoodType.술안주);
        return foodService.convertToDTO(randomFood);
    }

    @GetMapping("/food/{categoryId}")
    public String getFoodByCategory(@PathVariable Long categoryId, Model model) {
        FoodCategory category = foodCategoryService.getCategoryById(categoryId);
        List<Food> foods = foodService.getFoodsByCategory(category);
        model.addAttribute("foods", foods);
        return "foodDetail";
    }

    @PostMapping("/food")
    public String addFood(@RequestParam String foodName, @RequestParam Long categoryId) {
        FoodCategory category = foodCategoryService.getCategoryById(categoryId);
        Food food = new Food();
        food.setFoodName(foodName);
        food.setCategory(category);
        foodService.saveFood(food);
        return "redirect:/food";
    }
}