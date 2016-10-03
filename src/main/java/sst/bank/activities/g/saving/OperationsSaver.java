package sst.bank.activities.g.saving;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.model.container.BankContainer;
import sst.bank.model.transfer.OperationTO;
import sst.bank.model.transfer.TransferObject;

public class OperationsSaver implements BankActivity {

    @Override
    public void run() {
	TransferObject to = fillTransferObject();
	save(to);
    }

    private TransferObject fillTransferObject() {
	TransferObject to = new TransferObject();
	to.setLastId(BankContainer.me().getLastId());
	// to.setOperations(BankContainer.me().operations());
	BankContainer.me().operations()
		.stream()
		.map(o -> OperationTO.fromOperation(o))
		.forEach(t -> to.getOperations().add(t));
	return to;
    }

    private void save(TransferObject to) {
	try {
	    ObjectMapper mapper = new ObjectMapper();

	    // Object to JSON in file
	    mapper.writeValue(new File(BankConfiguration.OPERATIONS_JSON), to);
	    // Object to JSON in file
	    mapper.writeValue(new File(BankConfiguration.OPERATIONS_TXT), to);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
