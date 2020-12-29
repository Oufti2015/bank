package sst.bank.activities;

import lombok.Data;
import sst.bank.activities.lifecycle.LifeCycle.Phase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class ActivityPhase {
    private Phase phase;
    private List<BankActivity> activities = new ArrayList<>();

    public ActivityPhase(Phase phase, BankActivity... activities) {
        this.phase = phase;
        Collections.addAll(this.activities, activities);
    }
}
