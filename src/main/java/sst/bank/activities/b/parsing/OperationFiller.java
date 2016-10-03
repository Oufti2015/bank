package sst.bank.activities.b.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sst.bank.activities.BankActivity;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

public class OperationFiller implements BankActivity {

    @Override
    public void run() {
	BankContainer.me().operations().stream().forEach(o -> fillOperation(o));
    }

    private void fillOperation(Operation o) {

	defaultCounterparty(o);
	return;
    }

    private void defaultCounterparty(Operation o) {
	if (o.getCounterparty() == null) {
	    Pattern pattern = Pattern.compile(".*((LU|BE)\\d{14}).*");
	    Matcher matcher = pattern.matcher(o.getDetail());
	    if (matcher.find()) {
		o.setCounterparty(matcher.group(1));
	    } else {
		pattern = Pattern.compile(".*((LU|BE)\\d{2} \\d{4} \\d{4} \\d{4}).*");
		matcher = pattern.matcher(o.getDetail());
		if (matcher.find()) {
		    o.setCounterparty(matcher.group(1));
		}
	    }
	}

	if (o.getCounterparty() != null) {
	    o.setCounterparty(formatCounterparty(o.getCounterparty()));
	}
    }

    private String formatCounterparty(String group) {
	Pattern pattern = Pattern.compile(".*((LU|BE)\\d{2} \\d{4} \\d{4} \\d{4}).*");
	Matcher matcher = pattern.matcher(group);
	if (!matcher.find() && group.startsWith("BE")) {
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
