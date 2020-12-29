package sst.bank.activities.e.labelling.labels;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedLabelProperties;
import sst.bank.model.container.BankContainer;

public class MapCounterpartyToLabel implements LabelActivity {
    @Override
    public void run() {
        InvertedLabelProperties counterparties = BankConfiguration.me().getCounterpartiesLabelsMapping();
        BankContainer.me().operationsContainer().operations().stream()
                .filter(o -> counterparties.map(o.getCounterparty()) != null)
                .forEach(o -> o.getLabels().add(counterparties.map(o.getCounterparty())));
    }
}
