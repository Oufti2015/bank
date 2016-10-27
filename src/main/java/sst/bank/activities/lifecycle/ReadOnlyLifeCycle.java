package sst.bank.activities.lifecycle;

import java.util.Arrays;
import java.util.List;

import sst.bank.activities.ActivityPhase;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.a.config.Configurator;
import sst.bank.activities.b.loading.OperationsLoader;
import sst.bank.activities.d.categorising.categories.DefaultCategory;
import sst.bank.activities.d.categorising.categories.MapCounterpartyToCategory;
import sst.bank.activities.d.categorising.categories.MapDetailToCategory;
import sst.bank.activities.d.categorising.categories.SalaireCategory;
import sst.bank.activities.d.categorising.categories.WithoutRuleCategory;
import sst.bank.activities.e.grouping.OperationsGrouper;

public class ReadOnlyLifeCycle extends LifeCycle {

    @Override
    protected List<ActivityPhase> createLifeCycle() {
	return Arrays.asList(
		new ActivityPhase(Phase.CONFIG,
			new CategoriesLoader(),
			new Configurator()),
		new ActivityPhase(Phase.LOADING,
			new OperationsLoader()),
		new ActivityPhase(Phase.CATEGORISING,
			new DefaultCategory(),
			new WithoutRuleCategory(),
			new SalaireCategory(),
			new MapCounterpartyToCategory(),
			new MapDetailToCategory()),
		new ActivityPhase(Phase.GROUPING,
			new OperationsGrouper()));
    }
}
