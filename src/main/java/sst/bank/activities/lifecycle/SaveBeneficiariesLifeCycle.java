package sst.bank.activities.lifecycle;

import java.util.Arrays;
import java.util.List;

import sst.bank.activities.ActivityPhase;
import sst.bank.activities.i.saving.OperationsSaver;

public class SaveBeneficiariesLifeCycle extends LifeCycle {

    @Override
    protected List<ActivityPhase> createLifeCycle() {
	return Arrays.asList(
		new ActivityPhase(Phase.SAVING,
			new OperationsSaver()));
    }

}
