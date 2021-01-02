package sst.bank.main.tools;

import com.google.common.base.Strings;
import lombok.extern.log4j.Log4j2;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;

@Log4j2
public class ListCategories {

    public static void main(String[] args) {
        new ListCategories().run();
    }

    public void run() {
        new CategoriesLoader().run();

        log.info(
                String.format("%-17s %-23s %-7s %-15s %-17s %10s %10s %10s %10s %-7s %-8s", "NAME", "LABEL", "TYPE",
                        "STYLE", "BUDGET", "MONTHLY", "CONTROLLED", "YEARLY", "CONTROLLED", "MON/YEA", "SPE/SAVE"));
        log.info(
                String.format("%-17s %-23s %-7s %-15s %17s %10s %10s %10s %10s %7s %8s", Strings.padEnd("-", 17, '-'),
                        Strings.padEnd("-", 23, '-'), Strings.padEnd("-", 7, '-'),
                        Strings.padEnd("-", 15, '-'), Strings.padEnd("-", 17, '-'), Strings.padEnd("-", 10, '-'),
                        Strings.padEnd("-", 10, '-'), Strings.padEnd("-", 10, '-'), Strings.padEnd("-", 10, '-'),
                        Strings.padEnd("-", 7, '-'), Strings.padEnd("-", 8, '-')));

        BankContainer
                .me()
                .getCategories()
                .stream()
                .sorted()
                .forEach(c -> log.info(printCategory(c)));
    }

    private static String printCategory(Category category) {
        return String.format("%-15s : %-23s %-7s %-15s %s", category.getName(), category.getLabel(), category.getType(),
                category.getStyle(), category.getBudget());
    }
}
