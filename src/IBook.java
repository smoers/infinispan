

import java.util.Date;
import java.util.UUID;

import org.joda.time.DateTime;

public interface IBook {
	
	/*
	 * Set ID
	 * @param id type object
	 */
	public void setID(UUID id);
	
	/*
	 * get ID
	 */
	public UUID getID();
	
	public void setTitle(String title);
	
	public String getTitle();

	public void setStyle(String style);
	
	public String getStyle();
	
	public void setPresentation(String presentation);
	
	public String getPresentation();
	
	public void setCover(IPhotoOrCover cover);
	
	public IPhotoOrCover getCover();
	
	public void setEditor(String editor);
	
	public String getEditor();
	
	public void setCollection(String collection);
	
	public String getCollection();
	
	public void setISBN(String isbn);
	
	public String getISBN();
	
	/*
	 * set date de creation de l'objet
	 * @param creationdate type org.joda.time.DateTime
	 */
	public void setCreationDate (DateTime creationdate);
	
	/*
	 * get Date de creation de l'objet
	 */
	public DateTime getCreationDate();



	


}
