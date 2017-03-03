package sst.bank.activities.c.parsing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.BankActivity;
import sst.bank.activities.c.parsing.easyBanking.EasyBankingOperation;
import sst.bank.activities.c.parsing.easyBanking.EasyBankingParser;
import sst.bank.activities.c.parsing.pcBanking.PCBankingOperation;
import sst.bank.activities.c.parsing.pcBanking.PCBankingParser;
import sst.bank.activities.c.parsing.visa.VISAFirstLine;
import sst.bank.activities.c.parsing.visa.VISAFirstLineParser;
import sst.bank.activities.c.parsing.visa.VISASecondLine;
import sst.bank.activities.c.parsing.visa.VISASecondLineParser;
import sst.bank.config.BankConfiguration;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;
import sst.common.file.exceptions.ParserExceptions;
import sst.common.file.loader.FileLoader;
import sst.common.file.parser.GenericParser;

@Log4j
public class OperationsParser implements BankActivity {
    private List<Operation> list = new ArrayList<>();

    private List<Operation> parseFile(File file) {
	FileLoader fl = FileLoader.getInstance();
	try {
	    list = new ArrayList<>();
	    fl.process(file);
	} catch (IOException | ParserExceptions e) {
	    log.error("Cannot read " + file, e);
	    return null;
	}

	return list;
    }

    @Override
    public void run() {
	FileLoader fl = FileLoader.getInstance();
	addPCBankingParser(fl);
	addEasyBankingParser(fl);
	addVISAParsers(fl);

	File[] fileList = readInputDir();
	if (fileList != null) {
	    for (int i = 0; i < fileList.length; i++) {
		File file = fileList[i];
		log.info("Reading " + file + "...");
		List<Operation> parseFile = parseFile(file);
		BankContainer.me().operationsContainer().addOperations(parseFile);
		file.renameTo(new File(archiveFileName(file)));
	    }
	}
    }

    private void addVISAParsers(FileLoader fl) {
	VISAFirstLineParser visaParser1 = new VISAFirstLineParser();
	VISASecondLineParser visaParser2 = new VISASecondLineParser();
	fl.addRecordFormat(visaParser1,
		new GenericParser(VISAFirstLine.class).delimiter(";").removeQuotes(true).trim(true), visaParser1);
	fl.addRecordFormat(visaParser2,
		new GenericParser(VISASecondLine.class).delimiter(";").removeQuotes(true).trim(true), visaParser1);
    }

    private void addEasyBankingParser(FileLoader fl) {
	EasyBankingParser parserEasy = new EasyBankingParser();
	fl.addRecordFormat(parserEasy,
		new GenericParser(EasyBankingOperation.class).delimiter(";").removeQuotes(true).trim(true), parserEasy);
    }

    private void addPCBankingParser(FileLoader fl) {
	PCBankingParser parser = new PCBankingParser();
	fl.addRecordFormat(parser,
		new GenericParser(PCBankingOperation.class).delimiter(";").removeQuotes(true).trim(true), parser);
    }

    private String archiveFileName(File file) {
	return file.getParentFile() + File.separator + "archive" + File.separator
		+ file.getName();
    }

    private File[] readInputDir() {
	File folder = new File(BankConfiguration.me().getInputDir());
	return folder.listFiles(new CsvFileFilter());
    }
}
