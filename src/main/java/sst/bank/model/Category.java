package sst.bank.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Category implements Comparable<Category>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public enum CategoryType {
        POSITIF, NEGATIF
    }

    private String name;
    private String label;
    private String style;
    private CategoryType type = CategoryType.POSITIF;
    private Budget budget;
    @SuppressWarnings("unused")
    private boolean negatif;
    private boolean defaultCategory;
    public boolean visa = false;
    private String fxName;

    public Category() {
        super();
    }

    public Category(String name, String label, String style) {
        super();
        this.label = label;
        this.style = style;
        this.name = name;
    }

    public Category(String name, String label, String style, CategoryType type) {
        super();
        this.name = name;
        this.label = label;
        this.style = style;
        this.type = type;
    }

    public Category(String name, String label, String style, CategoryType type, Budget budget,
                    boolean defaultCategory) {
        super();
        this.name = name;
        this.label = label;
        this.style = style;
        this.type = type;
        this.budget = budget;
        this.defaultCategory = defaultCategory;
    }

    @Override
    public int compareTo(Category o) {
        return this.label.compareTo(o.label);
    }

    public boolean isNegatif() {
        return getType().equals(CategoryType.NEGATIF);
    }

    @Override
    public String toString() {
        return this.getFxName();
    }

    public String getFxName() {
        return (null == fxName) ? this.getLabel() : this.fxName;
    }
}
