package sst.bank.main.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import sst.bank.model.Budget;
import sst.bank.model.Category;
import sst.bank.model.container.BankBudget;
import sst.bank.model.container.BankContainer;
import sst.bank.model.container.BankContainer.CategoryName;
import sst.common.file.output.OutputFile;

public class MakeCategoryProperties {

    private static final String CATEGORIES_PROPERTIES = "src" + File.separator + "main" + File.separator + "resources"
	    + File.separator + "categories.properties";

    public static void main(String[] args) {
	new MakeCategoryProperties().run();

    }

    private void run() {
	try {
	    OutputFile output = new OutputFile(CATEGORIES_PROPERTIES);
	    ArrayList<String> catList = new ArrayList<>();
	    ArrayList<CategoryName> catListEnum = new ArrayList<>();
	    CategoryName[] categories = CategoryName.values();
	    for (int i = 0; i < categories.length; i++) {
		catList.add(categories[i].name());
		catListEnum.add(categories[i]);
	    }
	    output.println("CATEGORIES=" + String.join(",", catList));

	    catListEnum.stream().forEach(c -> {
		printCategory(output, c);
	    });
	    output.println("");

	} catch (IOException e) {
	    e.printStackTrace();
	    System.exit(-1);
	}
    }

    private Object printCategory(OutputFile output, CategoryName c) {
	try {
	    output.println("");
	    Category category = BankContainer.me().category(c);

	    output.println(category.getName().name() + ".NAME=" + category.getName().name());
	    output.println(category.getName().name() + ".LABEL=" + category.getLabel());
	    output.println(category.getName().name() + ".STYLE=" + category.getStyle());
	    output.println(category.getName().name() + ".TYPE=" + category.getType());

	    Budget bb = BankBudget.me().get(c);
	    output.println(category.getName().name() + ".BUDGET.AMOUT=" + bb.getAmount());
	    output.println(category.getName().name() + ".BUDGET.FREQUENCYTYPE=" + bb.getBudgetFrequencyType().name());
	    output.println(category.getName().name() + ".BUDGET.TYPE=" + bb.getBudgetType().name());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }
}
