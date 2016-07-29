package sst.bank.model;

import lombok.Getter;
import lombok.Setter;

public class Category implements Comparable<Category> {
    @Getter
    @Setter
    private String label;
    @Getter
    @Setter
    private String style;

    public Category(String label, String style) {
	super();
	this.label = label;
	this.style = style;
    }

    @Override
    public int compareTo(Category o) {
	return this.label.compareTo(o.label);
    }
}
