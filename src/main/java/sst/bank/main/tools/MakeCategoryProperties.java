package sst.bank.main.tools;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import sst.bank.activities.LifeCycleInterface;
import sst.bank.config.BankConfiguration;
import sst.bank.main.OuftiBank;
import sst.bank.model.Budget;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;
import sst.common.file.output.OutputFile;

public class MakeCategoryProperties {

    public static void main(String[] args) {
	BankContainer.me();
	MakeCategoryProperties tool = new MakeCategoryProperties();
	LifeCycleInterface.runReadOnlyLifeCyle();
	tool.exportToPropertyFile();
	tool.exportToJSON();
    }

    private void exportToJSON() {
	try {
	    ObjectMapper mapper = new ObjectMapper();

	    // Object to JSON in file
	    mapper.writeValue(new File(BankConfiguration.me().getCategoriesJson()), BankContainer.me().getCategories());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private void exportToPropertyFile() {
	try (OutputFile output = new OutputFile(BankConfiguration.CATEGORIES_PROPERTIES)) {
	    Collection<Category> catList = BankContainer.me().getCategories();
	    output.println("CATEGORIES="
		    + String.join(",", catList.stream().map(c -> c.getName()).collect(Collectors.toList())));

	    catList.stream().forEach(c -> {
		printCategory(output, c);
	    });
	    output.println("");

	} catch (IOException e) {
	    e.printStackTrace();
	    OuftiBank.eventBus.post(e);
	}
    }

    private void printCategory(OutputFile output, Category category) {
	try {
	    output.println("");

	    output.println(category.getName() + ".NAME=" +
		    category.getName());
	    output.println(category.getName() + ".LABEL=" +
		    category.getLabel());
	    output.println(category.getName() + ".FXNAME=" +
		    category.getFxName());
	    output.println(category.getName() + ".STYLE=" +
		    category.getStyle());
	    output.println(category.getName() + ".TYPE=" +
		    category.getType());

	    Budget bb = category.getBudget();
	    output.println(category.getName() + ".BUDGET.AMOUT=" +
		    bb.getAmount());
	    output.println(category.getName() + ".BUDGET.FREQUENCYTYPE=" +
		    bb.getBudgetFrequencyType().name());
	    output.println(category.getName() + ".BUDGET.TYPE=" +
		    bb.getBudgetType().name());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return;
    }
}
