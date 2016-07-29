package sst.bank.activities.b.categorising.categories;

import java.util.HashMap;
import java.util.Map;

import sst.bank.model.container.BankContainer;
import sst.bank.model.container.BankContainer.CategoryName;

public class MapCounterpartyToCategory implements CategoryActivity {

    private static Map<String, CategoryName> mapping = new HashMap<String, CategoryName>();

    static {
	mapping.put("BE27 0000 0010 4373", CategoryName.ASSURANCES);
	mapping.put("BE76 3350 5545 9895", CategoryName.ELECTRICITE);
	mapping.put("LU09 0030 0603 0202 0000", CategoryName.TELEPHONE);
	mapping.put("BE43 0013 6602 2001", CategoryName.TELEPHONE);
	mapping.put("BE28 7775 9447 8320", CategoryName.AGILITY);
	mapping.put("BE83 7320 0184 9115", CategoryName.AGILITY);
	mapping.put("BE05 0003 2602 4575", CategoryName.AGILITY);
	mapping.put("BE54 9730 2899 9697", CategoryName.AGILITY);
	mapping.put("BE97 9796 2123 1249", CategoryName.AGILITY);
	mapping.put("BE63 0682 2795 7808", CategoryName.AGILITY);
	mapping.put("BE55 3600 1131 4044", CategoryName.AGILITY);
	mapping.put("BE15 0017 7247 4330", CategoryName.SODEXHO);
	mapping.put("LU68 0030 7742 4732 1000", CategoryName.SPORT);
	mapping.put("BE08 0969 6900 0113", CategoryName.WATER);
	mapping.put("BE11 3400 3075 0048", CategoryName.MUTUELLE);
	mapping.put("FRAIS MENSUELS D'UTILISATION", CategoryName.BANK);
	mapping.put("REDEVANCE MENSUELLE", CategoryName.BANK);
	mapping.put("BE13 2100 0004 7239", CategoryName.CAR);
	mapping.put("BE87 2100 0009 1594", CategoryName.CAR);
	mapping.put("BE36 7742 2921 4981", CategoryName.HOUSE);
	mapping.put("BE20 7320 1778 0656", CategoryName.DOG);
	mapping.put("BE16 0635 1214 4574", CategoryName.DOG);
	mapping.put("PAIEMENT A BANK CARD COMPANY", CategoryName.VISA);
	mapping.put("BE70 3401 3366 4725", CategoryName.DOG);
	mapping.put("BE36 0634 9991 3581", CategoryName.ANNE);
	mapping.put("BE86 2407 8045 6950", CategoryName.EPARGNE);
	mapping.put("BE59 7509 3291 3426", CategoryName.DOG);
	mapping.put("BE88 0003 2507 0541", CategoryName.DRESSING);
	mapping.put("BE11 2100 8848 3048", CategoryName.LEISURE);
	mapping.put("BE19 2100 6340 4912", CategoryName.DOG);
	mapping.put("BE65 7506 7296 0496", CategoryName.DOG);
    }

    @Override
    public void process() {
	BankContainer.me().operations().stream()
		.filter(o -> mapping.get(o.getCounterparty()) != null)
		.forEach(o -> o.setCategory(BankContainer.me().category(mapping.get(o.getCounterparty()))));
    }
}
