package sst.bank.activities.a.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;

@Log4j
public class CategoriesLoader implements BankActivity {
    List<Category> categories;

    @Override
    public void run() {
	ObjectMapper mapper = new ObjectMapper();

	// JSON from file to Object
	try {
	    categories = mapper.readValue(new File(BankConfiguration.me().getCategoriesJson()),
		    new TypeReference<List<Category>>() {
		    });
	    BankContainer.me().setCategories(categories);
	} catch (IOException e) {
	    log.fatal("Cannot read file " + BankConfiguration.me().getCategoriesJson(), e);
	    System.exit(-1);
	}
    }
}
