package net.yorkdevsolutions.pantry.entities;

import jakarta.persistence.*;
import net.yorkdevsolutions.pantry.dto.RecipeDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String imageUrl;
    
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> ingredients;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String instructions;

    @Column(nullable = false)
    private UUID accountId;

    public Recipe() {
    }

    public Recipe(RecipeDTO dto, UUID account){
        this.id = dto.getId();
        this.name = dto.getName();
        this.imageUrl = dto.getImageUrl();
        StringBuilder instructions = new StringBuilder();
        for(String instruction : dto.getInstructions()){
            instructions.append(instruction);
            instructions.append(";");
        }
        this.instructions = instructions.toString();
        this.ingredients = new HashSet<>();
        this.accountId = account;
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

    public Set<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(RecipeIngredient ingredient){
        this.ingredients.add(ingredient);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID account) {
        this.accountId = account;
    }
}
