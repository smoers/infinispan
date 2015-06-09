import java.text.DateFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;


public class Inf_10 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EmbeddedCacheManager manager = new DefaultCacheManager();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		Properties properties = new Properties();
		properties.put("hibernate.search.default.directory_provide", "filesystem");
		properties.put("hibernate.search.default.indexBase", "D:/infinispan/Indexes");

		Configuration c = cb
			.indexing()
			.enable()
			.indexLocalOnly(true)
			.withProperties(properties)
			.persistence()
			.passivation(false)
			.addSingleFileStore()
			.preload(true)
			.shared(false)
			.fetchPersistentState(true)
			.ignoreModifications(false)
			.purgeOnStartup(false)
			.location("D:/infinispan")
			.build();
		
		manager.defineConfiguration("book01", c);
		
		Cache<UUID, IAuthor> cache = manager.getCache("book01");
		
		Set<Entry<UUID, IAuthor>> set = cache.getAdvancedCache().entrySet();
		
		Iterator<Entry<UUID, IAuthor>> it = set.iterator();
		while(it.hasNext()){
			
			Entry<UUID, IAuthor> entry = it.next();
			System.out.println("Key : " + entry.getKey());
			IAuthor author = entry.getValue();
			System.out.println("Author : " + author.getFirstName() + " " + author.getLastName());
			System.out.println("Date de création : " + author.getCreationDate());
			System.out.println("Nbr de Cycle : " + author.getListCycle().size());
			
			Hashtable<UUID, ICycle> listcycle = author.getListCycle();
			for(Entry<UUID, ICycle> entrycycle : listcycle.entrySet()){
				ICycle cycle = entrycycle.getValue();
				System.out.println("--Cycle : " + cycle.getCycleTitle());
				
				Hashtable<UUID, IBook> listbook = cycle.getListBook();
				for(Entry<UUID, IBook> entrybook : listbook.entrySet()){
					IBook book = entrybook.getValue();
					System.out.println("----Book : " + book.getTitle());
				}
				
			}
			
			
			System.out.println("-------------------------------------------------------------------");
			
		}
		
		IAuthor author = cache.get(UUID.fromString("d33d3ee6-c83a-4ba1-a075-a507a2d690be"));
		System.out.println(author.getLastName());
		/*author.setLastName("Moers");
		cache.put(author.getID(), author);
		IAuthor author1 = cache.get(UUID.fromString("d33d3ee6-c83a-4ba1-a075-a507a2d690be"));
		System.out.println(author1.getLastName());*/
		
		cache.stop();
		manager.stop();
	}

}
