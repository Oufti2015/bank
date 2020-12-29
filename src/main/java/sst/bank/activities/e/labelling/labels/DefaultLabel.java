package sst.bank.activities.e.labelling.labels;

import sst.bank.model.container.BankContainer;

import java.util.ArrayList;

public class DefaultLabel implements LabelActivity {

    @Override
    public void run() {
        BankContainer.me().operationsContainer().operations().stream()
                .forEach(o -> o.setLabels(new ArrayList<>()));
    }
}
