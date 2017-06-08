package sst.bank.activities.i.saving;

import java.io.File;
import java.io.IOException;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.config.GsonUtils;
import sst.bank.main.OuftiBank;
import sst.bank.model.container.BankContainer;
import sst.common.file.output.OutputFile;

@Log4j
public class LabelsSaver implements BankActivity {

    @Override
    public void run() {
	try (OutputFile file = new OutputFile(new File(BankConfiguration.me().getLabelsJson()))) {
	    // Object to JSON in file
	    file.println(GsonUtils.buildGson().toJson(BankContainer.me().getLabels()));
	} catch (IOException e) {
	    log.fatal("Cannot save " + BankConfiguration.me().getLabelsJson(), e);
	    OuftiBank.eventBus.post(e);
	}
    }

}
