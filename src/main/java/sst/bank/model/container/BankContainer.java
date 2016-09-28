package sst.bank.model.container;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import sst.bank.config.BankConfiguration;
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

    private List<BankSummary> operationsByCategory = new ArrayList<>();

    public List<BankSummary> operationsByCategory() {
	return operationsByCategory;
    }

    public void addOperationsByCategory(BankSummary summary) {
	operationsByCategory.add(summary);
    }

    @Deprecated
    public enum CategoryName {
	UNKNOWN,
	SALAIRE,
	ASSURANCES,
	ELECTRICITE,
	TELEPHONE,
	DOG_AGILITY,
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
	LEISURE_HOL,
	MOVE,
	DOG_VET,
	DOG_FOOD,
	CAR_FUEL,
	HEALTH,
	TAX,
	HOUSE_HOT,
	HOUSE_FOOD,
	BANK_RETRAIT
    };

    private static List<Category> categories = new ArrayList<>();
    private static HashMap<String, Category> categoryByName = new HashMap<>();

    static {
	ObjectMapper mapper = new ObjectMapper();

	// JSON from file to Object
	try {
	    categories = mapper.readValue(new File(BankConfiguration.CATEGORIES_JSON),
		    new TypeReference<List<Category>>() {
		    });
	} catch (IOException e) {
	    e.printStackTrace();
	}

	categories.stream().forEach(c -> categoryByName.put(c.getName(), c));

	// addCategory(CategoryName.UNKNOWN, "", "");
	//
	// addCategory(CategoryName.SALAIRE, "Salaire", "salaire");
	// addCategory(CategoryName.ASSURANCES, "Assurances", "assurances");
	// addCategory(CategoryName.ELECTRICITE, "Maison (&Eacute;lectr.)",
	// "house");
	// addCategory(CategoryName.TELEPHONE, "T&eacute;l&eacute;phone",
	// "phone");
	// addCategory(CategoryName.DOG_AGILITY, "Chiens (Agility)", "dog");
	// addCategory(CategoryName.SODEXHO, "Maison (Sodexho)", "house");
	// addCategory(CategoryName.SPORT, "Sport", "sport");
	// addCategory(CategoryName.WATER, "Maison (Eau)", "house");
	// addCategory(CategoryName.MUTUELLE, "Mutuelle", "mutuelle");
	// addCategory(CategoryName.BANK, "Banque", "bank");
	// addCategory(CategoryName.BANK_RETRAIT, "Banque (Retrait)", "bank");
	// addCategory(CategoryName.CAR, "Voiture", "car");
	// addCategory(CategoryName.CAR_FUEL, "Voiture (Essence)", "car");
	// addCategory(CategoryName.HOUSE, "Maison", "house");
	// addCategory(CategoryName.HOUSE_HOT, "Maison (Chauffage)", "house");
	// addCategory(CategoryName.DOG, "Chiens", "dog");
	// addCategory(CategoryName.DOG_VET, "Chiens (Soins)", "dog");
	// addCategory(CategoryName.DOG_FOOD, "Chiens (Food)", "dog");
	// addCategory(CategoryName.VISA, "Visa", "bank");
	// addCategory(CategoryName.EPARGNE, "&Eacute;pargne", "bank",
	// CategoryType.NEGATIF);
	// addCategory(CategoryName.ANNE, "Anne", "anne");
	// addCategory(CategoryName.DRESSING, "V&ecirc;tements", "house");
	// addCategory(CategoryName.LEISURE, "Loisirs", "loisirs");
	// addCategory(CategoryName.LEISURE_HOL, "Loisirs (Vacances)",
	// "loisirs");
	// addCategory(CategoryName.MOVE, "D&eacute;placements", "move");
	// addCategory(CategoryName.HEALTH, "Sant&eacute;", "water");
	// addCategory(CategoryName.TAX, "Taxes", "electricite");
	// addCategory(CategoryName.HOUSE_FOOD, "Food", "house");
    }

    // private static void addCategory(CategoryName category, String label,
    // String css) {
    // categories.put(category, new Category(category, label, css));
    // }
    //
    // private static void addCategory(CategoryName category, String label,
    // String css, CategoryType type) {
    // categories.put(category, new Category(category, label, css, type));
    // }

    public Category category(String categoryName) {
	return categoryByName.get(categoryName);
    }

    public BankSummary yearlySummary(Year year, LocalDate endDate) {
	List<Operation> result = operations.stream()
		.filter(o -> year.equals(Year.from(o.getValueDate())))
		.filter(o -> endDate.compareTo(o.getValueDate()) >= 0)
		.collect(Collectors.toList());

	return new BankSummary(result);
    }

    public Collection<Category> getCategories() {
	return categories;
    }
}
