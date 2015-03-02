import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;



public class DateTimeSplitBridge implements FieldBridge {

	@Override
	public void set(String name, Object value, Document document, LuceneOptions luceneoptions) {

		Date datetime = (Date) value;
		int year = datetime.getYear();
		int month = datetime.getMonth();
		int day = datetime.getDay();
		
		// set year
		Field field = new Field(name+".year", String.valueOf(year),luceneoptions.getStore(),luceneoptions.getIndex(),luceneoptions.getTermVector());
		field.setBoost(luceneoptions.getBoost());
		document.add(field);
		System.out.println("field name : " + field.name());
		
		// set month
		field = new Field(name+".month", month < 10 ? "0" : "" + String.valueOf(month),luceneoptions.getStore(),luceneoptions.getIndex(),luceneoptions.getTermVector());
		field.setBoost(luceneoptions.getBoost());
		document.add(field);

		// set day
		field = new Field(name+".day", month < 10 ? "0" : "" + String.valueOf(day),luceneoptions.getStore(),luceneoptions.getIndex(),luceneoptions.getTermVector());
		field.setBoost(luceneoptions.getBoost());
		document.add(field);

	}

}
