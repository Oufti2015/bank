package sst.bank.activities.e.labelling.labels;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedLabelProperties;
import sst.bank.model.container.BankContainer;

public class WithoutRuleLabel implements LabelActivity {
    @Override
    public void run() {
        InvertedLabelProperties ids = BankConfiguration.me().getIdLabelsMapping();
        BankContainer.me().operationsContainer().operations().stream()
                .filter(o -> o.getFortisId() != null)
                .filter(o -> ids.map(o.getFortisId()) != null)
                .forEach(o -> o.getLabels().add(ids.map(o.getFortisId())));
    }
}
