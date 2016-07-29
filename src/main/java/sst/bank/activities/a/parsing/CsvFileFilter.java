package sst.bank.activities.a.parsing;

import java.io.File;
import java.io.FileFilter;

class CsvFileFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {
	return (pathname.getName().toLowerCase().endsWith("csv"));
    }
}
