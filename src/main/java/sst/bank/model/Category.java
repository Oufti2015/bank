package sst.bank.model;

import lombok.Data;
import sst.bank.model.container.BankContainer.CategoryName;

@Data
public class Category implements Comparable<Category> {
    public enum CategoryType {
	POSITIF, NEGATIF
    };

    private CategoryName name;
    private String label;
    private String style;
    private CategoryType type = CategoryType.POSITIF;
    private Budget budget;

    public Category(CategoryName name, String label, String style) {
	super();
	this.label = label;
	this.style = style;
	this.name = name;
    }

    public Category(CategoryName name, String label, String style, CategoryType type) {
	super();
	this.name = name;
	this.label = label;
	this.style = style;
	this.type = type;
    }

    @Override
    public int compareTo(Category o) {
	return this.label.compareTo(o.label);
    }

    public boolean isNegatif() {
	return getType().equals(CategoryType.NEGATIF);
    }
}
