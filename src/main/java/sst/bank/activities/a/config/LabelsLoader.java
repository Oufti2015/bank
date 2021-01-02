package sst.bank.activities.a.config;

import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.config.GsonUtils;
import sst.bank.main.OuftiBank;
import sst.bank.model.OperationLabel;
import sst.bank.model.container.BankContainer;
import sst.textfile.InputTextFile;
import sst.textfile.InputTextFileImpl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class LabelsLoader implements BankActivity {
    List<OperationLabel> labels;

    @Override
    public void run() {
        Type listType = new TypeToken<ArrayList<OperationLabel>>() {
        }.getType();

        // JSON from file to Object
        try {
            InputTextFile textFile = new InputTextFileImpl(new File(BankConfiguration.me().getLabelsJson()));
            labels = GsonUtils.buildGson().fromJson(textFile.oneLine(), listType);
            BankContainer.me().setLabels(labels);
        } catch (IOException e) {
            log.fatal("Cannot read file " + BankConfiguration.me().getLabelsJson(), e);
            OuftiBank.eventBus.post(e);
        }
    }
}
