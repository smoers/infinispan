import java.lang.annotation.ElementType;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.hibernate.search.Environment;
import org.hibernate.search.cfg.SearchMapping;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;


public class Inf_1 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		EmbeddedCacheManager manager = new DefaultCacheManager();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		SearchMapping mapping = new SearchMapping();
		
		mapping.entity(Author.class).indexed().providedId()
			.property("firstname", ElementType.METHOD)
			.property("lastname", ElementType.METHOD);
		
		Properties properties = new Properties();
		properties.put(Environment.MODEL_MAPPING, mapping);
		
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
		
		manager.defineConfiguration("bird02", c);
		
		Cache<UUID, Author> cache = manager.getCache("bird02"); 

		System.out.println(cache.size());
		
		for(int i=1 ; i<50; i++){
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
