package net.yorkdevsolutions.pantry.dto;

import net.yorkdevsolutions.pantry.entities.Account;
import net.yorkdevsolutions.pantry.entities.Recipe;

import java.util.HashSet;
import java.util.Set;

public class AccountDTO {
    private Long id;
    private String name;
    private Set<RecipeDTO> recipes;

    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.recipes = new HashSet<>();
        if(account.getRecipes() != null){
            for(Recipe recipe : account.getRecipes()){
                var recipeDTO = new RecipeDTO(recipe);
                this.recipes.add(recipeDTO);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RecipeDTO> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<RecipeDTO> recipes) {
        this.recipes = recipes;
    }
}
