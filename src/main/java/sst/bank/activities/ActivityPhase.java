package sst.bank.activities;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import sst.bank.activities.LifeCycle.Phase;

@Data
public class ActivityPhase {
    private Phase phase = null;
    private List<BankActivity> activities = new ArrayList<>();

    public ActivityPhase(Phase phase, BankActivity... activities) {
	this.phase = phase;
	for (int i = 0; i < activities.length; i++) {
	    this.activities.add(activities[i]);
	}
    }
}
