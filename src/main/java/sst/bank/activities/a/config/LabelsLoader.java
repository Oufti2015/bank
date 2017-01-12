package sst.bank.activities.a.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.main.OuftiBank;
import sst.bank.model.OperationLabel;
import sst.bank.model.container.BankContainer;

@Log4j
public class LabelsLoader implements BankActivity {
    List<OperationLabel> labels;

    @Override
    public void run() {
	ObjectMapper mapper = new ObjectMapper();

	// JSON from file to Object
	try {
	    labels = mapper.readValue(new File(BankConfiguration.me().getLabelsJson()),
		    new TypeReference<List<OperationLabel>>() {
		    });
	    BankContainer.me().setLabels(labels);
	} catch (IOException e) {
	    log.fatal("Cannot read file " + BankConfiguration.me().getLabelsJson(), e);
	    OuftiBank.eventBus.post(e);
	}
    }
}
