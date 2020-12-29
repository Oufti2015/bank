package sst.bank.main.tools;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.j.saving.LabelsSaver;
import sst.bank.model.OperationLabel;
import sst.bank.model.container.BankContainer;

import java.util.Set;
import java.util.TreeSet;

@Log4j
public class CreateLabels {

    public static void main(String[] args) {
        new CreateLabels().create();
    }

    private void create() {
        Set<OperationLabel> labels = new TreeSet<>();

        labels.add(createLabel("Agility", "AGILITY"));
        labels.add(createLabel("Alice", "ALICE"));
        labels.add(createLabel("Anne", "ANNE"));
        labels.add(createLabel("Aviva", "AVIVA"));
        labels.add(createLabel("Frais", "CHARGES"));
        labels.add(createLabel("Nettoyage", "CLEANING"));
        labels.add(createLabel("Clearstream", "CLEARSTREAM"));
        labels.add(createLabel("V�tements", "CLOTHING"));
        labels.add(createLabel("Contr�le", "CONTROL"));
        labels.add(createLabel("M�decins", "DOCTORS"));
        labels.add(createLabel("Electricit�", "ELECTRICITY"));
        labels.add(createLabel("Ergo", "ERGO"));
        labels.add(createLabel("Nourriture", "FOOD"));
        labels.add(createLabel("Carburant", "FUEL"));
        labels.add(createLabel("Financement", "FUNDING"));
        labels.add(createLabel("Chauffage", "HEATING"));
        labels.add(createLabel("Vacances", "HOLIDAYS"));
        labels.add(createLabel("Hugla", "HUGLA"));
        labels.add(createLabel("Mutuelle", "INSURANCE"));
        labels.add(createLabel("Logos", "LOGOS"));
        labels.add(createLabel("Grandes Surfaces", "MALLS"));
        labels.add(createLabel("Parking", "PARKING"));
        labels.add(createLabel("Paypal", "PAYPAL"));
        labels.add(createLabel("T�l�phone", "PHONE"));
        labels.add(createLabel("Restaurant", "RESTAURANT"));
        labels.add(createLabel("Sport", "SPORT"));
        labels.add(createLabel("Abonnement", "SUBSCRIPTION"));
        labels.add(createLabel("Pneus", "TIRES"));
        labels.add(createLabel("Divers", "VARIOUS"));
        labels.add(createLabel("V�t�rinaire", "VETERINARY"));
        labels.add(createLabel("Visa", "VISA"));
        labels.add(createLabel("Eau", "WATER"));
        labels.add(createLabel("Retrait", "WITHDRAWAL"));

        labels.forEach(log::debug);
        log.debug("labels : " + labels.size());

        BankContainer.me().getLabels().addAll(labels);

        new LabelsSaver().run();
    }

    private OperationLabel createLabel(String name, String id) {
        OperationLabel label = new OperationLabel();
        label.setId(id);
        label.setName(name);
        return label;
    }
}
