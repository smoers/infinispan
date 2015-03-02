import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.NIOFSDirectory;


public class Inf_12 {

	public static void main(String[] args) throws CorruptIndexException, IOException {
		
		IndexReader indexreader = IndexReader.open(NIOFSDirectory.open(new File("D:/infinispan/Indexes/AuthorInfinispan")));
		List list = indexreader.document(0).getFields();
		Iterator it = list.iterator();
		while(it.hasNext()){
			Field field = (Field) it.next();
			System.out.println(field.name() );
		}
		
		indexreader.close(); 
		
		System.out.println(RandomGenerator.generateRandomWords(10));
		
	}

}
