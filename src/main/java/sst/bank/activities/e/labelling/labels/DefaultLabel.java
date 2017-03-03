package sst.bank.activities.e.labelling.labels;

import java.util.ArrayList;

import sst.bank.model.container.BankContainer;

public class DefaultLabel implements LabelActivity {

    @Override
    public void run() {
	BankContainer.me().operationsContainer().operations().stream()
		.forEach(o -> o.setLabels(new ArrayList<>()));
    }
}
