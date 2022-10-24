package ca.gbc.comp3095.recipeapp.service.impl;


/**********************************************************************************
 * Project: < My Recipe App >
 * Assignment: < Assignment #1 >
 * Author(s): < Zion Henry, Mohsen Yahya>
 * Student Number: < 101232420, 101241827 >
 * Description: <Defines custom query calls made in recipe service file. Can convert a regular entity into a Dto>
 *********************************************************************************/


import ca.gbc.comp3095.recipeapp.dto.RecipeDto;
import ca.gbc.comp3095.recipeapp.entity.Recipe;

import ca.gbc.comp3095.recipeapp.repository.RecipeRepository;
import ca.gbc.comp3095.recipeapp.service.RecipeService;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
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
    public Recipe findByName(String name, Pageable pageable) {
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
