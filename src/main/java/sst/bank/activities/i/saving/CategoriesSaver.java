package sst.bank.activities.i.saving;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.main.OuftiBank;
import sst.bank.model.container.BankContainer;

@Log4j
public class CategoriesSaver implements BankActivity {

    @Override
    public void run() {
	try {
	    ObjectMapper mapper = new ObjectMapper();

	    // Object to JSON in file
	    mapper.writeValue(new File(BankConfiguration.me().getCategoriesJson()), BankContainer.me().getCategories());
	} catch (IOException e) {
	    log.fatal("Cannot save " + BankConfiguration.me().getCategoriesJson(), e);
	    OuftiBank.eventBus.post(e);
	}
    }

}
