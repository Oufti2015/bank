package sst.bank.activities.e.labelling.labels;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedLabelProperties;
import sst.bank.model.container.BankContainer;

import java.math.BigDecimal;

public class SalaireLabel implements LabelActivity {
    @Override
    public void run() {
        InvertedLabelProperties counterparties = BankConfiguration.me().getPositifCounterpartiesLabelsMapping();
        BankContainer.me().operationsContainer().operations().stream()
                .filter(o -> o.getCategory().isDefaultCategory())
                .filter(o -> o.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .filter(o -> counterparties.map(o.getCounterparty()) != null)
                .forEach(o -> o.getLabels().add(counterparties.map(o.getCounterparty())));
    }
}
