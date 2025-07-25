package com.example.food.service;

import java.util.List;
import java.util.Random;

import com.example.food.dto.FoodDTO;
import com.example.food.entity.Food;
import com.example.food.entity.FoodCategory;
import com.example.food.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public List<Food> getFoodsByCategory(FoodCategory category) {
        return foodRepository.findByCategory(category);
    }

    public Food getRandomFood() {
        List<Food> foods = foodRepository.findAll();
        Random random = new Random();
        return foods.get(random.nextInt(foods.size()));
    }

    public Food getRandomFoodByCategory(FoodCategory category) {
        List<Food> foods = foodRepository.findByCategory(category);
        Random random = new Random();
        return foods.get(random.nextInt(foods.size()));
    }

    public Food getRandomFoodByType(Food.FoodType foodType) {
        List<Food> foods = foodRepository.findRandomFoodByType(foodType, Food.FoodType.점심과술안주, PageRequest.of(0, 1));
        return foods.isEmpty() ? null : foods.get(0);
    }

    public void saveFood(Food food) {
        foodRepository.save(food);
    }

    public FoodDTO convertToDTO(Food food) {
        return FoodDTO.builder()
                .id(food.getId())
                .foodName(food.getFoodName())
                .foodImg(food.getFoodImg())
                .build();
    }
}