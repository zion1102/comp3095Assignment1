package ca.gbc.comp3095.recipeapp.repository;

/**********************************************************************************
 * Project: < My Recipe App >
 * Assignment: < Assignment #1 >
 * Author(s): < Zion Henry, Mohsen Yahya>
 * Student Number: < 101232420, 101241827 >
 * Description: <Recipe Repository. Allows Crud operations to be performed on entities.>
 *********************************************************************************/

import ca.gbc.comp3095.recipeapp.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findByName(String name);

}
