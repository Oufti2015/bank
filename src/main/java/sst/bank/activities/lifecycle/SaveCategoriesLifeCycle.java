package sst.bank.activities.lifecycle;

import java.util.Arrays;
import java.util.List;

import sst.bank.activities.ActivityPhase;
import sst.bank.activities.d.categorising.OperationCategoriser;
import sst.bank.activities.e.labelling.OperationLabeller;
import sst.bank.activities.g.grouping.OperationsGrouper;
import sst.bank.activities.h.budgeting.OperationBudgeter;
import sst.bank.activities.j.saving.CategoriesSaver;
import sst.bank.activities.j.saving.OperationsSaver;

public class SaveCategoriesLifeCycle extends LifeCycle {

    @Override
    protected List<ActivityPhase> createLifeCycle() {
	return Arrays.asList(
		new ActivityPhase(Phase.SAVING,
			new CategoriesSaver()),
		new ActivityPhase(Phase.CATEGORISING,
			new OperationCategoriser()),
		new ActivityPhase(Phase.LABELLING,
			new OperationLabeller()),
		new ActivityPhase(Phase.GROUPING,
			new OperationsGrouper()),
		new ActivityPhase(Phase.BUDGETING,
			new OperationBudgeter()),
		new ActivityPhase(Phase.SAVING,
			new OperationsSaver()));
    }
}
