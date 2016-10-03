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

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import sst.bank.config.BankConfiguration;
import sst.bank.model.BankSummary;
import sst.bank.model.Category;
import sst.bank.model.Operation;

public class BankContainer {
    private static Logger logger = Logger.getLogger(BankContainer.class);

    private static BankContainer me = null;
    static {
	me = new BankContainer();
    }

    private BankContainer() {
    }

    public static BankContainer me() {
	return me;
    }

    @Getter
    @Setter
    private Integer lastId = 0;

    public Integer newId() {
	return lastId++;
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
	return operationsByCategory.stream().sorted().collect(Collectors.toList());
    }

    public void addOperationsByCategory(BankSummary summary) {
	operationsByCategory.add(summary);
    }

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
	    logger.error("Cannot read file " + BankConfiguration.CATEGORIES_JSON, e);
	}

	categories.stream().forEach(c -> categoryByName.put(c.getName(), c));

    }

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
