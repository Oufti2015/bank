package sst.bank.activities;

import java.util.Arrays;
import java.util.List;

import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.a.config.Configurator;
import sst.bank.activities.b.loading.OperationsLoader;
import sst.bank.activities.c.parsing.OperationFiller;
import sst.bank.activities.c.parsing.OperationsParser;
import sst.bank.activities.d.categorising.categories.DefaultCategory;
import sst.bank.activities.d.categorising.categories.MapCounterpartyToCategory;
import sst.bank.activities.d.categorising.categories.MapDetailToCategory;
import sst.bank.activities.d.categorising.categories.SalaireCategory;
import sst.bank.activities.d.categorising.categories.WithoutRuleCategory;
import sst.bank.activities.e.grouping.OperationsGrouper;
import sst.bank.activities.f.budgeting.OperationBudgeter;
import sst.bank.activities.g.printing.DefaultCategoriesPrinter;
import sst.bank.activities.g.printing.bycategory.OperationsPrinterByCategory;
import sst.bank.activities.g.printing.bydate.OperationsPrinterByDate;
import sst.bank.activities.h.saving.CategoriesSaver;
import sst.bank.activities.h.saving.OperationsSaver;

public class CompleteLifeCycle extends LifeCycle {

    public CompleteLifeCycle() {
	// TODO Auto-generated constructor stub
    }

    @Override
    protected List<ActivityPhase> createLifeCycle() {
	return Arrays.asList(
		new ActivityPhase(Phase.CONFIG,
			new CategoriesLoader(),
			new Configurator()),
		new ActivityPhase(Phase.LOADING,
			new OperationsLoader()),
		new ActivityPhase(Phase.PARSING,
			new OperationsParser(),
			new OperationFiller()),
		new ActivityPhase(Phase.CATEGORISING,
			new DefaultCategory(),
			new WithoutRuleCategory(),
			new SalaireCategory(),
			new MapCounterpartyToCategory(),
			new MapDetailToCategory()),
		new ActivityPhase(Phase.GROUPING,
			new OperationsGrouper()),
		new ActivityPhase(Phase.BUDGETING,
			new OperationBudgeter()),
		new ActivityPhase(Phase.PRINTING,
			new OperationsPrinterByDate(),
			new OperationsPrinterByCategory(),
			new DefaultCategoriesPrinter()),
		new ActivityPhase(Phase.SAVING,
			new OperationsSaver(),
			new CategoriesSaver()));
    }

}
