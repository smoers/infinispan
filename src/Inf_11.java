import java.lang.annotation.ElementType;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.lucene.search.Query;
import org.hibernate.search.Environment;
import org.hibernate.search.cfg.SearchMapping;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.ResultIterator;
import org.infinispan.query.Search;
import org.infinispan.query.SearchManager;


public class Inf_11 {

	public static void main(String[] args) {

		EmbeddedCacheManager manager = new DefaultCacheManager();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		SearchMapping mapping = new SearchMapping();
		
		mapping.entity(AuthorInfinispan.class).indexed().providedId()
			.property("lastname", ElementType.METHOD)
			.property("firstname", ElementType.METHOD)
			.property("authoralias", ElementType.METHOD);
		
		mapping.entity(CycleInfinispan.class).indexed().providedId()
		.property("cycletitle", ElementType.METHOD);
		
		mapping.entity(BookInfinispan.class).indexed().providedId()
			.property("title", ElementType.METHOD)
			.property("style", ElementType.METHOD)
			.property("editor", ElementType.METHOD);
			
		
		Properties properties = new Properties();
		properties.put(Environment.MODEL_MAPPING, mapping);
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
		
		SearchManager searchmanager = Search.getSearchManager(cache);
		
		searchmanager.getMassIndexer().start();
		
		
		QueryBuilder mythQB = searchmanager.getSearchFactory().buildQueryBuilder().forEntity(AuthorInfinispan.class).get();
		Query lucenequery = mythQB.keyword().wildcard().onField("lastname").matching("Moers").createQuery();
		//Query lucenequery = mythQB.keyword().wildcard().onField("firstname").andField("inflistcycle.inflistbook.title").matching("Au delà des échos-9-1-1").createQuery();
		//Query lucenequery = mythQB.keyword().wildcard().onField("comment").matching("*").createQuery();
		//Query lucenequery = mythQB.keyword().wildcard().onField("borndate.year").matching("2014").createQuery();
		//Query lucenequery = mythQB.phrase().onField("inflistcycle.inflistbook.title").sentence("Au delà des échos-400-1-1").createQuery();
		//Query lucenequery = mythQB.phrase().withSlop(100).onField("inflistcycle.inflistbook.title").sentence("Au delà des échos-40").createQuery();
		//Query lucenequery = mythQB.phrase().onField("borndate.year").sentence("2014").createQuery();
		/*Query lucenequery = mythQB.bool().should(mythQB.phrase().withSlop(100).onField("inflistcycle.inflistbook.title").sentence("Au delà des échos-40").createQuery())
				.should(mythQB.keyword().wildcard().onField("lastname").matching("Moers").createQuery())
				.should(mythQB.phrase().onField("borndate.year").sentence("2015").createQuery())
				.createQuery();*/
				
		CacheQuery cachequery = searchmanager.getQuery(lucenequery);
		
		//List<IAuthor> found = cachequery.list();
		
		System.out.println(cachequery.getResultSize());
		
		
		ResultIterator it = cachequery.iterator();
		while(it.hasNext()){
			
			IAuthor author = (IAuthor) it.next();
			System.out.println("Key : " + author.getID());
			System.out.println("Author : " + author.getFirstName() + " " + author.getLastName());
			System.out.println("Date de création : " + author.getCreationDate());
			System.out.println("Nbr de Cycle : " + author.getListCycle().size());
			System.out.println("Site Web : " + author.getWebSite());
			System.out.println("Born Date : " + author.getBornDate());
			
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

		it.close();
		cache.stop();
		manager.stop();

	

	}

}
