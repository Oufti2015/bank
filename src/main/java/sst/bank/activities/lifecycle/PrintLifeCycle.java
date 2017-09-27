package sst.bank.activities.lifecycle;

import java.util.Arrays;
import java.util.List;

import sst.bank.activities.ActivityPhase;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.a.config.Configurator;
import sst.bank.activities.a.config.LabelsLoader;
import sst.bank.activities.b.loading.BeneficiaryValidator;
import sst.bank.activities.b.loading.OperationsLoader;
import sst.bank.activities.c.parsing.OperationFiller;
import sst.bank.activities.c.parsing.OperationsParser;
import sst.bank.activities.d.categorising.OperationCategoriser;
import sst.bank.activities.e.labelling.OperationLabeller;
import sst.bank.activities.e.labelling.OperationsDefaulter;
import sst.bank.activities.f.projectsSelecting.ProjectsSelecter;
import sst.bank.activities.g.grouping.OperationsGrouper;
import sst.bank.activities.h.budgeting.OperationBudgeter;
import sst.bank.activities.i.printing.DefaultCategoriesPrinter;
import sst.bank.activities.i.printing.budget.BudgetPrinterActivity;
import sst.bank.activities.i.printing.bycategory.OperationsPrinterByCategory;
import sst.bank.activities.i.printing.bydate.OperationsPrinterByDate;
import sst.bank.activities.j.saving.CategoriesSaver;
import sst.bank.activities.j.saving.LabelsSaver;
import sst.bank.activities.j.saving.OperationsSaver;

public class PrintLifeCycle extends LifeCycle {

    public PrintLifeCycle() {
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
			// new BeneficiaryCreator(),
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
		new ActivityPhase(Phase.PROJECTS,
			new ProjectsSelecter()),
		new ActivityPhase(Phase.BUDGETING,
			new OperationBudgeter()),
		new ActivityPhase(Phase.PRINTING,
			new OperationsPrinterByDate(),
			new OperationsPrinterByCategory(),
			new DefaultCategoriesPrinter(),
			new BudgetPrinterActivity()),
		new ActivityPhase(Phase.SAVING,
			new OperationsSaver(),
			new CategoriesSaver(),
			new LabelsSaver()));
    }

}
