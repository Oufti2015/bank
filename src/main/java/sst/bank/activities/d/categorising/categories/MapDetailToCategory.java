package sst.bank.activities.d.categorising.categories;

import lombok.extern.log4j.Log4j2;
import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedCategoryProperties;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

@Log4j2
public class MapDetailToCategory implements CategoryActivity {

    private static InvertedCategoryProperties details = null;

    @Override
    public void run() {
        details = BankConfiguration.me().getDetailsMapping();
        for (String key : details.keys()) {
             log.debug(" ------> Key=" + key + " - " + details.map(key));
            BankContainer.me().operationsContainer().operations().stream()
                    .filter(o -> o.getCategory() != null)
                    .filter(o -> o.getCategory().isDefaultCategory())
                    .filter(o -> o.getDetail().contains(key))
                    .forEach(o -> setCategory(o, key, details.mapCategory(key)));
        }
        // BankContainer.me().operations().stream()
        // .filter(o -> o.getCategory().isDefaultCategory())
        // .filter(o -> o.getDetail().contains("TOTAL 3007 RODANGE"))
        // .forEach(o -> System.out.println(o));
    }

    private void setCategory(Operation o, String key, Category map) {
        // System.out.println("Set " + map.getName() + " on " + o.getDetail());
        o.setCategory(map);
        o.setCounterparty(key);
    }
}
