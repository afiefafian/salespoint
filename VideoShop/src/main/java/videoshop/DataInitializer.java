package videoshop;

import java.util.Arrays;

import org.salespointframework.core.inventory.Inventory;
import org.salespointframework.core.inventory.InventoryItem;
import org.salespointframework.core.money.Money;
import org.salespointframework.core.quantity.Units;
import org.salespointframework.core.useraccount.Role;
import org.salespointframework.core.useraccount.UserAccount;
import org.salespointframework.core.useraccount.UserAccountIdentifier;
import org.salespointframework.core.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import videoshop.model.BluRay;
import videoshop.model.Customer;
import videoshop.model.CustomerRepository;
import videoshop.model.Disc;
import videoshop.model.Dvd;
import videoshop.model.VideoCatalog;

// (｡◕‿◕｡)
// Zum testen bzw. während der Entwicklung sind schon vorhandene Dummy Daten sinnvoll, 
// diese werden hier erzeugt und hinzugefügt 

@Component
public class DataInitializer {


	@Autowired
	public DataInitializer(Inventory inventory, VideoCatalog videoCatalog, UserAccountManager userAccountManager, CustomerRepository customerRepository) {

		initializeUsers(userAccountManager,customerRepository);

		videoCatalog.add(new Dvd("Last Action Hero", "lac", new Money(9.99), "Äktschn/Comedy"));
		videoCatalog.add(new Dvd("Back to the Future", "bttf", new Money(9.99), "Sci-Fi"));
		videoCatalog.add(new Dvd("Fido", "fido", new Money(9.99), "Comedy/Drama/Horror"));
		videoCatalog.add(new Dvd("Super Fuzz", "sf", new Money(19.99), "Action/Sci-Fi/Comedy"));
		videoCatalog.add(new Dvd("Armour of God II: Operation Condor", "aog2oc", new Money(14.99), "Action/Adventure/Comedy"));
		videoCatalog.add(new Dvd("Persepolis", "pers", new Money(14.99), "Animation/Biography/Drama"));
		videoCatalog.add(new Dvd("Hot Shots! Part Deux", "hspd", Money.OVER9000, "Action/Comedy/War"));
		videoCatalog.add(new Dvd("Avatar: The Last Airbender", "tla", new Money(19.99), "Animation/Action/Adventure"));

		videoCatalog.add(new BluRay("Secretary", "secretary", new Money(6.99), "Political Drama"));
		videoCatalog.add(new BluRay("The Godfather", "tg", new Money(19.99), "Crime/Drama"));
		videoCatalog.add(new BluRay("No Retreat, No Surrender", "nrns", new Money(29.99), "Martial Arts"));
		videoCatalog.add(new BluRay("The Princess Bride", "tpb", new Money(39.99), "Adventure/Comedy/Family"));
		videoCatalog.add(new BluRay("Top Secret!", "ts", new Money(39.99), "Comedy"));
		videoCatalog.add(new BluRay("The Iron Giant", "tig", new Money(34.99), "Animation/Action/Adventure"));
		videoCatalog.add(new BluRay("Battle Royale", "br", new Money(19.99), "Action/Drama/Thriller"));
		videoCatalog.add(new BluRay("Oldboy", "old", new Money(24.99), "Action/Drama/Thriller"));
		videoCatalog.add(new BluRay("Bill & Ted's Excellent Adventure", "bt", new Money(29.99), "Adventure/Comedy/Family"));

		// (｡◕‿◕｡)
		// Über alle eben hinzugefügten Discs iterieren und jeweils ein InventoryItem mit der Quantity 10 setzen
		// Das heißt: Von jeder Disc sind 10 Stück im Inventar.
		
		for (Disc disc : videoCatalog.findAll()) {
			InventoryItem inventoryItem = new InventoryItem(disc, Units.TEN);
			inventory.add(inventoryItem);
		}
	}

	private void initializeUsers(UserAccountManager userAccountManager, CustomerRepository customerRepository) {
		
		// (｡◕‿◕｡)
		// UserAccounts bestehen aus einem Identifier und eine Password, diese werden auch für ein Login gebraucht
		// Zusätzlich kann ein UserAccount noch Rollen bekommen, diese können in den Controllern und im View dazu genutzt werden
		// um bestimmte Bereiche nicht zugänglich zu machen, das "ROLE_"-Prefix ist eine Konvention welche für Spring Security nötig ist.
		
		UserAccountIdentifier bossUI = new UserAccountIdentifier("boss");
		UserAccount bossAccount = userAccountManager.create(bossUI, "123", new Role("ROLE_BOSS"));
		userAccountManager.save(bossAccount);
		
		final Role customerRole = new Role("ROLE_CUSTOMER");
		
		UserAccount ua1 = userAccountManager.create(new UserAccountIdentifier("hans"), "123", customerRole);
		userAccountManager.save(ua1);
		UserAccount ua2 = userAccountManager.create(new UserAccountIdentifier("dextermorgan"), "123", customerRole);
		userAccountManager.save(ua2);
		UserAccount ua3 = userAccountManager.create(new UserAccountIdentifier("earlhickey"), "123", customerRole);
		userAccountManager.save(ua3);
		UserAccount ua4 = userAccountManager.create(new UserAccountIdentifier("mclovinfogell"), "123", customerRole);
		userAccountManager.save(ua4);
		
		Customer c1 = new Customer(ua1, "wurst");
		Customer c2 = new Customer(ua2, "Miami-Dade County");
		Customer c3 = new Customer(ua3, "Camden County - Motel");
		Customer c4 = new Customer(ua4 , "Los Angeles");
		
		// (｡◕‿◕｡)
		// Zu faul um save 4x am Stück aufzurufen :)
		customerRepository.save(Arrays.asList(c1,c2,c3,c4));
		

	}
}
