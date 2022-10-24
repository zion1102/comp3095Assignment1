package ca.gbc.comp3095.recipeapp.dto;

/**********************************************************************************
 * Project: < My Recipe App >
 * Assignment: < Assignment #1 >
 * Author(s): < Zion Henry, Mohsen Yahya>
 * Student Number: < 101232420, 101241827 >
 * Description: <Recipe Dto Structure>
 *********************************************************************************/

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String instructions;

    @NotEmpty
    private String ingredients;

    @NotEmpty
    private Long prepTime;

    @NotEmpty
    private Long cookTime;



}
