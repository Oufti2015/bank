package sst.bank.activities.lifecycle;

import sst.bank.activities.ActivityPhase;
import sst.bank.activities.j.saving.OperationsSaver;

import java.util.Arrays;
import java.util.List;

public class SaveBeneficiariesLifeCycle extends LifeCycle {

    @Override
    protected List<ActivityPhase> createLifeCycle() {
        return Arrays.asList(
                new ActivityPhase(Phase.SAVING,
                        new OperationsSaver()));
    }

}
