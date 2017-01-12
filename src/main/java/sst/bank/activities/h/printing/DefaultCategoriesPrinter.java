package sst.bank.activities.h.printing;

import java.io.File;
import java.io.IOException;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.config.BankConfiguration;
import sst.bank.main.OuftiBank;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;
import sst.common.file.output.OutputFile;

@Log4j
public class DefaultCategoriesPrinter implements BankActivity {
    private static final String DATA_DEFAULT_TXT = BankConfiguration.me().getOutputDir()
	    + File.separator
	    + "default.txt";

    @Override
    public void run() {
	try (OutputFile output = new OutputFile(new File(DATA_DEFAULT_TXT))) {

	    BankContainer.me().operations().stream().filter(o -> o.getCategory().isDefaultCategory())
		    .forEach(o -> print(output, o));
	} catch (IOException e) {
	    log.fatal("Cannot open " + DATA_DEFAULT_TXT, e);
	    OuftiBank.eventBus.post(e);
	}

    }

    private void print(OutputFile output, Operation operation) {
	try {
	    output.println(
		    operation.getBankId() + "\t" + operation.getFortisId() + "\t"
			    + operation.getDetail().replaceAll("\t", "[TAB]"));
	} catch (IOException e) {
	    log.fatal("Cannot write to " + DATA_DEFAULT_TXT, e);
	    OuftiBank.eventBus.post(e);
	}
    }

}
