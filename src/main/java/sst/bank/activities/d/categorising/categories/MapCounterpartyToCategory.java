package sst.bank.activities.d.categorising.categories;

import lombok.extern.log4j.Log4j2;
import sst.bank.config.BankConfiguration;
import sst.bank.config.InvertedCategoryProperties;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

@Log4j2
public class MapCounterpartyToCategory implements CategoryActivity {

    private static InvertedCategoryProperties counterparties = null;

    @Override
    public void run() {
        counterparties = BankConfiguration.me().getCounterpartiesMapping();
    /*    BankContainer.me().operationsContainer().operations().stream()
                .filter(o -> counterparties.mapCategory(o.getCounterparty()) != null)
                .forEach(o -> o.setCategory(counterparties.mapCategory(o.getCounterparty())));
*/

        for (Operation o : BankContainer.me().operationsContainer().operations()) {
            final Category category = counterparties.mapCategory(o.getCounterparty());
            if (category != null) {
                o.setCategory(category);
            } else {
                log.debug("Cannot map counterparty " + o.getCounterparty());
            }
        }
    }
}
