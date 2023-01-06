package com.asherbakov.springweb.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Ingredient {
    private String name;
    private int weight;
    private String measureUnit;

    public Ingredient(String name, int weight, String measureUnit) {
        setName(name);
        setWeight(weight);
        setMeasureUnit(measureUnit);
    }

    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Не задано название ингредиента.");
        }
    }

    public void setWeight(int weight) {
        if (weight > 0) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Количество ингредиентов не задано, либо задано не корректно.");
        }
    }

    public void setMeasureUnit(String measureUnit) {
        if (measureUnit != null && !measureUnit.isBlank()) {
            this.measureUnit = measureUnit;
        } else {
            throw new IllegalArgumentException("Не задана единица измерения.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %s %s", name, weight, measureUnit);
    }
}
