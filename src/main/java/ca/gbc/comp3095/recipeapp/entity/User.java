package ca.gbc.comp3095.recipeapp.entity;

/**********************************************************************************
 * Project: < My Recipe App >
 * Assignment: < Assignment #1 >
 * Author(s): < Zion Henry, Mohsen Yahya>
 * Student Number: < 101232420, 101241827 >
 * Description: <User model Structure>
 *********************************************************************************/


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.style.ToStringCreator;

import java.util.ArrayList;
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


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Recipe> recipes;

    public Set<Recipe> getRecipes() {
        return this.recipes;
    }

    public void addRecipe(Recipe recipe){
        //if(recipe.isNew()){
            getRecipes().add(recipe);
        //}
    }


    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     * @param name to test
     * @return a pet if pet name is already in use
     */
    public Recipe getRecipe(String name) {
        return getRecipe(name, false);
    }

    /**
     * Return the Pet with the given id, or null if none found for this Owner.
     * @param name to test
     * @return a pet if pet id is already in use
     */
    public Recipe getRecipe(Integer id) {
        for (Recipe recipe : getRecipes()) {
            if (!recipe.isNew()) {
                Integer compId = recipe.getId();
                if (compId.equals(id)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     * @param name to test
     * @return a pet if pet name is already in use
     */
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
