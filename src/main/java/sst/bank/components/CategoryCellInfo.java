package sst.bank.components;

import sst.bank.model.Category;
import sst.common.html.table.builders.CellInfo;

public class CategoryCellInfo extends CellInfo {

    public CategoryCellInfo(Category category) {
        super(category.getLabel(), category.getStyle());
    }

}
