package sst.bank.activities.i.printing.bycategory;

import lombok.extern.log4j.Log4j2;
import sst.bank.activities.BankActivity;
import sst.bank.activities.i.printing.*;
import sst.bank.config.BankConfiguration;
import sst.bank.main.OuftiBank;
import sst.bank.model.BankSummary;
import sst.bank.model.container.BankContainer;
import sst.common.file.output.OutputFile;
import sst.common.html.*;
import sst.common.html.head.HTMLHead;
import sst.common.html.table.builders.IntoTableConverter;
import sst.common.html.table.builders.TableBuilder;
import sst.common.html.util.HTMLOneRowTable;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class OperationsPrinterByCategory implements BankActivity {
    private TableBuilder operationsTableBuilder = new TableBuilder(OperationsIntoTableConverter.getHeaders());

    @Override
    public void run() {
        buildHTMLPage();
    }

    private void buildHTMLPage() {
        HTML html = new HTML();
        HTMLHead head = new HTMLHead()
                .css("../bank.css")
                .css("../tabmenu.css")
                .javaScript("../tabmenu.js")
                .title("Oufti Bank");
        html.addChild(head);
        HTMLBody body = new HTMLBody();
        html.addChild(body);
        body.addChild(tabMenu());

        for (BankSummary bm : BankContainer.me().operationsByCategory()) {

            body.addChild(new HTMLHyperlinks().id(anchorName(bm)));
            List<IntoTableConverter> listConvert = bm.getList().stream()
                    .map(OperationsIntoTableConverter::new)
                    .collect(Collectors.toList());
            listConvert.add(new SumRowIntoTableConverter(bm.getList()));

            HTMLDiv div = new HTMLDiv();
            div.style("overflow-x:auto;");
            div.addChild(new HTMLThemeBreak());
            div.addChild(printHeader("Operations", bm));
            div.addChild(operationsTableBuilder.build(listConvert));

            printSummary(bm, div);

            body.addChild(div);

            div = new HTMLDiv();
            div.classId("backtotop");
            div.addChild(new HTMLThemeBreak());
            div.addChild(new HTMLOneRowTable(new HTMLHyperlinks().href("#").textContent("Back to Top"),
                    new HTMLHyperlinks().href("../index.html").textContent("Back to Home")));
            body.addChild(div);
        }

        try (OutputFile output = new OutputFile(constructPathName())) {
            output.println(html.toString());
        } catch (IOException e) {
            log.fatal("Cannot write to " + constructPathName(), e);
            OuftiBank.eventBus.post(e);
        }
    }

    private String constructPathName() {
        return BankConfiguration.me().getOutputDir() + File.separator + "category" + File.separator + "index.html";
    }

    private void printSummary(BankSummary bm, HTMLDiv div) {
        TotalAmountSummers tas = new TotalAmountSummers();
        List<IntoTableConverter> listConvert;
        listConvert = bm.getSummary().keySet().stream()
                .sorted()
                .map(c -> new SummaryIntoTableConverter(tas, c,
                        bm.getSummary().get(c).amount,
                        c.getBudget(),
                        bm.monthQuantity()))
                .collect(Collectors.toList());
        listConvert.add(new SummarySumRowIntoTableConverter(bm, tas));

        HTMLDiv div2 = new HTMLDiv();
        div2.style("overflow-x:auto;");

        div.addChild(div2);
    }

    private AbstractHTMLElement printHeader(String title, BankSummary bm) {
        String result;
        result = title + " of " + bm.getCategory().getLabel() + " (" + Year.from(bm.getStartDate()) + ")";
        return new HTMLHeader(2).textContent(result);
    }

    private AbstractHTMLElement tabMenu() {
        HTMLDiv div = new HTMLDiv();
        HTMLUnorderedList ul = new HTMLUnorderedList();
        ul.id("tabmenu");

        String menuName = null;
        HTMLUnorderedList subul = null;
        for (BankSummary bm : BankContainer.me().operationsByCategory()) {
            String constructMenuName = constructMenuName(bm);
            if (menuName == null || !menuName.equals(constructMenuName)) {
                menuName = constructMenuName;
                HTMLListItem li = new HTMLListItem();
                ul.addChild(li);
                li.textContent(constructMenuName);
                subul = new HTMLUnorderedList();
                li.addChild(subul);
            }
            HTMLListItem subli = new HTMLListItem();
            sst.common.html.HTMLHyperlinks a = new HTMLHyperlinks();
            a.href(linkName(bm));
            a.textContent(constructSubMenuName(bm));
            subli.addChild(a);
            subul.addChild(subli);
        }
        return div.addChild(ul);
    }

    private String constructMenuName(BankSummary bm) {
        return "" + Year.from(bm.getStartDate());
    }

    private String constructSubMenuName(BankSummary bm) {
        return (bm.getCategory() != null) ? "" + bm.getCategory().getLabel() : "";
    }

    private String linkName(BankSummary bm) {
        return (bm.getCategory() != null) ? "#" + anchorName(bm) : "#";
    }

    private String anchorName(BankSummary bm) {
        return Year.from(bm.getStartDate()) + bm.getCategory().getName();
    }
}
