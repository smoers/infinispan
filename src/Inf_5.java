import java.lang.annotation.ElementType;
import java.util.HashSet;
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


public class Inf_5 {


	public static void main(String[] args) {


		Set set = new HashSet();
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

		System.out.println(cache.size());
		
		for(int i=1 ; i<50; i++){
			Address address = new Address();
			address.setStreet("street"+i);
			address.setCity("city"+i);
			for(int ii=1; ii<50; ii++){
				Place place = new Place();
				place.setName("name"+ii);
				place.setAddress(address);
				set.add(place);
			}
			address.setPlaces(set);
			cache.put(UUID.randomUUID(), address);
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
