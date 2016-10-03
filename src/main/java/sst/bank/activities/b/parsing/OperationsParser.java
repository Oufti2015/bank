package sst.bank.activities.b.parsing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sst.bank.activities.BankActivity;
import sst.bank.activities.b.parsing.easyBanking.EasyBankingOperation;
import sst.bank.activities.b.parsing.easyBanking.EasyBankingParser;
import sst.bank.activities.b.parsing.pcBanking.PCBankingOperation;
import sst.bank.activities.b.parsing.pcBanking.PCBankingParser;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;
import sst.common.file.exceptions.ParserExceptions;
import sst.common.file.loader.FileLoader;
import sst.common.file.parser.GenericParser;

public class OperationsParser implements BankActivity {
    private List<Operation> list = new ArrayList<>();

    private List<Operation> parseFile(File file) {
	FileLoader fl = FileLoader.getInstance();
	try {
	    list = new ArrayList<>();
	    fl.process(file);
	} catch (IOException | ParserExceptions e) {
	    e.printStackTrace();
	    return null;
	}

	return list;
    }

    @Override
    public void run() {
	FileLoader fl = FileLoader.getInstance();
	PCBankingParser parser = new PCBankingParser();
	fl.addRecordFormat(parser,
		new GenericParser(PCBankingOperation.class).delimiter(";").removeQuotes(true).trim(true), parser);
	EasyBankingParser parserEasy = new EasyBankingParser();
	fl.addRecordFormat(parserEasy,
		new GenericParser(EasyBankingOperation.class).delimiter(";").removeQuotes(true).trim(true), parserEasy);

	File[] fileList = readInputDir();
	if (fileList != null) {
	    for (int i = 0; i < fileList.length; i++) {
		System.out.println("Reading " + fileList[i] + "...");
		List<Operation> parseFile = parseFile(fileList[i]);
		BankContainer.me().addOperations(parseFile);
	    }
	}
    }

    private File[] readInputDir() {
	File folder = new File("data");
	return folder.listFiles(new CsvFileFilter());
    }
}
