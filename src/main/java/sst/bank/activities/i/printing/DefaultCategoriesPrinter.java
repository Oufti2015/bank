package sst.bank.activities.i.printing;

import lombok.extern.log4j.Log4j2;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.main.OuftiBank;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;
import sst.common.file.output.OutputFile;

import java.io.File;
import java.io.IOException;

@Log4j2
public class DefaultCategoriesPrinter implements BankActivity {
    private static final String DATA_DEFAULT_TXT = BankConfiguration.me().getOutputDir()
            + File.separator
            + "default.txt";

    @Override
    public void run() {
        try (OutputFile output = new OutputFile(new File(DATA_DEFAULT_TXT))) {

            BankContainer.me().operationsContainer().operations().stream()
                    .filter(o -> o.getCategory().isDefaultCategory())
                    .forEach(o -> print(output, o));
        } catch (IOException e) {
            log.fatal("Cannot open " + DATA_DEFAULT_TXT, e);
            OuftiBank.eventBus.post(e);
        }

    }

    private void print(OutputFile output, Operation operation) {
        try {
            output.println(operation.getBankId() + "\t" + operation.getFortisId() + "\t" + operation.getDetail().replace("\t", "[TAB]"));
        } catch (IOException e) {
            log.fatal("Cannot write to " + DATA_DEFAULT_TXT, e);
            OuftiBank.eventBus.post(e);
        }
    }

}
