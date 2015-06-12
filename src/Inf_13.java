import java.lang.annotation.ElementType;
import java.util.Properties;
import java.util.UUID;

import org.hibernate.search.Environment;
import org.hibernate.search.cfg.SearchMapping;
import org.hibernate.search.infinispan.impl.InfinispanDirectoryProvider;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.joda.time.DateTime;


public class Inf_13 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InfinispanDirectoryProvider directory = new InfinispanDirectoryProvider();
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

		for(int author_i=60; author_i < 621; author_i++){
			AuthorInfinispan author = new AuthorInfinispan();
			author.setLastName("lastname"+author_i);
			author.setFirstName("firstname"+author_i );
			author.setAuthorAlias("authoralias"+author_i);
			author.setWebSite("website"+author_i);
			author.setBornDate(new DateTime());
			for(int cycle_i=0; cycle_i < 2;cycle_i++){
				CycleInfinispan cycle = new CycleInfinispan();
				cycle.setCycleTitle("cycletitle"+author_i+"-"+cycle_i );
				cycle.setAuhtor(author);
				for(int book_i = 0; book_i < 3; book_i++){
					BookInfinispan book = new BookInfinispan();
					book.setTitle("Au delà des échos-"+author_i+"-"+cycle_i+"-"+book_i);
					book.setStyle("style"+author_i+"-"+cycle_i+"-"+book_i);
					book.setEditor("editor"+author_i+"-"+cycle_i+"-"+book_i);
					book.setCycle(cycle);
					cycle.getListBook().put(book.getID(), book);
				}
				cycle.refreshInfinispanSet();
				author.getListCycle().put(cycle.getID(), cycle);
			}
			author.refreshInfinispanSet();
			cache.put(author.getID(), author);
			System.out.println(author_i);
		}
		
		System.out.println(cache.size());
		cache.stop();
		manager.stop();		

	}

}
