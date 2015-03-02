/*
 * Interface utilisee pour l'implementation d'un object Author
 */

import java.util.Hashtable;
import java.util.UUID;

import org.joda.time.DateTime;
 

public interface IAuthor {
	
	
	/*
	 * get ID
	 */
	public UUID getID();
	
	/*
	 * set Nom de l'auteur
	 * @param lastname type string
	 */
	public void setLastName(String lastname);
	
	/*
	 * get Nom de l'auteur
	 */
	public String getLastName();
	
	/*
	 * set Prenom de l'auteur
	 * @param firstname type string
	 */
	public void setFirstName(String firstname);
	
	/*
	 * get Prenom de l'auteur
	 */
	public String getFirstName();
	
	/*
	 * set l'alias de l'auteur
	 * @param authoralias type String
	 */
	public void setAuthorAlias(String authoralias);
	
	/*
	 * get l'alias de l'auteur
	 */
	public String getAuthorAlias();
	
	/*
	 * set la photo d'un auteur
	 * @param photo type IPhotoOrCover
	 */
	public void setPhoto(IPhotoOrCover photo);
	
	/*
	 * get la photo d'un auteur
	 */
	public IPhotoOrCover getPhoto();
	
	/*
	 * set Adresse WebSite
	 * @param website type String
	 */	
	public void setWebSite (String website);
	
	/*
	 * get Adresse WebSite
	 */
	public String getWebSite();
		
	/*
	 * set Biographie
	 * @param biography type String
	 */
	public void setBiography (String biography);
	
	/*
	 * get biographie
	 */
	public String getBiography();
	
	/*
	 * set commentaire
	 * @param comment type String
	 */
	public void setComment (String comment);
	
	/*
	 * get Commentaire
	 */
	public String getComment();
	
	/*
	 * Set date de naissance
	 * @param borndate type org.joda.time.DateTime
	 */
	public void setBornDate (DateTime borndate);
	
	/*
	 * get Date de naissance
	 */
	public DateTime getBornDate();
	
	/*
	 * set date de creation de l'objet
	 * @param creationdate type org.joda.time.DateTime
	 */
	public void setCreationDate (DateTime creationdate);
	
	/*
	 * get Date de creation de l'objet
	 */
	public DateTime getCreationDate();
	
	/*
	 * set la liste des cycles
	 * @param listcycle type Interface IListCycle
	 */
	public void setListCycle(Hashtable listcycle);
	
	/*
	 * get la liste des cycles
	 */
	public Hashtable getListCycle();
	

}
