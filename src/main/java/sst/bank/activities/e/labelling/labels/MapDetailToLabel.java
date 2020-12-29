package sst.bank.activities.e.labelling.labels;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedLabelProperties;
import sst.bank.model.Operation;
import sst.bank.model.OperationLabel;
import sst.bank.model.container.BankContainer;

public class MapDetailToLabel implements LabelActivity {
    @Override
    public void run() {
        InvertedLabelProperties details = BankConfiguration.me().getDetailsLabelsMapping();
        for (String key : details.keySet()) {
            for (Operation operation : BankContainer.me().operationsContainer().operations()) {
                if (operation.getDetail().contains(key)) {
                    setCategory(operation, details.map(key));
                }
            }
        }
    }

    private void setCategory(Operation o, OperationLabel map) {
        o.getLabels().add(map);
    }
}
