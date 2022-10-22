package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "ingredients")
    private String ingredients;

    @Column(name = "prep_time")
    private Long prepTime;

    @Column(name = "cook_time")
    private Long cookTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Long getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Long prepTime) {
        this.prepTime = prepTime;
    }

    public Long getCookTime() {
        return cookTime;
    }

    public void setCookTime(Long cookTime) {
        this.cookTime = cookTime;
    }
    public boolean isNew() {
        return this.id == null;
    }
}
