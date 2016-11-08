package sst.bank.activities.lifecycle;

import java.util.Arrays;
import java.util.List;

import sst.bank.activities.ActivityPhase;
import sst.bank.activities.d.categorising.categories.DefaultCategory;
import sst.bank.activities.d.categorising.categories.MapCounterpartyToCategory;
import sst.bank.activities.d.categorising.categories.MapDetailToCategory;
import sst.bank.activities.d.categorising.categories.SalaireCategory;
import sst.bank.activities.d.categorising.categories.WithoutRuleCategory;
import sst.bank.activities.e.grouping.OperationsGrouper;
import sst.bank.activities.f.budgeting.OperationBudgeter;
import sst.bank.activities.h.saving.CategoriesSaver;
import sst.bank.activities.h.saving.OperationsSaver;

public class SaveCategoriesLifeCycle extends LifeCycle {

    @Override
    protected List<ActivityPhase> createLifeCycle() {
	return Arrays.asList(
		new ActivityPhase(Phase.SAVING,
			new CategoriesSaver()),
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
		new ActivityPhase(Phase.SAVING,
			new OperationsSaver()));
    }
}
