package sst.bank.activities.a.loading;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.model.container.BankContainer;
import sst.bank.model.transfer.TransferObject;

public class OperationsLoader implements BankActivity {

    @Override
    public void run() {
	TransferObject to = load();
	if (to != null) {
	    BankContainer.me().setLastId(to.getLastId());
	    BankContainer.me()
		    .addOperations(to.getOperations()
			    .stream()
			    .map(o -> o.toOperation())
			    .collect(Collectors.toList()));

	    System.out.println("" + to.getOperations().size() + " operations loaded.");
	}
    }

    private TransferObject load() {
	ObjectMapper mapper = new ObjectMapper();
	TransferObject to = null;

	// JSON from file to Object
	try {
	    to = mapper.readValue(new File(BankConfiguration.OPERATIONS_JSON), TransferObject.class);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return to;
    }
}
