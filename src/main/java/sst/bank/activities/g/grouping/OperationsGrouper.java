package sst.bank.activities.g.grouping;

import sst.bank.activities.BankActivity;
import sst.bank.model.BankSummary;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OperationsGrouper implements BankActivity {
    private DateTimeFormatter dtfMonth = DateTimeFormatter.ofPattern("yyyy-MM");
    private DateTimeFormatter dtfYear = DateTimeFormatter.ofPattern("yyyy");

    @Override
    public void run() {

        List<String> mois = BankContainer.me().operationsContainer().operations().stream()
                .map(o -> o.getValueDate().format(dtfMonth))
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        mois.stream().forEach(this::groupOperationsByMonth);

        List<String> years = BankContainer.me().operationsContainer().operations().stream()
                .map(o -> o.getValueDate().format(dtfYear))
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        years.stream().forEach(this::groupOperationsByYear);

        BankContainer.me().getCategories().stream().forEach(this::groupOperationsByCategory);
    }

    private void groupOperationsByMonth(String m) {
        List<Operation> collect = BankContainer.me().operationsContainer().operations().stream()
                .filter(o -> o.getValueDate().format(dtfMonth).equals(m))
                .sorted()
                .collect(Collectors.toList());
        BankSummary bm = new BankSummary(collect);
        BankContainer.me().addMonth(bm);
    }

    private void groupOperationsByYear(String m) {
        List<Operation> collect = BankContainer.me().operationsContainer().operations().stream()
                .filter(o -> o.getValueDate().format(dtfYear).equals(m))
                .sorted()
                .collect(Collectors.toList());
        BankSummary bm = new BankSummary(collect);
        BankContainer.me().addYear(bm);
    }

    private void groupOperationsByCategory(Category c) {
        List<Operation> collect = BankContainer.me().operationsContainer().operations()
                .stream()
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
