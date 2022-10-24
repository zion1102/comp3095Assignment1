package ca.gbc.comp3095.recipeapp.service;


/**********************************************************************************
 * Project: < My Recipe App >
 * Assignment: < Assignment #1 >
 * Author(s): < Zion Henry, Mohsen Yahya>
 * Student Number: < 101232420, 101241827 >
 * Description: <Recipe Service. Allows for custom query calls to recipe table.>
 *********************************************************************************/


import ca.gbc.comp3095.recipeapp.dto.RecipeDto;
import ca.gbc.comp3095.recipeapp.entity.Recipe;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RecipeService {

    void saveRecipe(RecipeDto recipeDto);

    Recipe findByName(String name, Pageable pageable);

    List<RecipeDto> findAllRecipes();



}
