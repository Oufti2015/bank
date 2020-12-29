package sst.bank.activities.lifecycle;

import sst.bank.activities.ActivityPhase;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.a.config.Configurator;
import sst.bank.activities.b.loading.OperationsLoader;

import java.util.Arrays;
import java.util.List;

public class LoadLifeCycle extends LifeCycle {

    @Override
    protected List<ActivityPhase> createLifeCycle() {
        return Arrays.asList(
                new ActivityPhase(Phase.CONFIG,
                        new CategoriesLoader(),
                        new Configurator()),
                new ActivityPhase(Phase.LOADING,
                        new OperationsLoader()));
    }
}
