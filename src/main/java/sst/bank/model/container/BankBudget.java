package sst.bank.model.container;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		.filter(b -> !BudgetType.SAVING.equals(b.getBudgetType()))
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
		.filter(b -> !BudgetType.SAVING.equals(b.getBudgetType()))
		.mapToDouble(b -> b.monthlyAmount().doubleValue()).sum() + (saving - yearly);
    }

    public double yearlyTotal() { // NO_UCD (unused code)
	double yearly = budgets.values().stream()
		.filter(b -> BudgetFrequencyType.YEARLY.equals(b.getBudgetFrequencyType()))
		.filter(b -> !BudgetType.SAVING.equals(b.getBudgetType()))
		.mapToDouble(b -> b.yearlyAmount(12).doubleValue())
		.sum();
	double saving = budgets.values().stream()
		.filter(b -> BudgetFrequencyType.MONTHLY.equals(b.getBudgetFrequencyType()))
		.filter(b -> BudgetType.SAVING.equals(b.getBudgetType()))
		.mapToDouble(b -> b.yearlyAmount(12).doubleValue())
		.sum();

	System.out.println("yearly = " + yearly);
	System.out.println("saving = " + saving);
	System.out.println("saving - yearly = " + (saving - yearly));

	return budgets.values().stream()
		.filter(b -> !BudgetType.SAVING.equals(b.getBudgetType()))
		.mapToDouble(b -> b.yearlyAmount(12).doubleValue()).sum() + (saving - yearly);
    }

    public Budget get(CategoryName category) { // NO_UCD (unused code)
	// System.out.println("category=" + category + "/budget=" +
	// budgets.get(category));
	return budgets.get(category);
    }

    private void init() {
	put(new Budget(CategoryName.EPARGNE, BigDecimal.valueOf(-600.00), BudgetFrequencyType.MONTHLY,
		BudgetType.SAVING));

	put(new Budget(CategoryName.ASSURANCES, BigDecimal.valueOf(-1634.64), BudgetFrequencyType.YEARLY));
	put(new Budget(CategoryName.ELECTRICITE, BigDecimal.valueOf(-73), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.ANNE, BigDecimal.valueOf(-17.66), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.SALAIRE, BigDecimal.valueOf(3376.00), BudgetFrequencyType.MONTHLY,
		BudgetType.SALARY));
	put(new Budget(CategoryName.TELEPHONE, BigDecimal.valueOf(-108.00), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.DOG_AGILITY, BigDecimal.valueOf(-36.00 * 3.00), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.SODEXHO, BigDecimal.valueOf(-216.00), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.SPORT, BigDecimal.valueOf(-308), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.WATER, BigDecimal.valueOf(-118.5), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.MOVE, BigDecimal.valueOf(-50), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.DRESSING, BigDecimal.valueOf(-10.4), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.VISA, BigDecimal.valueOf(-1000), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.BANK, BigDecimal.valueOf(-8.2), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.CAR, BigDecimal.valueOf(-329.81), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.HOUSE, BigDecimal.valueOf(-988.80), BudgetFrequencyType.MONTHLY));
	put(new Budget(CategoryName.HOUSE_HOT, BigDecimal.valueOf(-1100), BudgetFrequencyType.YEARLY));

	for (CategoryName cat : CategoryName.values()) {
	    if (!budgets.keySet().contains(cat)) {
		put(new Budget(cat, BigDecimal.ZERO, BudgetFrequencyType.MONTHLY));
	    }
	}
    }

    private void put(Budget budget) {
	budgets.put(budget.getCategory(), budget);
	BankContainer.me().category(budget.getCategory()).setBudget(budget);
    }

    private void checkBudget() {
	CategoryName[] categories = CategoryName.values();
	for (int i = 0; i < categories.length; i++) {
	    if (budgets.get(categories[i]) == null) {
		System.err.println("WARNING: " + categories[i] + " not found in budget.");
	    }
	}
    }

    public List<Budget> budgets() {
	return budgets.values().stream().collect(Collectors.toList());
    }
}
