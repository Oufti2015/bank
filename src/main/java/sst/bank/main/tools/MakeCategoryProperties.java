package sst.bank.main.tools;

import sst.bank.activities.LifeCycleInterface;
import sst.bank.activities.j.saving.CategoriesSaver;
import sst.bank.config.BankConfiguration;
import sst.bank.main.OuftiBank;
import sst.bank.model.Budget;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;
import sst.common.file.output.OutputFile;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public class MakeCategoryProperties {

    public static void main(String[] args) {
        BankContainer.me();
        MakeCategoryProperties tool = new MakeCategoryProperties();
        LifeCycleInterface.runReadOnlyLifeCyle();
        tool.exportToPropertyFile();
        tool.exportToJSON();
    }

    private void exportToJSON() {
        new CategoriesSaver().run();
    }

    private void exportToPropertyFile() {
        try (OutputFile output = new OutputFile(BankConfiguration.CATEGORIES_PROPERTIES)) {
            Collection<Category> catList = BankContainer.me().getCategories();
            output.println("CATEGORIES="
                    + String.join(",", catList.stream().map(Category::getName).collect(Collectors.toList())));

            catList.forEach(c -> printCategory(output, c));
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
    }
}
