package sst.bank.activities.c.parsing;

import sst.bank.activities.BankActivity;
import sst.bank.model.Operation;
import sst.bank.model.Operation.OperationType;
import sst.bank.model.container.BankContainer;

import java.time.LocalDate;
import java.time.Month;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationFiller implements BankActivity {
    //DE13585501300001104488
    private final Pattern[] patternDetails = {
            Pattern.compile(".*((BE)\\d{14}).*"),
            Pattern.compile(".*((LU)\\d{18}).*"),
            Pattern.compile(".*((FR)\\d{22}.\\d{2}).*"),
            Pattern.compile(".*((DE)\\d{20}).*")};
    private final Pattern[] patternFormatted = {
            Pattern.compile(".*((BE)\\d{2} \\d{4} \\d{4} \\d{4}).*"),
            Pattern.compile(".*((LU)\\d{2} \\d{4} \\d{4} \\d{4} \\d{4}).*"),
            Pattern.compile(".*((FR)\\d{2} \\d{4} \\d{4} \\d{4} \\d{4} \\d{4} .{3}).*"),
            Pattern.compile(".*((DE)\\d{2} \\d{4} \\d{4} \\d{4} \\d{4} \\d{2}).*")};
    private String[] countries = {"BE", "LU", "FR", "DE"};
    private String[] countriesForb = {"BEL", "LUX", "FRA", "GER"};

    @Override
    public void run() {
        BankContainer.me().operationsContainer().operations().stream().forEach(o -> fillOperation(o));
    }

    private void fillOperation(Operation o) {

        defaultCounterparty(o);
        // correctVISADate(o);
        return;
    }

    private void correctVISADate(Operation o) {
        if ("CARTE VISA".equals(o.getCounterparty())) {
            o.setExecutionDate(LocalDate.of(2016, Month.SEPTEMBER, 4));
            o.setValueDate(LocalDate.of(2016, Month.SEPTEMBER, 4));
            o.setCounterparty("RELEVE VISA");
        }
    }

    private void defaultCounterparty(Operation o) {
        if (o.getCounterparty() != null && (o.getCounterparty().startsWith("LU")) && o.getCounterparty().length() == 16) {
            o.setCounterparty(null);
        }

        for (int i = 0; i < patternDetails.length; i++) {
            if (o.getCounterparty() == null) {
                Matcher matcher = patternDetails[i].matcher(o.getDetail());
                if (matcher.find()) {
                    o.setCounterparty(matcher.group(1));
                } else {
                    matcher = patternFormatted[i].matcher(o.getDetail());
                    if (matcher.find()) {
                        o.setCounterparty(matcher.group(1));
                    }
                }
            }

            if (o.getCounterparty() != null) {
                o.setCounterparty(
                        formatCounterparty(o.getCounterparty(), countries[i], countriesForb[i], patternFormatted[i]));
            }
        }

        if (o.getCounterparty() == null && OperationType.VISA.equals(o.getOperationType())) {
            o.setCounterparty("CARTE VISA");
        }
    }

    private String formatCounterparty(String group, String country, String countryForb, Pattern pattern) {
        // System.out.println("group=" + group);
        Matcher matcher = pattern.matcher(group);
        if (!matcher.find() && group.startsWith(country) && !group.startsWith(countryForb)) {
            int i = 0;
            String result = "";
            while (i < group.length()) {
                result += group.substring(i, (i + 4 > group.length()) ? group.length() : i + 4) + " ";
                i += 4;
            }
            return result.trim();
        }
        return group;
    }
}
