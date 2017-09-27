package sst.bank.activities.i.printing.budget;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.model.container.BankContainer;
import sst.common.html.table.builders.CellInfo;
import sst.common.html.table.builders.IntoTableConverter;

/**
 * Created by steph on 22/06/17.
 */
public class UtilisationIntoTableConverterTest {
    @Before
    public void init() {
	new CategoriesLoader().run();
    }

    @Test
    public void convert() throws Exception {
	Double[] doubles = { 1.01, 1.02, 1.03 };
	IntoTableConverter tableConverter = new UtilisationIntoTableConverter(BankContainer.me().category("BANK"),
		doubles);
	CellInfo[] cellInfos = tableConverter.convert();

	assertEquals(5, cellInfos.length);
    }

}