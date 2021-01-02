package sst.bank.activities.a.config;

import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.config.GsonUtils;
import sst.bank.main.OuftiBank;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;
import sst.textfile.InputTextFile;
import sst.textfile.InputTextFileImpl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CategoriesLoader implements BankActivity {
    List<Category> categories;

    @Override
    public void run() {
        Type listType = new TypeToken<ArrayList<Category>>() {
        }.getType();

        // JSON from file to Object
        try {
            InputTextFile textFile = new InputTextFileImpl(new File(BankConfiguration.me().getCategoriesJson()));
            categories = GsonUtils.buildGson().fromJson(textFile.oneLine(), listType);
            BankContainer.me().setCategories(categories);
        } catch (IOException e) {
            log.fatal("Cannot read file " + BankConfiguration.me().getCategoriesJson(), e);
            OuftiBank.eventBus.post(e);
        }
    }
}
