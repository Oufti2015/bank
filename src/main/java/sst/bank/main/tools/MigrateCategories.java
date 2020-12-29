package sst.bank.main.tools;

import lombok.extern.log4j.Log4j;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.j.saving.CategoriesSaver;
import sst.bank.model.Budget;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;
import sst.bank.model.container.ContainerInterface;

@Log4j
public class MigrateCategories {

    public static final String HOUSE = "HOUSE";
    public static final String BANK = "BANK";
    public static final String CAR = "CAR";
    public static final String DOG = "DOG";
    public static final String HEALTH = "HEALTH";
    public static final String LEISURE = "LEISURE";
    public static final String PRESENT = "PRESENT";
    public static final String SHOPPING = "SHOPPING";
    public static final String FOOD = "FOOD";
    public static final String CANNOT_FIND_CATGORY = "Cannot find catgory ";

    public static void main(String[] args) {
        new CategoriesLoader().run();

        new MigrateCategories().migrate();
    }

    private void migrate() {
        merge(BANK, "VISA");
        merge(BANK, "BANK_RETRAIT");
        merge(CAR, "CAR_FUEL");
        merge(CAR, "CAR_PARK");
        merge(DOG, "DOG_AGILITY");
        merge(DOG, "DOG_FOOD");
        merge(DOG, "DOG_VET");
        merge(HEALTH, "MUTUELLE");
        merge(HOUSE, "SODEXHO");
        merge(HOUSE, "ELECTRICITE");
        merge(HOUSE, "HOUSE_HOT");
        merge(HOUSE, "TELEPHONE");
        merge(HOUSE, "WATER");
        merge(LEISURE, "LEISURE_HOL");
        merge(LEISURE, "PAYPAL");
        merge(PRESENT, "ANNE");
        rename(SHOPPING, "FOOD");
        merge(FOOD, "HOUSE_FOOD");

        new CategoriesSaver().run();
    }

    private void rename(String newCat, String oldCat) {
        ContainerInterface bc = BankContainer.me();

        Category toBeRemoved = bc.category(oldCat);

        if (toBeRemoved == null) {
            log.error(CANNOT_FIND_CATGORY + oldCat);
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
                log.error(CANNOT_FIND_CATGORY + toBeKeptString);
            if (toBeRemoved == null)
                log.error(CANNOT_FIND_CATGORY + toBeRemovedString);
            return;
        }

        merge(toBeKept, toBeRemoved);
    }

    private void merge(Category toBeKept, Category toBeRemoved) {

        log.info("Merging " + toBeRemoved + " into " + toBeKept);
        merge(toBeKept.getBudget(), toBeRemoved.getBudget());

        BankContainer.me().getCategories().remove(toBeRemoved);
    }

    private void merge(Budget toBeKept, Budget toBeRemoved) {
        toBeKept.setAmount(toBeKept.getAmount().add(toBeRemoved.getAmount()));
    }
}
