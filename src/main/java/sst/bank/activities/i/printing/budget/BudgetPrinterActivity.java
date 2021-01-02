package sst.bank.activities.i.printing.budget;

import lombok.extern.log4j.Log4j2;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.main.OuftiBank;
import sst.bank.model.BankSummary;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;
import sst.common.file.output.OutputFile;
import sst.common.html.HTML;
import sst.common.html.HTMLBody;
import sst.common.html.HTMLThemeBreak;
import sst.common.html.head.HTMLHead;
import sst.common.html.table.HTMLTable;
import sst.common.html.table.builders.IntoTableConverter;
import sst.common.html.table.builders.TableBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class BudgetPrinterActivity implements BankActivity {
    @Override
    public void run() {
        List<BankSummary> operationsByYear = BankContainer.me().operationsByYear();
        List<String> header = new ArrayList<>();
        header.add("Catégories");
        header.add("Budget");

        operationsByYear
                .stream()
                .map(BankSummary::toString)
                .forEach(header::add);

        HTML html = createDocument();
        html.body().addChild(createTable(operationsByYear, header));
        html.body().addChild(new HTMLThemeBreak());
        html.body().addChild(createBudgetTable());

        try (OutputFile output = new OutputFile(constructPathName())) {
            output.println(html.toString());
        } catch (IOException e) {
            log.fatal("Cannot write to " + constructPathName(), e);
            OuftiBank.eventBus.post(e);
        }
    }

    private HTML createDocument() {
        HTML html = new HTML();
        HTMLHead head = new HTMLHead()
                .css("../bank.css")
                .css("../tabmenu.css")
                .javaScript("../tabmenu.js")
                .title("Oufti Bank");
        html.addChild(head);
        HTMLBody body = new HTMLBody();
        html.addChild(body);
        return html;
    }

    private HTMLTable createTable(List<BankSummary> operationsByYear, List<String> header) {
        TableBuilder budgetsTableBuilder = new TableBuilder(header.toArray(new String[header.size()]));

        List<IntoTableConverter> rows = new ArrayList<>(BankContainer.me().getCategories().size());
        for (Category category : BankContainer.me().getCategories().stream().sorted().collect(Collectors.toList())) {
            Double[] utilisations = new Double[operationsByYear.size()];
            int i = 0;
            for (BankSummary summary : operationsByYear) {
                utilisations[i++] = summary.getList().stream()
                        .filter(o -> category.equals(o.getCategory()))
                        .mapToDouble(o -> o.getAmount().doubleValue())
                        .sum();
            }
            rows.add(new UtilisationIntoTableConverter(category, utilisations));
        }

        return budgetsTableBuilder.build(rows);
    }

    private HTMLTable createBudgetTable() {
        TableBuilder tableBuilder = new TableBuilder(BudgetIntoTableConverter.headers);
        List<IntoTableConverter> list = new ArrayList<>();

        BankContainer.me().getCategories()
                .stream()
                .sorted()
                .forEach(c -> list.add(new BudgetIntoTableConverter(c)));

        return tableBuilder.build(list);
    }

    private String constructPathName() {
        return BankConfiguration.me().getOutputDir() + File.separator + "category" + File.separator + "budgets.html";
    }
}
