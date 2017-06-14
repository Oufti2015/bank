package sst.bank.main.tools;

import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.j.saving.CategoriesSaver;
import sst.bank.model.Budget;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;
import sst.bank.model.container.ContainerInterface;

public class MigrateCategories {

    public static void main(String[] args) {
	new CategoriesLoader().run();

	new MigrateCategories().migrate();
    }

    private void migrate() {
	merge("BANK", "VISA");
	merge("BANK", "BANK_RETRAIT");
	merge("CAR", "CAR_FUEL");
	merge("CAR", "CAR_PARK");
	merge("DOG", "DOG_AGILITY");
	merge("DOG", "DOG_FOOD");
	merge("DOG", "DOG_VET");
	merge("HEALTH", "MUTUELLE");
	merge("HOUSE", "SODEXHO");
	merge("HOUSE", "ELECTRICITE");
	merge("HOUSE", "HOUSE_HOT");
	merge("HOUSE", "TELEPHONE");
	merge("HOUSE", "WATER");
	merge("LEISURE", "LEISURE_HOL");
	merge("LEISURE", "PAYPAL");
	merge("PRESENT", "ANNE");
	rename("SHOPPING", "FOOD");
	merge("FOOD", "HOUSE_FOOD");

	new CategoriesSaver().run();
    }

    private void rename(String newCat, String oldCat) {
	ContainerInterface bc = BankContainer.me();

	Category toBeRemoved = bc.category(oldCat);

	if (toBeRemoved == null) {
	    if (toBeRemoved == null)
		System.err.println("Cannot find catgory " + oldCat);
	    return;
	}

	rename(newCat, toBeRemoved);
    }

    private void rename(String newCat, Category toBeRemoved) {
	toBeRemoved.setName(newCat);
    }

    private void merge(String toBeKeptString, String toBeRemovedString) {
	ContainerInterface bc = BankContainer.me();

	Category toBeKept = bc.category(toBeKeptString);
	Category toBeRemoved = bc.category(toBeRemovedString);

	if (toBeKept == null || toBeRemoved == null) {
	    if (toBeKept == null)
		System.err.println("Cannot find catgory " + toBeKeptString);
	    if (toBeRemoved == null)
		System.err.println("Cannot find catgory " + toBeRemovedString);
	    return;
	}

	merge(toBeKept, toBeRemoved);
    }

    private void merge(Category toBeKept, Category toBeRemoved) {

	System.out.println("Merging " + toBeRemoved + " into " + toBeKept);
	merge(toBeKept.getBudget(), toBeRemoved.getBudget());

	BankContainer.me().getCategories().remove(toBeRemoved);
    }

    private void merge(Budget toBeKept, Budget toBeRemoved) {
	toBeKept.setAmount(toBeKept.getAmount().add(toBeRemoved.getAmount()));
    }
}
