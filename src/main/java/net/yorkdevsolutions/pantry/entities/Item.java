package net.yorkdevsolutions.pantry.entities;

import jakarta.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String imageUrl;

    private String unitMeasuredIn;

    private Long caloriesPerUnit;

    private Long unitsAvailable;

    public Item() {
    }

    public Item(String name) {
        this.name = name;
        this.imageUrl = null;
        this.unitMeasuredIn = "gram";
        this.caloriesPerUnit = 0L;
        this.unitsAvailable = 0L;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUnitMeasuredIn() {
        return unitMeasuredIn;
    }

    public void setUnitMeasuredIn(String unitMeasuredIn) {
        this.unitMeasuredIn = unitMeasuredIn;
    }

    public Long getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    public void setCaloriesPerUnit(Long caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
    }

    public Long getUnitsAvailable() {
        return unitsAvailable;
    }

    public void setUnitsAvailable(Long unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }
}
