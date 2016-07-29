package sst.bank.model.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	categories.put(CategoryName.UNKNOWN, new Category("", ""));

	categories.put(CategoryName.SALAIRE, new Category("Salaire", "salaire"));
	categories.put(CategoryName.ASSURANCES, new Category("Assurances", "assurances"));
	categories.put(CategoryName.ELECTRICITE, new Category("&Eacute;lectricit&eacute;", "electricite"));
	categories.put(CategoryName.TELEPHONE, new Category("T&eacute;l&eacute;phone", "phone"));
	categories.put(CategoryName.AGILITY, new Category("Agility", "dog"));
	categories.put(CategoryName.SODEXHO, new Category("Sodexho", "house"));
	categories.put(CategoryName.SPORT, new Category("Sport", "sport"));
	categories.put(CategoryName.WATER, new Category("Eau", "house"));
	categories.put(CategoryName.MUTUELLE, new Category("Mutuelle", "mutuelle"));
	categories.put(CategoryName.BANK, new Category("Banque", "bank"));
	categories.put(CategoryName.CAR, new Category("Voiture", "car"));
	categories.put(CategoryName.HOUSE, new Category("Maison", "house"));
	categories.put(CategoryName.DOG, new Category("Chiens", "dog"));
	categories.put(CategoryName.VISA, new Category("Visa", "bank"));
	categories.put(CategoryName.EPARGNE, new Category("&Eacute;pargne", "bank"));
	categories.put(CategoryName.ANNE, new Category("Anne", "anne"));
	categories.put(CategoryName.DRESSING, new Category("Vêtements", "water"));
	categories.put(CategoryName.LEISURE, new Category("Loisirs", "loisirs"));
	categories.put(CategoryName.MOVE, new Category("D&eacute;placements", "move"));
    }

    public Category category(CategoryName name) {
	return categories.get(name);
    }

    public BankSummary yearlySummary() {
	return new BankSummary(operations);
    }
}
