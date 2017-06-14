package sst.bank.main.tools;

import java.math.BigDecimal;
import java.util.Optional;

import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.j.saving.CategoriesSaver;
import sst.bank.model.Budget;
import sst.bank.model.Budget.BudgetFrequencyType;
import sst.bank.model.Budget.BudgetType;
import sst.bank.model.Category;
import sst.bank.model.Category.CategoryType;
import sst.bank.model.container.BankContainer;

public class UpdateCategories {

    public static void main(String[] args) {
	new CategoriesLoader().run();

	Category category = createCategory();

	if (BankContainer.me().getCategories().contains(category)) {
	    Optional<Category> cat = BankContainer.me().getCategories().stream()
		    .filter(c -> c.getName().equals("RESTO")).findFirst();
	    if (cat.isPresent()) {
		category = cat.get();
		Budget budget = category.getBudget();
		if (budget.getControlledAmount().compareTo(BigDecimal.ZERO) > 0) {
		    budget.setControlledAmount(budget.getControlledAmount().multiply(BigDecimal.valueOf(-1.00)));
		}
		category.setStyle("loisirs");
	    }
	} else {
	    BankContainer.me().getCategories().add(category);
	}
	new CategoriesSaver().run();
	new ListCategories().run();
    }

    private static Category createCategory() {
	Category category = new Category();
	category.setName("FOOD");
	category.setLabel("Alimentation");
	category.setStyle("house");
	category.setType(CategoryType.POSITIF);
	category.setDefaultCategory(false);
	category.setNegatif(false);
	category.setVisa(false);

	Budget budget = new Budget();
	budget.setCategory(category.getName());
	budget.setAmount(BigDecimal.valueOf(0.0));
	budget.setBudgetFrequencyType(BudgetFrequencyType.MONTHLY);
	budget.setBudgetType(BudgetType.SPENDING);
	budget.setControlledAmount(budget.getAmount());

	category.setBudget(budget);

	return category;
    }
}
