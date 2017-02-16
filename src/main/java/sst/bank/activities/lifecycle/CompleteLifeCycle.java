package sst.bank.activities.lifecycle;

import java.util.Arrays;
import java.util.List;

import sst.bank.activities.ActivityPhase;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.a.config.Configurator;
import sst.bank.activities.a.config.LabelsLoader;
import sst.bank.activities.b.loading.BeneficiaryCreator;
import sst.bank.activities.b.loading.BeneficiaryValidator;
import sst.bank.activities.b.loading.OperationsLoader;
import sst.bank.activities.c.parsing.OperationFiller;
import sst.bank.activities.c.parsing.OperationsParser;
import sst.bank.activities.d.categorising.OperationCategoriser;
import sst.bank.activities.e.labelling.OperationLabeller;
import sst.bank.activities.e.labelling.OperationsDefaulter;
import sst.bank.activities.f.grouping.OperationsGrouper;
import sst.bank.activities.g.budgeting.OperationBudgeter;
import sst.bank.activities.h.printing.DefaultCategoriesPrinter;
import sst.bank.activities.h.printing.bycategory.OperationsPrinterByCategory;
import sst.bank.activities.h.printing.bydate.OperationsPrinterByDate;
import sst.bank.activities.i.saving.CategoriesSaver;
import sst.bank.activities.i.saving.LabelsSaver;
import sst.bank.activities.i.saving.OperationsSaver;

public class CompleteLifeCycle extends LifeCycle {

    public CompleteLifeCycle() {
    }

    @Override
    protected List<ActivityPhase> createLifeCycle() {
	return Arrays.asList(
		new ActivityPhase(Phase.CONFIG,
			new CategoriesLoader(),
			new LabelsLoader(),
			new Configurator()),
		new ActivityPhase(Phase.LOADING,
			new OperationsLoader(),
			new BeneficiaryCreator(),
			new BeneficiaryValidator()),
		new ActivityPhase(Phase.PARSING,
			new OperationsParser(),
			new OperationFiller()),
		new ActivityPhase(Phase.CATEGORISING,
			new OperationCategoriser()),
		new ActivityPhase(Phase.LABELLING,
			new OperationLabeller(),
			new OperationsDefaulter()),
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
			new CategoriesSaver(),
			new LabelsSaver()));
    }

}
