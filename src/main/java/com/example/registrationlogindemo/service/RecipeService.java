package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.RecipeDto;
import com.example.registrationlogindemo.entity.Recipe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RecipeService {

    void saveRecipe(RecipeDto recipeDto);

    Recipe findByName(String name, Pageable pageable);

    List<RecipeDto> findAllRecipes();



}
