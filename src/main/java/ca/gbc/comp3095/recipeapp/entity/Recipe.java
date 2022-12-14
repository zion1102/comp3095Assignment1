package ca.gbc.comp3095.recipeapp.entity;

/**********************************************************************************
 * Project: < My Recipe App >
 * Assignment: < Assignment #1 >
 * Author(s): < Zion Henry, Mohsen Yahya>
 * Student Number: < 101232420, 101241827 >
 * Description: <Recipe Model Structure>
 *********************************************************************************/

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Recipe() { }

    public Recipe(String name, String instructions, String ingredients,
                  Long prepTime, Long cookTime, User user) {
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


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
