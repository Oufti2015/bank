package sst.bank.activities.e.grouping;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import sst.bank.activities.BankActivity;
import sst.bank.model.BankSummary;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

public class OperationsGrouper implements BankActivity {
    private DateTimeFormatter dtf_month = DateTimeFormatter.ofPattern("yyyy-MM");
    private DateTimeFormatter dtf_year = DateTimeFormatter.ofPattern("yyyy");

    @Override
    public void run() {

	List<String> mois = BankContainer.me().operations().stream()
		.map(o -> o.getValueDate().format(dtf_month))
		.distinct()
		.sorted()
		.collect(Collectors.toList());

	mois.stream().forEach(m -> groupOperationsByMonth(m));

	List<String> years = BankContainer.me().operations().stream()
		.map(o -> o.getValueDate().format(dtf_year))
		.distinct()
		.sorted()
		.collect(Collectors.toList());

	years.stream().forEach(y -> groupOperationsByYear(y));

	BankContainer.me().getCategories().stream().forEach(c -> groupOperationsByCategory(c));
    }

    private void groupOperationsByMonth(String m) {
	List<Operation> collect = BankContainer.me().operations().stream()
		.filter(o -> o.getValueDate().format(dtf_month).equals(m))
		.sorted()
		.collect(Collectors.toList());
	BankSummary bm = new BankSummary(collect);
	BankContainer.me().addMonth(bm);
    }

    private void groupOperationsByYear(String m) {
	List<Operation> collect = BankContainer.me().operations().stream()
		.filter(o -> o.getValueDate().format(dtf_year).equals(m))
		.sorted()
		.collect(Collectors.toList());
	BankSummary bm = new BankSummary(collect);
	BankContainer.me().addYear(bm);
    }

    private void groupOperationsByCategory(Category c) {
	for (BankSummary bmYear : BankContainer.me().operationsByYear()) {
	    List<Operation> collect = bmYear.getList().stream()
		    .filter(o -> o.getCategory().equals(c))
		    .sorted()
		    .collect(Collectors.toList());
	    if (!collect.isEmpty()) {
		BankSummary bm = new BankSummary(collect);
		bm.setCategory(c);
		BankContainer.me().addOperationsByCategory(bm);
	    }
	}
    }
}
