package sst.bank.activities.c.grouping;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import sst.bank.activities.Activity;
import sst.bank.model.BankSummary;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

public class OperationsGrouper implements Activity {
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
}
