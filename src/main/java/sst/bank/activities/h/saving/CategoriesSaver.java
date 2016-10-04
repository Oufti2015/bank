package sst.bank.activities.h.saving;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.model.container.BankContainer;

public class CategoriesSaver implements BankActivity {

    @Override
    public void run() {
	try {
	    ObjectMapper mapper = new ObjectMapper();

	    // Object to JSON in file
	    mapper.writeValue(new File(BankConfiguration.CATEGORIES_JSON), BankContainer.me().getCategories());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
