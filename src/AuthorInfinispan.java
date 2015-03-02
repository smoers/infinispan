import java.io.Serializable;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.joda.time.DateTime;

@Entity
@Indexed
public class AuthorInfinispan implements IAuthor,Serializable {
	
	@Id
	private UUID id;
	@Field(store=Store.YES, index=Index.YES)
	private String lastname;
	@Field(store=Store.YES, index=Index.YES)
	private String firstname;
	@Field(store=Store.YES, index=Index.YES)
	private String authoralias;
	private IPhotoOrCover photo;
	@Field
	private String website;
	@Field
	private String biography;
	@Field
	private String comment;
	@Field(store=Store.YES, index=Index.YES)
    @FieldBridge(impl=JodaTimeSplitBridge.class)
	private DateTime borndate;
	@Field(store=Store.YES, index=Index.YES)
	@FieldBridge(impl=JodaTimeSplitBridge.class)
	private DateTime creationdate;

	@OneToMany(mappedBy="author")
	@IndexedEmbedded
	private Set<CycleInfinispan> inflistcycle;

	private Hashtable<UUID, ICycle> listcycle;	
	

	public AuthorInfinispan(){
		this.id = UUID.randomUUID();
		this.creationdate = new DateTime();
		this.listcycle = new Hashtable<UUID, ICycle>();
		this.inflistcycle = new HashSet<CycleInfinispan>();
	}
	
	@Override
	public UUID getID() {
		return this.id;
	}

	@Override
	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String getLastName() {
		return this.lastname;
	}

	@Override
	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public String getFirstName() {
		return this.firstname;
	}

	@Override
	public void setAuthorAlias(String authoralias) {
		this.authoralias = authoralias;
	}

	@Override
	public String getAuthorAlias() {
		return this.authoralias;
	}

	@Override
	public void setPhoto(IPhotoOrCover photo) {
		this.photo = photo;
	}

	@Override
	public IPhotoOrCover getPhoto() {
		return this.photo;
	}

	@Override
	public void setWebSite(String website) {
		this.website = website;
	}

	@Override
	public String getWebSite() {
		return this.website;
	}

	@Override
	public void setBiography(String biography) {
		this.biography = biography;
	}

	@Override
	public String getBiography() {
		return this.biography;
	}

	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String getComment() {
		return this.comment;
	}

	@Override
	public void setBornDate(DateTime borndate) {
		this.borndate = borndate;
	}

	@Override
	public DateTime getBornDate() {
		return this.borndate;
	}

	@Override
	public void setCreationDate(DateTime creationdate) {
		this.creationdate = creationdate;
	}

	@Override
	public DateTime getCreationDate() {
		return this.creationdate;
	}

	@Override
	public void setListCycle(Hashtable listcycle) {
		this.listcycle = listcycle; 
	}

	@Override
	public Hashtable<UUID, ICycle> getListCycle() {
		return this.listcycle;
	}
	
	public void refreshInfinispanSet(){
		inflistcycle.clear();
		for(Entry<UUID, ICycle> entry :  listcycle.entrySet()){
			inflistcycle.add((CycleInfinispan) entry.getValue());
		}
		
	}

}
