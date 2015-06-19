import java.lang.annotation.ElementType;
import java.util.Hashtable;
import java.util.Properties;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.lucene.search.Query;
import org.hibernate.search.cfg.Environment;
import org.hibernate.search.cfg.SearchMapping;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.Index;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.ResultIterator;
import org.infinispan.query.SearchManager;
import org.infinispan.query.Search;

public class Inf_14 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EmbeddedCacheManager manager = new DefaultCacheManager();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		SearchMapping mapping = new SearchMapping();
	
		/*mapping.analyzerDef("fr", StandardTokenizerFactory.class)
			.filter(LowerCaseFilterFactory.class)
			.filter(FrenchStemFilterFactory.class);*/
		
		mapping.entity(AuthorInfinispan.class).indexed().providedId()
			.property("lastname", ElementType.METHOD).field()
			.property("firstname", ElementType.METHOD).field()
			.property("authoralias", ElementType.METHOD).field();

		
		mapping.entity(CycleInfinispan.class).indexed().providedId()
		.property("cycletitle", ElementType.METHOD).field();
		
		mapping.entity(BookInfinispan.class).indexed().providedId()
			.property("title", ElementType.METHOD).field()
			.property("style", ElementType.METHOD).field();
					
		Properties properties = new Properties();
		properties.put(Environment.MODEL_MAPPING, mapping);
		//properties.put("hibernate.search.default.directory_provide", "filesystem");
		//properties.put("hibernate.search.default.indexBase", "D:/infinispan/Indexes/");
		
		Configuration c = cb.indexing()
				.index(Index.LOCAL)
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
		
		manager.defineConfiguration("book012015", c);
		
		Cache<UUID, IAuthor> cache = manager.getCache("book012015"); 
		
		SearchManager sm = Search.getSearchManager(cache);
		
		System.out.println(cache.getCacheConfiguration().indexing().index().isEnabled());
		
		IAuthor authorch = cache.get(UUID.fromString("09586ce5-5763-471d-bbca-30b7cf78712e"));
		authorch.setFirstName("Serge");
		cache.put(authorch.getID(), authorch);
		/*sm.getMassIndexer().start();*/
		
		QueryBuilder qb = sm.buildQueryBuilderForClass(AuthorInfinispan.class).get();
		Query q = qb.keyword().wildcard().onField("firstname").matching("ser*").createQuery();
		CacheQuery cq = sm.getQuery(q, AuthorInfinispan.class);
		System.out.println(cq.getResultSize());
		
		
		ResultIterator it = cq.iterator();
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
