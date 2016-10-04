package sst.bank.activities.a.loading;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;

public class CategoriesLoader implements BankActivity {
    private static Logger logger = Logger.getLogger(CategoriesLoader.class);
    List<Category> categories;

    @Override
    public void run() {
	ObjectMapper mapper = new ObjectMapper();

	// JSON from file to Object
	try {
	    categories = mapper.readValue(new File(BankConfiguration.CATEGORIES_JSON),
		    new TypeReference<List<Category>>() {
		    });
	    BankContainer.me().setCategories(categories);
	} catch (IOException e) {
	    logger.error("Cannot read file " + BankConfiguration.CATEGORIES_JSON, e);
	}
    }
}
