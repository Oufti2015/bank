package sst.bank.activities.c.grouping;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import sst.bank.activities.Activity;
import sst.bank.model.BankSummary;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

public class OperationsGrouper implements Activity {
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");

    @Override
    public void run() {

	List<String> mois = BankContainer.me().operations().stream()
		.map(o -> o.getValueDate().format(dtf))
		.distinct()
		.sorted()
		.collect(Collectors.toList());

	mois.stream().forEach(m -> groupOperationsByMonth(m));
    }

    private void groupOperationsByMonth(String m) {
	List<Operation> collect = BankContainer.me().operations().stream()
		.filter(o -> o.getValueDate().format(dtf).equals(m))
		.sorted()
		.collect(Collectors.toList());
	BankSummary bm = new BankSummary(collect);
	BankContainer.me().addMonth(bm);
    }
}
