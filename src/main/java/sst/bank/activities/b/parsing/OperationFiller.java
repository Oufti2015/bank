package sst.bank.activities.b.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sst.bank.activities.BankActivity;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

public class OperationFiller implements BankActivity {

    private Pattern[] patternDetails = { Pattern.compile(".*((BE)\\d{14}).*"), Pattern.compile(".*((LU)\\d{18}).*"),
	    Pattern.compile(".*((FR)\\d{27}).*") };
    private Pattern[] patternFormatted = { Pattern.compile(".*((BE)\\d{2} \\d{4} \\d{4} \\d{4}).*"),
	    Pattern.compile(".*((LU)\\d{2} \\d{4} \\d{4} \\d{4} \\d{4}).*"),
	    Pattern.compile(".*((FR)\\d{2} \\d{4} \\d{4} \\d{4} \\d{4} \\d{4} \\d{3}).*") };
    private String[] countries = { "BE", "LU", "FR}" };

    @Override
    public void run() {
	BankContainer.me().operations().stream().forEach(o -> fillOperation(o));
    }

    private void fillOperation(Operation o) {

	defaultCounterparty(o);
	return;
    }

    private void defaultCounterparty(Operation o) {
	if (o.getCounterparty() != null && o.getCounterparty().startsWith("LU") && o.getCounterparty().length() == 16) {
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
		o.setCounterparty(formatCounterparty(o.getCounterparty(), countries[i], patternFormatted[i]));
	    }
	}
    }

    private String formatCounterparty(String group, String country, Pattern pattern) {
	Matcher matcher = pattern.matcher(group);
	if (!matcher.find() && group.startsWith(country)) {
	    int i = 0;
	    String result = "";
	    while (i < group.length()) {
		result += group.substring(i, i + 4) + " ";
		i += 4;
	    }
	    return result.trim();
	}
	return group;
    }
}
