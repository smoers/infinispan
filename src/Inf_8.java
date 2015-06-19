import java.lang.annotation.ElementType;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.lucene.search.Query;
import org.hibernate.search.cfg.Environment;
import org.hibernate.search.cfg.SearchMapping;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.Search;
import org.infinispan.query.SearchManager;


public class Inf_8 {

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
		
		SearchManager searchmanager = Search.getSearchManager(cache);
		
		QueryBuilder mythQB = searchmanager.getSearchFactory().buildQueryBuilder().forEntity(Address.class).get();
		//Query lucenequery = mythQB.keyword().wildcard().onField("street").matching("street*").createQuery();
		Query lucenequery = mythQB.keyword().wildcard().onField("street").andField("places.name").matching("name21").createQuery();
		
		CacheQuery cachequery = searchmanager.getQuery(lucenequery);
		
		List<Object> found = cachequery.list();
		
		System.out.println(found.size());
		
		/*Iterator it = found.iterator();
		while(it.hasNext()){
			Address tmp = (Address) it.next();
			System.out.println(tmp.toString());
		}*/

		cache.stop();
		manager.stop();
		
		
	}

}
