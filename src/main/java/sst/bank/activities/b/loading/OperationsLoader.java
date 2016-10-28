package sst.bank.activities.b.loading;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.model.container.BankContainer;
import sst.bank.model.transfer.TransferObject;

@Log4j
public class OperationsLoader implements BankActivity {

    @Override
    public void run() {
	TransferObject to = load();
	if (to != null) {
	    BankContainer container = BankContainer.me();
	    container.setCreationDate(to.getCreationDate());
	    container.setLastId(to.getLastId());
	    container.addOperations(to.getOperations()
		    .stream()
		    .map(o -> o.toOperation())
		    .collect(Collectors.toList()));

	    log.info("" + to.getOperations().size() + " operations loaded.");
	}
    }

    private TransferObject load() {
	ObjectMapper mapper = new ObjectMapper();
	TransferObject to = null;

	// JSON from file to Object
	try {
	    to = mapper.readValue(new File(BankConfiguration.OPERATIONS_JSON), TransferObject.class);
	} catch (IOException e) {
	    log.error("Cannot read file " + BankConfiguration.OPERATIONS_JSON, e);
	}

	return to;
    }
}
