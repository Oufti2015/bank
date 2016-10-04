package sst.bank.activities.c.parsing.visa;

import sst.common.file.loader.interfaces.RecordSelector;

public class VISASecondLineParser implements RecordSelector {

    @Override
    public boolean select(String record) {
	String[] array = record.split(";", -2);
	return array.length == 7 && !record.contains("cution;Date valeur;Montant;Devise du compte;D")
		&& array[5].equals("Taux de change##");
    }
}
