package sst.bank.model;

import lombok.Data;
import sst.bank.model.container.BankContainer.CategoryName;

@Data
public class Category implements Comparable<Category> {
    private CategoryName name;
    private String label;
    private String style;

    public Category(CategoryName name, String label, String style) {
	super();
	this.label = label;
	this.style = style;
	this.name = name;
    }

    @Override
    public int compareTo(Category o) {
	return this.label.compareTo(o.label);
    }
}
