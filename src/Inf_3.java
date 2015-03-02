
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.Search;
import org.infinispan.query.SearchManager;



public class Inf_3 {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
		EmbeddedCacheManager manager = new DefaultCacheManager();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		Configuration c = cb
			.indexing().enabled(true).indexLocalOnly(true)
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
		
		SearchManager searchmanager = Search.getSearchManager(cache);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		//Query query = new QueryParser(Version.LUCENE_36,"firstname",analyzer).parse("firstname : 'FirstName10' OR lastname : 'LastName11'");
		Query query = new QueryParser(Version.LUCENE_36,"list.title",analyzer).parse("list.title : 'title18'");
		
		CacheQuery cachequery = searchmanager.getQuery(query);
		
		List<Object> found = cachequery.list();
		
		System.out.println(found.size());
		
		/*
		Iterator it = found.iterator();
		while(it.hasNext()){
			Author tmp = (Author) it.next();
			System.out.println(tmp.toString());
		}*/
		
	}

}
