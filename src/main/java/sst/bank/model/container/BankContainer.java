package sst.bank.model.container;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import sst.bank.model.BankSummary;
import sst.bank.model.Category;
import sst.bank.model.Operation;

public class BankContainer {
    private static BankContainer me = null;
    static {
	me = new BankContainer();
    }

    private BankContainer() {
    }

    public static BankContainer me() {
	return me;
    }

    private List<Operation> operations = new ArrayList<>();

    public List<Operation> operations() {
	return operations;
    }

    public void addOperations(List<Operation> list) {
	operations.addAll(list);
    }

    private List<BankSummary> operationsByMonth = new ArrayList<>();

    public List<BankSummary> operationsByMonth() {
	return operationsByMonth;
    }

    public void addMonth(BankSummary summary) {
	operationsByMonth.add(summary);
    }

    private List<BankSummary> operationsByYear = new ArrayList<>();

    public List<BankSummary> operationsByYear() {
	return operationsByYear;
    }

    public void addYear(BankSummary summary) {
	operationsByYear.add(summary);
    }

    public enum CategoryName {
	UNKNOWN,
	SALAIRE,
	ASSURANCES,
	ELECTRICITE,
	TELEPHONE,
	AGILITY,
	SODEXHO,
	SPORT,
	WATER,
	MUTUELLE,
	BANK,
	CAR,
	HOUSE,
	DOG,
	VISA,
	ANNE,
	EPARGNE,
	DRESSING,
	LEISURE,
	MOVE
    };

    private static Map<CategoryName, Category> categories = new HashMap<>();

    static {
	categories.put(CategoryName.UNKNOWN, new Category(CategoryName.UNKNOWN, "", ""));

	categories.put(CategoryName.SALAIRE, new Category(CategoryName.SALAIRE, "Salaire", "salaire"));
	categories.put(CategoryName.ASSURANCES, new Category(CategoryName.ASSURANCES, "Assurances", "assurances"));
	categories.put(CategoryName.ELECTRICITE,
		new Category(CategoryName.ELECTRICITE, "&Eacute;lectricit&eacute;", "electricite"));
	categories.put(CategoryName.TELEPHONE,
		new Category(CategoryName.TELEPHONE, "T&eacute;l&eacute;phone", "phone"));
	categories.put(CategoryName.AGILITY, new Category(CategoryName.AGILITY, "Agility", "dog"));
	categories.put(CategoryName.SODEXHO, new Category(CategoryName.SODEXHO, "Sodexho", "house"));
	categories.put(CategoryName.SPORT, new Category(CategoryName.SPORT, "Sport", "sport"));
	categories.put(CategoryName.WATER, new Category(CategoryName.WATER, "Eau", "house"));
	categories.put(CategoryName.MUTUELLE, new Category(CategoryName.MUTUELLE, "Mutuelle", "mutuelle"));
	categories.put(CategoryName.BANK, new Category(CategoryName.BANK, "Banque", "bank"));
	categories.put(CategoryName.CAR, new Category(CategoryName.CAR, "Voiture", "car"));
	categories.put(CategoryName.HOUSE, new Category(CategoryName.HOUSE, "Maison", "house"));
	categories.put(CategoryName.DOG, new Category(CategoryName.DOG, "Chiens", "dog"));
	categories.put(CategoryName.VISA, new Category(CategoryName.VISA, "Visa", "bank"));
	categories.put(CategoryName.EPARGNE, new Category(CategoryName.EPARGNE, "&Eacute;pargne", "bank"));
	categories.put(CategoryName.ANNE, new Category(CategoryName.ANNE, "Anne", "anne"));
	categories.put(CategoryName.DRESSING, new Category(CategoryName.DRESSING, "V&ecirc;tements", "water"));
	categories.put(CategoryName.LEISURE, new Category(CategoryName.LEISURE, "Loisirs", "loisirs"));
	categories.put(CategoryName.MOVE, new Category(CategoryName.MOVE, "D&eacute;placements", "move"));
    }

    public Category category(CategoryName name) {
	return categories.get(name);
    }

    public BankSummary yearlySummary(Year year, LocalDate endDate) {
	List<Operation> result = operations.stream()
		.filter(o -> year.equals(Year.from(o.getValueDate())))
		.filter(o -> endDate.compareTo(o.getValueDate()) >= 0)
		.collect(Collectors.toList());

	return new BankSummary(result);
    }
}
