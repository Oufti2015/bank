package sst.bank.activities.b.parsing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sst.bank.activities.BankActivity;
import sst.bank.activities.b.parsing.easyBanking.EasyBankingOperation;
import sst.bank.activities.b.parsing.easyBanking.EasyBankingParser;
import sst.bank.activities.b.parsing.pcBanking.PCBankingOperation;
import sst.bank.activities.b.parsing.pcBanking.PCBankingParser;
import sst.bank.activities.b.parsing.visa.VISAFirstLine;
import sst.bank.activities.b.parsing.visa.VISAFirstLineParser;
import sst.bank.activities.b.parsing.visa.VISASecondLine;
import sst.bank.activities.b.parsing.visa.VISASecondLineParser;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;
import sst.common.file.exceptions.ParserExceptions;
import sst.common.file.loader.FileLoader;
import sst.common.file.parser.GenericParser;

public class OperationsParser implements BankActivity {
    private static Logger logger = Logger.getLogger(OperationsParser.class);

    private List<Operation> list = new ArrayList<>();

    private List<Operation> parseFile(File file) {
	FileLoader fl = FileLoader.getInstance();
	try {
	    list = new ArrayList<>();
	    fl.process(file);
	} catch (IOException | ParserExceptions e) {
	    logger.error("Cannot read " + file, e);
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
		logger.info("Reading " + file + "...");
		List<Operation> parseFile = parseFile(file);
		BankContainer.me().addOperations(parseFile);
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
	File folder = new File("data");
	return folder.listFiles(new CsvFileFilter());
    }
}
