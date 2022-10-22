package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.RecipeDto;
import com.example.registrationlogindemo.entity.Recipe;

import com.example.registrationlogindemo.repository.RecipeRepository;
import com.example.registrationlogindemo.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveRecipe(RecipeDto recipeDto){
        Recipe recipe = new Recipe();
        recipe.setName(recipeDto.getName());
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setInstructions(recipeDto.getInstructions());
        recipe.setPrepTime(recipeDto.getPrepTime());
        recipe.setCookTime(recipeDto.getCookTime());

        recipeRepository.save(recipe);
    }
    @Override
    public Recipe findByName(String name) {
        return recipeRepository.findByName(name);
    }

    @Override
    public List<RecipeDto> findAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map((recipe) -> convertEntityToDto(recipe))
                .collect(Collectors.toList());
    }

    private RecipeDto convertEntityToDto(Recipe recipe){
        RecipeDto recipeDto = new RecipeDto();

        recipeDto.setName(recipe.getName());
        recipeDto.setIngredients(recipe.getIngredients());
        recipeDto.setInstructions(recipe.getInstructions());
        recipeDto.setPrepTime(recipe.getPrepTime());
        recipeDto.setCookTime(recipe.getCookTime());
        return recipeDto;
    }
}
