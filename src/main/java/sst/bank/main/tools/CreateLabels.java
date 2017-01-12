package sst.bank.main.tools;

import java.util.Set;
import java.util.TreeSet;

import sst.bank.activities.i.saving.LabelsSaver;
import sst.bank.model.OperationLabel;
import sst.bank.model.container.BankContainer;

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
	labels.add(createLabel("Vêtements", "CLOTHING"));
	labels.add(createLabel("Contrôle", "CONTROL"));
	labels.add(createLabel("Médecins", "DOCTORS"));
	labels.add(createLabel("Electricité", "ELECTRICITY"));
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
	labels.add(createLabel("Téléphone", "PHONE"));
	labels.add(createLabel("Restaurant", "RESTAURANT"));
	labels.add(createLabel("Sport", "SPORT"));
	labels.add(createLabel("Abonnement", "SUBSCRIPTION"));
	labels.add(createLabel("Pneus", "TIRES"));
	labels.add(createLabel("Divers", "VARIOUS"));
	labels.add(createLabel("Vétérinaire", "VETERINARY"));
	labels.add(createLabel("Visa", "VISA"));
	labels.add(createLabel("Eau", "WATER"));
	labels.add(createLabel("Retrait", "WITHDRAWAL"));

	labels.stream().forEach(
		l -> System.out.println(l));
	System.out.println("labels : " + labels.size());

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
