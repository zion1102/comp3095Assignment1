package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.RecipeDto;
import com.example.registrationlogindemo.entity.Recipe;

import java.util.List;

public interface RecipeService {

    void saveRecipe(RecipeDto recipeDto);

    Recipe findByName(String name);

    List<RecipeDto> findAllRecipes();

}
