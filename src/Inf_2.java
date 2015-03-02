import java.io.IOException;

import org.infinispan.Cache;
import org.infinispan.manager.CacheManager;
import org.infinispan.manager.DefaultCacheManager;


public class Inf_2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		CacheManager manager = new DefaultCacheManager("all.xml");  
		Cache cache = manager.getCache("CacheStore"); 
		 
		cache.put("key", "value");
		 
		cache.stop();
		manager.stop();
		
	}

}
