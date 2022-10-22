package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findByName(String name);

}
