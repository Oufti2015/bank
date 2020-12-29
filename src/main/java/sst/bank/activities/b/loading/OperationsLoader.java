package sst.bank.activities.b.loading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.config.GsonUtils;
import sst.bank.main.OuftiBank;
import sst.bank.model.Operation;
import sst.bank.model.Project;
import sst.bank.model.container.BankContainer;
import sst.bank.model.container.ContainerInterface;
import sst.bank.model.transfer.OperationTO;
import sst.bank.model.transfer.TransferObject;
import sst.textfile.InputTextFile;
import sst.textfile.InputTextFileImpl;

@Log4j
public class OperationsLoader implements BankActivity {

    @Override
    public void run() {
	TransferObject to = load();
	if (to != null) {
	    ContainerInterface container = BankContainer.me();
	    container.initCreationDate(to.getCreationDate());
	    container.initLastId(to.getLastId());
	    container.operationsContainer().addAll(to.getOperations()
		    .stream()
		    .map(OperationTO::toOperation)
		    .collect(Collectors.toList()));
	    container.addAllBeneficiaries(to.getBeneficiaries());
	    container.projectsContainer().addAll(to.getProjects());

	    for (Project project : container.projectsContainer().projects()) {
		List<Operation> realOperations = new ArrayList<>();
		for (Operation op : project.getOperations()) {
		    String id = (op.getFortisId() != null) ? op.getFortisId() : op.getBankId().toString();
		    realOperations.add(container.operationsContainer().get(id));
		}
		project.getOperations().clear();
		project.getOperations().addAll(realOperations);
	    }

	    log.info("" + to.getOperations().size() + " operations loaded.");
	    log.info("" + to.getBeneficiaries().size() + " beneficiaries loaded.");
	    log.info("" + to.getProjects().size() + " projects loaded.");
	}
    }

    private TransferObject load() {
	TransferObject to = null;

	// JSON from file to Object
	try {
	    InputTextFile textFile = new InputTextFileImpl(new File(BankConfiguration.me().getOperationsJson()));
	    to = GsonUtils.buildGson().fromJson(textFile.oneLine(), TransferObject.class);
	} catch (IOException e) {
	    log.fatal("Cannot read file " + BankConfiguration.me().getOperationsJson(), e);
	    OuftiBank.eventBus.post(e);
	}

	return to;
    }
}
