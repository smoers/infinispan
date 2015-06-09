import java.lang.annotation.ElementType;
import java.util.Properties;
import java.util.UUID;




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
import org.infinispan.query.SearchManager;
import org.infinispan.query.Search;



public class Inf_14 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
			.property("style", ElementType.METHOD);
					
		Properties properties = new Properties();
		properties.put(Environment.MODEL_MAPPING, mapping);
		//properties.put("hibernate.search.default.directory_provide", "filesystem");
		//properties.put("hibernate.search.default.indexBase", "D:/infinispan/Indexes/");
		
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
		
		manager.defineConfiguration("book012015", c);
		
		Cache<UUID, IAuthor> cache = manager.getCache("book012015"); 
		
		SearchManager sm = Search.getSearchManager(cache);
		
		QueryBuilder qb = sm.buildQueryBuilderForClass(AuthorInfinispan.class).get();
		Query q = qb.keyword().wildcard().onField("lastname").matching("lastname400").createQuery();
		CacheQuery cq = sm.getQuery(q, AuthorInfinispan.class);
		System.out.println(cq.getResultSize());
		

	}

}
