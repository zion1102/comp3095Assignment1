package com.example.registrationlogindemo.dto;


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
