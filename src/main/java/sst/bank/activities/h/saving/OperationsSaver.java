package sst.bank.activities.h.saving;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.main.OuftiBank;
import sst.bank.model.container.BankContainer;
import sst.bank.model.transfer.OperationTO;
import sst.bank.model.transfer.TransferObject;

@Log4j
public class OperationsSaver implements BankActivity {
    @Override
    public void run() {
	TransferObject to = fillTransferObject();
	save(to);
    }

    private TransferObject fillTransferObject() {
	TransferObject to = new TransferObject();
	to.setLastId(BankContainer.me().getLastId());
	to.setCreationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
	// to.setOperations(BankContainer.me().operations());
	BankContainer.me().operations()
		.stream()
		.map(o -> OperationTO.fromOperation(o))
		.forEach(t -> to.getOperations().add(t));
	BankContainer.me().beneficiaries()
		.stream()
		.forEach(t -> to.getBeneficiaries().add(t));

	return to;
    }

    private void save(TransferObject to) {
	ObjectMapper mapper = new ObjectMapper();
	try {
	    // Object to JSON in file
	    mapper.writeValue(new File(BankConfiguration.me().getOperationsJson()), to);
	} catch (IOException e) {
	    log.fatal("Cannot write file " + BankConfiguration.me().getOperationsJson(), e);
	    OuftiBank.eventBus.post(e);
	}
    }
}
