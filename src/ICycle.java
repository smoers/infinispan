
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

import org.joda.time.DateTime;

public interface ICycle {
	
	/*
	 * Set ID
	 * @param id type object
	 */
	public void setID(UUID id);
	
	/*
	 * get ID
	 */
	public UUID getID();
	
	/*
	 * set le titre du cycle
	 * @param cycletitle type String
	 */
	public void setCycleTitle(String cycletitle);
	
	/*
	 * get le titre du cycle
	 */
	public String getCycleTitle();
	
	/*
	 * defini si c'est un cycle ou juste un livre
	 * @param cycle type boolean
	 */
	public void setCycle(boolean cycle);
	
	/*
	 * retourne un true si c'est un cycle
	 * retourne un false si c'est juste un livre
	 */
	public boolean getCycle();
	
	/*
	 * définir le nombre de tome dans la cycle
	 * @param nbrvolume type int
	 */
	public void setNbrVolume(int nbrvolume);
	
	/*
	 * retourne le nombre de tome dans le cycle
	 */
	public int getNbrVolume();
	
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
	 * set date de creation de l'objet
	 * @param creationdate type org.joda.time.DateTime
	 */
	public void setCreationDate (DateTime creationdate);
	
	/*
	 * get Date de creation de l'objet
	 */
	public DateTime getCreationDate();
	
	/*
	 * set une liste avec les livres du cycle
	 * @param listbook type interface org.bird.system.interfaces.IListBook
	 */
	public void setListBook(Hashtable listbook);
	
	/*
	 * retourne la liste des livres du cycle
	 * @return object type org.bird.system.interfaces.IListBook
	 */
	public Hashtable getListBook();
	
	

}
