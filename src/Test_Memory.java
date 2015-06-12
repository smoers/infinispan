import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

import org.joda.time.DateTime;


public class Test_Memory {
	
	private static final int MegaBytes = 1048576;
	private static Instrumentation instrumentation;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MemoryMXBean memoryMXBean=ManagementFactory.getMemoryMXBean();
		MemoryUsage memNonHeap=memoryMXBean.getNonHeapMemoryUsage();
		MemoryUsage memHeap=memoryMXBean.getHeapMemoryUsage();
		
		System.out.println(memHeap.getUsed());

        long freeMemory = Runtime.getRuntime().freeMemory()/MegaBytes;
        long totalMemory = Runtime.getRuntime().totalMemory()/MegaBytes;
        long maxMemory = Runtime.getRuntime().maxMemory()/MegaBytes;

        System.out.println("freeMemory: " + freeMemory);
        System.out.println("Used Memory in JVM: " + (maxMemory - freeMemory));
        System.out.println("JVM totalMemory also equals to initial heap size of JVM : " + totalMemory);
        System.out.println("JVM maxMemory also equals to maximum heap size of JVM: " + maxMemory);

        Hashtable<UUID, IAuthor> cache = new Hashtable<UUID, IAuthor>();
        
        //System.out.println(MemoryUtil.deepSizeOf(cache));
        
		for(int author_i=0; author_i < 2000; author_i++){
			AuthorInfinispan author = new AuthorInfinispan();
			author.setLastName("lastname"+author_i);
			author.setFirstName("firstname"+author_i );
			author.setAuthorAlias("authoralias"+author_i);
			author.setWebSite("website"+author_i);
			author.setBornDate(new DateTime());
			for(int cycle_i=0; cycle_i < 20;cycle_i++){
				CycleInfinispan cycle = new CycleInfinispan();
				cycle.setCycleTitle("cycletitle"+author_i+"-"+cycle_i );
				cycle.setAuhtor(author);
				for(int book_i = 0; book_i < 30; book_i++){
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
			//System.out.println(author_i);
		}

        //System.out.println(MemoryUtil.deepSizeOf(cache));

        freeMemory = Runtime.getRuntime().freeMemory() / MegaBytes;
        totalMemory = Runtime.getRuntime().totalMemory() / MegaBytes;
        maxMemory = Runtime.getRuntime().maxMemory() / MegaBytes;

        System.out.println(cache.size());	
        System.out.println(memHeap.getUsed());
        System.out.println("Used Memory in JVM: " + (maxMemory - freeMemory));
        System.out.println("freeMemory in JVM: " + freeMemory);
        System.out.println("totalMemory in JVM shows current size of java heap : " + totalMemory);
        System.out.println("maxMemory in JVM: " + maxMemory);


	}

}
