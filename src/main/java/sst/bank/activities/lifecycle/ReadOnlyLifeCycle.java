package sst.bank.activities.lifecycle;

import sst.bank.activities.ActivityPhase;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.a.config.Configurator;
import sst.bank.activities.a.config.LabelsLoader;
import sst.bank.activities.b.loading.OperationsLoader;
import sst.bank.activities.d.categorising.OperationCategoriser;
import sst.bank.activities.e.labelling.OperationLabeller;
import sst.bank.activities.g.grouping.OperationsGrouper;

import java.util.Arrays;
import java.util.List;

public class ReadOnlyLifeCycle extends LifeCycle {

    @Override
    protected List<ActivityPhase> createLifeCycle() {
        return Arrays.asList(
                new ActivityPhase(Phase.CONFIG,
                        new CategoriesLoader(),
                        new LabelsLoader(),
                        new Configurator()),
                new ActivityPhase(Phase.LOADING,
                        new OperationsLoader()),
                new ActivityPhase(Phase.CATEGORISING,
                        new OperationCategoriser()),
                new ActivityPhase(Phase.LABELLING,
                        new OperationLabeller()),
                new ActivityPhase(Phase.GROUPING,
                        new OperationsGrouper()));
    }
}
