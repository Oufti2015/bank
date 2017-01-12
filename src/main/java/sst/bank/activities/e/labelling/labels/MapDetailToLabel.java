package sst.bank.activities.e.labelling.labels;

import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedLabelProperties;
import sst.bank.model.Operation;
import sst.bank.model.OperationLabel;
import sst.bank.model.container.BankContainer;

public class MapDetailToLabel implements LabelActivity {

    private static InvertedLabelProperties details = null;

    @Override
    public void run() {
	details = BankConfiguration.me().getDetailsLabelsMapping();
	for (String key : details.keySet()) {
	    // System.out.println(" ------> Key=" + key + " - " +
	    // details.map(key));
	    BankContainer.me().operations().stream()
		    .filter(o -> o.getCategory().isDefaultCategory())
		    .filter(o -> o.getDetail().contains(key))
		    .forEach(o -> setCategory(o, key, details.map(key)));
	}
	// BankContainer.me().operations().stream()
	// .filter(o -> o.getCategory().isDefaultCategory())
	// .filter(o -> o.getDetail().contains("TOTAL 3007 RODANGE"))
	// .forEach(o -> System.out.println(o));
    }

    private void setCategory(Operation o, String key, OperationLabel map) {
	// System.out.println("Set " + map.getName() + " on " + o.getDetail());
	o.getLabels().add(map);
    }
}
