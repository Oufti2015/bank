package sst.bank.activities.a.parsing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sst.bank.activities.Activity;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;
import sst.common.file.exceptions.ParserExceptions;
import sst.common.file.loader.FileLoader;
import sst.common.file.loader.interfaces.RecordFormatter;
import sst.common.file.loader.interfaces.RecordSelector;
import sst.common.file.parser.GenericParser;

public class OperationsParser implements RecordSelector, RecordFormatter, Activity {
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
    public void format(Object recordParsed) {
	Operation operation = (Operation) recordParsed;
	if (null != operation.getId()) {
	    list.add(operation);
	}
    }

    @Override
    public boolean select(String record) {
	return (null != record && !record.contains("ANNEE"));
    }

    @Override
    public void run() {
	FileLoader fl = FileLoader.getInstance();
	fl.addRecordFormat(this, new GenericParser(Operation.class).delimiter(";").removeQuotes(true).trim(true), this);

	File[] fileList = readInputDir();
	for (int i = 0; i < fileList.length; i++) {
	    System.out.println("Reading " + fileList[i] + "...");
	    List<Operation> parseFile = parseFile(fileList[i]);
	    BankContainer.me().addOperations(parseFile);
	}
    }

    private File[] readInputDir() {
	File folder = new File("data");
	return folder.listFiles(new CsvFileFilter());
    }
}
