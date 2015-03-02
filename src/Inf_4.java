import java.lang.annotation.ElementType;
import java.util.Properties;
import java.util.UUID;

import org.hibernate.search.Environment;
import org.hibernate.search.cfg.SearchMapping;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;


public class Inf_4 {

	public static void main(String[] args) {

		
		EmbeddedCacheManager manager = new DefaultCacheManager();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		Configuration c = cb
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
		
		manager.defineConfiguration("bird", c);
		
		Cache<UUID, Author> cache = manager.getCache("bird"); 

		System.out.println(cache.size());
		
		for(int i=1 ; i<500; i++){
			Author author = new Author(UUID.randomUUID());
			author.setFirstname("FirstName"+i);
			author.setLastname("LastName"+i);
			for(int ii=1; ii<50; ii++){
				Books book = new Books(UUID.randomUUID());
				book.setTitle("title" + ii);
				book.setTome("tome" + ii);
				author.setBook(book);
			}
			cache.put(author.getId(), author);
			System.out.println(i);
		}
		
		System.out.println(cache.size());
		/*Thread.sleep(2000);
		Set<Entry<UUID, Author>> set = cache.getAdvancedCache().entrySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			Entry entry = (Entry<UUID, Author>) it.next();
			System.out.println(entry.getKey() + " // " + entry.getValue().toString());
		}
		*/		
		
		
		cache.stop();
		manager.stop();
		
	}

}
