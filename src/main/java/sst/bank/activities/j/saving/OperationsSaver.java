package sst.bank.activities.j.saving;

import lombok.extern.log4j.Log4j2;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.config.GsonUtils;
import sst.bank.main.OuftiBank;
import sst.bank.model.container.BankContainer;
import sst.bank.model.transfer.OperationTO;
import sst.bank.model.transfer.TransferObject;
import sst.common.file.output.OutputFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Log4j2
public class OperationsSaver implements BankActivity {
    @Override
    public void run() {
        TransferObject to = fillTransferObject();
        save(to);
    }

    private TransferObject fillTransferObject() {
        TransferObject to = new TransferObject();
        to.setLastId(BankContainer.me().lastId());
        to.setCreationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        BankContainer.me().operationsContainer().operations()
                .stream()
                .map(OperationTO::fromOperation)
                .forEach(t -> to.getOperations().add(t));
        to.getBeneficiaries().addAll(BankContainer.me().beneficiaries());
        to.getProjects().addAll(BankContainer.me().projectsContainer().projects());

        return to;
    }

    private void save(TransferObject to) {
        try (OutputFile file = new OutputFile(new File(BankConfiguration.me().getOperationsJson()))) {
            // Object to JSON in file
            file.println(GsonUtils.buildGson().toJson(to));
        } catch (IOException e) {
            log.fatal("Cannot write file " + BankConfiguration.me().getOperationsJson(), e);
            OuftiBank.eventBus.post(e);
        }
    }
}
