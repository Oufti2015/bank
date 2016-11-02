package sst.bank.events;

import lombok.Data;
import sst.bank.model.Category;

@Data
public class CategoryChangeEvent {
    private Category category;

    public CategoryChangeEvent(Category category) {
	super();
	this.category = category;
    }
}
