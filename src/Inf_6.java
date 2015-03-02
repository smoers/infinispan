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


public class Inf_6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EmbeddedCacheManager manager = new DefaultCacheManager();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		SearchMapping mapping = new SearchMapping();
		
		mapping.entity(Address.class).indexed().providedId()
			.property("street", ElementType.METHOD)
			.property("city", ElementType.METHOD);

		mapping.entity(Place.class).indexed().providedId()
		.property("name", ElementType.METHOD);
		
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
		
		manager.defineConfiguration("place01", c);
		
		Cache<UUID, Address> cache = manager.getCache("place01"); 
		
		Set<Entry<UUID, Address>> set =  cache.getAdvancedCache().entrySet();
		Iterator<Entry<UUID, Address>> it = set.iterator();
		while(it.hasNext()){
			Entry entry = it.next();
			System.out.println(entry.getValue().toString());
		}
		
		cache.stop();
		manager.stop();		

	}

}
