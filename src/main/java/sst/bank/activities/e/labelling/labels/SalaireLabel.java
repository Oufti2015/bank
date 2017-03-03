package sst.bank.activities.e.labelling.labels;

import java.math.BigDecimal;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedLabelProperties;
import sst.bank.model.container.BankContainer;

public class SalaireLabel implements LabelActivity {

    private static InvertedLabelProperties counterparties = null;

    @Override
    public void run() {
	counterparties = BankConfiguration.me().getPositifCounterpartiesLabelsMapping();
	BankContainer.me().operationsContainer().operations().stream()
		.filter(o -> o.getCategory().isDefaultCategory())
		.filter(o -> o.getAmount().compareTo(BigDecimal.ZERO) > 0)
		.filter(o -> counterparties.map(o.getCounterparty()) != null)
		.forEach(o -> o.getLabels().add(counterparties.map(o.getCounterparty())));
    }
}
