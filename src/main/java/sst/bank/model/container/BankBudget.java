package sst.bank.model.container;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import sst.bank.model.Budget;
import sst.bank.model.Budget.BudgetFrequencyType;
import sst.bank.model.Budget.BudgetType;
import sst.bank.model.container.BankContainer.CategoryName;

public class BankBudget {
    private static BankBudget me = null;
    static {
	me = new BankBudget();
    }

    public static BankBudget me() {
	return me;
    }

    private Map<CategoryName, Budget> budgets = new HashMap<>();

    private BankBudget() {
	init();
	checkBudget();
	// System.out.println(
	// "Budget Total Mensuel: " + monthlyTotal());
	// System.out.println(
	// "Budget Total Annuel: " + yearlyTotal());
    }

    public double monthlyTotal() { // NO_UCD (unused code)
	double yearly = budgets.values().stream()
		.filter(b -> BudgetFrequencyType.YEARLY.equals(b.getBudgetFrequencyType()))
		.filter(b -> BudgetType.SPENDING.equals(b.getBudgetType()))
		.mapToDouble(b -> b.monthlyAmount().doubleValue())
		.sum();
	double saving = budgets.values().stream()
		.filter(b -> BudgetFrequencyType.MONTHLY.equals(b.getBudgetFrequencyType()))
		.filter(b -> BudgetType.SAVING.equals(b.getBudgetType()))
		.mapToDouble(b -> b.monthlyAmount().doubleValue())
		.sum();

	// System.out.println("yearly = " + yearly);
	// System.out.println("saving = " + saving);
	// System.out.println("saving - yearly = " + (saving - yearly));

	return budgets.values().stream()
		.filter(b -> BudgetType.SPENDING.equals(b.getBudgetType()))
		.mapToDouble(b -> b.monthlyAmount().doubleValue()).sum() + (saving - yearly);
    }

    public double yearlyTotal() { // NO_UCD (unused code)
	double yearly = budgets.values().stream()
		.filter(b -> BudgetFrequencyType.YEARLY.equals(b.getBudgetFrequencyType()))
		.filter(b -> BudgetType.SPENDING.equals(b.getBudgetType()))
		.mapToDouble(b -> b.yearlyAmount().doubleValue())
		.sum();
	double saving = budgets.values().stream()
		.filter(b -> BudgetFrequencyType.MONTHLY.equals(b.getBudgetFrequencyType()))
		.filter(b -> BudgetType.SAVING.equals(b.getBudgetType()))
		.mapToDouble(b -> b.yearlyAmount().doubleValue())
		.sum();

	System.out.println("yearly = " + yearly);
	System.out.println("saving = " + saving);
	System.out.println("saving - yearly = " + (saving - yearly));

	return budgets.values().stream()
		.filter(b -> BudgetType.SPENDING.equals(b.getBudgetType()))
		.mapToDouble(b -> b.yearlyAmount().doubleValue()).sum() + (saving - yearly);
    }

    public Budget get(CategoryName category) { // NO_UCD (unused code)
	return budgets.get(category);
    }

    private void init() {
	put(new Budget(CategoryName.EPARGNE, BigDecimal.valueOf(-600.00), BudgetFrequencyType.MONTHLY,
		BudgetType.SAVING));

	put(new Budget(CategoryName.ASSURANCES, BigDecimal.valueOf(-1634.64), BudgetFrequencyType.YEARLY));
	put(new Budget(CategoryName.ELECTRICITE, BigDecimal.valueOf(-150.00), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.ANNE, BigDecimal.valueOf(-17.66), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.SALAIRE, BigDecimal.valueOf(3962.90), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.TELEPHONE, BigDecimal.valueOf(-108.00), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.AGILITY, BigDecimal.valueOf(-36.00 * 3.00), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.SODEXHO, BigDecimal.valueOf(-216.00), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.SPORT, BigDecimal.valueOf(-308), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.WATER, BigDecimal.valueOf(-118.5), BudgetFrequencyType.MONTHLY));
    }

    private void put(Budget budget) {
	budgets.put(budget.getCategory(), budget);
    }

    private void checkBudget() {
	CategoryName[] categories = CategoryName.values();
	for (int i = 0; i < categories.length; i++) {
	    if (budgets.get(categories[i]) == null) {
		System.err.println("WARNING: " + categories[i] + " not found in budget.");
	    }
	}
    }
}
