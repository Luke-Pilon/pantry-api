package net.yorkdevsolutions.pantry.entities;

import jakarta.persistence.*;
import net.yorkdevsolutions.pantry.unit_conversion.MeasurementType;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String imageUrl;

    private MeasurementType measurementType;

    private String measurementName;

    private Long caloriesPerUnit;

    private double unitsAvailable;

    public Item() {
    }

    public Item(String name) {
        this.name = name;
        this.imageUrl = null;
        this.measurementType = MeasurementType.UNIT;
        this.measurementName = "";
        this.caloriesPerUnit = 0L;
        this.unitsAvailable = 0;
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

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public Long getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    public void setCaloriesPerUnit(Long caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
    }

    public double getUnitsAvailable() {
        return unitsAvailable;
    }

    public void setUnitsAvailable(double unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }

    public String getMeasurementName() {
        return measurementName;
    }

    public void setMeasurementName(String measurementName) {
        this.measurementName = measurementName;
    }
}
