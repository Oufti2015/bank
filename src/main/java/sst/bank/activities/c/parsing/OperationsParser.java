package sst.bank.activities.c.parsing;

import lombok.extern.log4j.Log4j2;
import sst.bank.activities.BankActivity;
import sst.bank.activities.c.parsing.easybanking.EasyBankingOperation;
import sst.bank.activities.c.parsing.easybanking.EasyBankingParser;
import sst.bank.activities.c.parsing.pcbanking.PCBankingOperation;
import sst.bank.activities.c.parsing.pcbanking.PCBankingParser;
import sst.bank.activities.c.parsing.visa.*;
import sst.bank.config.BankConfiguration;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;
import sst.common.file.exceptions.ParserExceptions;
import sst.common.file.loader.FileLoader;
import sst.common.file.parser.GenericParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class OperationsParser implements BankActivity {
    private List<Operation> list = new ArrayList<>();

    private List<Operation> parseFile(File file) {
        FileLoader fl = FileLoader.getInstance();
        try {
            list = new ArrayList<>();
            fl.process(file);
        } catch (IOException | ParserExceptions e) {
            log.error("Cannot read " + file, e);
            return new ArrayList<>();
        }

        return list;
    }

    @Override
    public void run() {
        FileLoader fl = FileLoader.getInstance();
        addPCBankingParser(fl);
        // addEasyBankingParser(fl);
        // addVISAParsers(fl);
        addVISA2Parsers(fl);

        File[] fileList = readInputDir();
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                File file = fileList[i];
                log.info("Reading " + file + "...");
                List<Operation> parseFile = parseFile(file);
                BankContainer.me().operationsContainer().addAll(parseFile);
                file.renameTo(new File(archiveFileName(file)));
            }
        }
    }

    private void addVISA2Parsers(FileLoader fl) {
        VISA2Parser visaParser1 = new VISA2Parser();
        fl.addRecordFormat(visaParser1,
                new GenericParser(VISA2Operation.class).delimiter(";").removeQuotes(true).trim(true), visaParser1);
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
