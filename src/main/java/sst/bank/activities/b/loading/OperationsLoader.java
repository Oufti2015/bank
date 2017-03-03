package sst.bank.activities.b.loading;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.main.OuftiBank;
import sst.bank.model.container.BankContainer;
import sst.bank.model.container.ContainerInterface;
import sst.bank.model.transfer.TransferObject;

@Log4j
public class OperationsLoader implements BankActivity {

    @Override
    public void run() {
	TransferObject to = load();
	if (to != null) {
	    ContainerInterface container = BankContainer.me();
	    container.initCreationDate(to.getCreationDate());
	    container.initLastId(to.getLastId());
	    container.operationsContainer().addOperations(to.getOperations()
		    .stream()
		    .map(o -> o.toOperation())
		    .collect(Collectors.toList()));
	    container.addAllBeneficiaries(to.getBeneficiaries());

	    log.info("" + to.getOperations().size() + " operations loaded.");
	    log.info("" + to.getBeneficiaries().size() + " beneficiariess loaded.");
	}
    }

    private TransferObject load() {
	ObjectMapper mapper = new ObjectMapper();
	TransferObject to = null;

	// JSON from file to Object
	try {
	    to = mapper.readValue(new File(BankConfiguration.me().getOperationsJson()), TransferObject.class);
	} catch (IOException e) {
	    log.fatal("Cannot read file " + BankConfiguration.me().getOperationsJson(), e);
	    OuftiBank.eventBus.post(e);
	}

	return to;
    }
}
