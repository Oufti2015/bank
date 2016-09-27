package sst.bank.main.tools;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import sst.bank.config.BankConfiguration;
import sst.bank.model.container.BankContainer;

public class MakeCategoryProperties {

    public static void main(String[] args) {
	BankContainer.me();
	MakeCategoryProperties tool = new MakeCategoryProperties();
	// tool.exportToPropertyFile();
	tool.exportToJSON();
    }

    private void exportToJSON() {
	try {
	    ObjectMapper mapper = new ObjectMapper();

	    // Object to JSON in file
	    mapper.writeValue(new File(BankConfiguration.CATEGORIES_JSON), BankContainer.me().getCategories());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // private void exportToPropertyFile() {
    // try (OutputFile output = new OutputFile(CATEGORIES_PROPERTIES)) {
    // ArrayList<String> catList = new ArrayList<>();
    // ArrayList<CategoryName> catListEnum = new ArrayList<>();
    // CategoryName[] categories = CategoryName.values();
    // for (int i = 0; i < categories.length; i++) {
    // catList.add(categories[i].name());
    // catListEnum.add(categories[i]);
    // }
    // output.println("CATEGORIES=" + String.join(",", catList));
    //
    // catListEnum.stream().forEach(c -> {
    // printCategory(output, c);
    // });
    // output.println("");
    //
    // } catch (IOException e) {
    // e.printStackTrace();
    // System.exit(-1);
    // }
    // }
    //
    // private Object printCategory(OutputFile output, CategoryName c) {
    // try {
    // output.println("");
    // Category category = BankContainer.me().category(c);
    //
    // output.println(category.getName().name() + ".NAME=" +
    // category.getName().name());
    // output.println(category.getName().name() + ".LABEL=" +
    // category.getLabel());
    // output.println(category.getName().name() + ".STYLE=" +
    // category.getStyle());
    // output.println(category.getName().name() + ".TYPE=" +
    // category.getType());
    //
    // Budget bb = BankBudget.me().get(c);
    // output.println(category.getName().name() + ".BUDGET.AMOUT=" +
    // bb.getAmount());
    // output.println(category.getName().name() + ".BUDGET.FREQUENCYTYPE=" +
    // bb.getBudgetFrequencyType().name());
    // output.println(category.getName().name() + ".BUDGET.TYPE=" +
    // bb.getBudgetType().name());
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }
}
