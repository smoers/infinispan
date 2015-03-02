import org.hibernate.search.bridge.StringBridge;
import org.joda.time.DateTime;


public class JodaDateTimeStringBridge implements StringBridge {

	@Override
	public String objectToString(Object object) {
		DateTime date = (DateTime) object;
		System.out.println(date.toString());
		return date.toString();
	}

}
