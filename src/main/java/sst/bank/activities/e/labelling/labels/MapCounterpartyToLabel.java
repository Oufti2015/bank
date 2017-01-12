package sst.bank.activities.e.labelling.labels;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedLabelProperties;
import sst.bank.model.container.BankContainer;

public class MapCounterpartyToLabel implements LabelActivity {

    private static InvertedLabelProperties counterparties = null;

    @Override
    public void run() {
	counterparties = BankConfiguration.me().getCounterpartiesLabelsMapping();
	BankContainer.me().operations().stream()
		.filter(o -> counterparties.map(o.getCounterparty()) != null)
		.forEach(o -> o.getLabels().add(counterparties.map(o.getCounterparty())));
    }
}
