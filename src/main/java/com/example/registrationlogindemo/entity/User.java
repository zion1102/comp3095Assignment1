package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.style.ToStringCreator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String firstName;

    @Column(nullable=false)
    private String lastName;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @OrderBy("name")
    private List<Recipe> recipes = new ArrayList<>();

    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    public void addRecipe(Recipe recipe){
        if(recipe.isNew()){
            getRecipes().add(recipe);
        }
    }

    public Recipe getRecipe(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (Recipe recipe : getRecipes()) {
            if (!ignoreNew || !recipe.isNew()) {
                String compName = recipe.getName();
                compName = compName == null ? "" : compName.toLowerCase();
                if (compName.equals(name)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    public boolean isNew() {
        return this.id == null;
    }


    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", this.getId()).append("new", this.isNew())
                .append("lastName", this.getLastName()).append("firstName", this.getFirstName())
                .append("email", this.email).toString();
    }


}
