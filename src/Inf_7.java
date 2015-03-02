import java.lang.annotation.ElementType;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.hibernate.search.Environment;
import org.hibernate.search.cfg.SearchMapping;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.Search;
import org.infinispan.query.SearchManager;


public class Inf_7 {

	public static void main(String[] args) throws ParseException {
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
		
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		//Query query = new QueryParser(Version.LUCENE_36,"firstname",analyzer).parse("firstname : 'FirstName10' OR lastname : 'LastName11'");
		Query query = new QueryParser(Version.LUCENE_36,"city",analyzer).parse("city : 'city1'*");
		
		CacheQuery cachequery = searchmanager.getQuery(query);
		
		List<Object> found = cachequery.list();
		
		System.out.println(found.size());

		
	}

}
