import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.joda.time.DateTime;

@Entity
@Indexed
public class CycleInfinispan implements ICycle, Serializable {

	@Id
	private UUID id;
	@Field(store=Store.YES, index=Index.YES)
	private String cycletitle;
	@Field(store=Store.YES, index=Index.YES)
	private boolean cycle;
	@Field
	private int nbrvolume;
	@Field
	private String comment;
	@Field(store=Store.YES, index=Index.YES)
	@FieldBridge(impl=JodaTimeSplitBridge.class)
	private DateTime creationdate;
	@OneToMany(mappedBy="cycle")
	@IndexedEmbedded
	private Set<BookInfinispan> inflistbook;
	private Hashtable<UUID, IBook> listbook;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@ContainedIn
	private IAuthor author;
	
	
	public CycleInfinispan(){
		this.id = UUID.randomUUID();
		this.creationdate = new DateTime();	
		this.listbook = new Hashtable<UUID, IBook>();
		this.inflistbook = new HashSet<BookInfinispan>();
	}
	
	@Override
	public void setID(UUID id) {
		this.id = id;
	}

	@Override
	public UUID getID() {
		return this.id;
	}

	@Override
	public void setCycleTitle(String cycletitle) {
		this.cycletitle = cycletitle;
	}

	@Override
	public String getCycleTitle() {
		return this.cycletitle;
	}

	@Override
	public void setCycle(boolean cycle) {
		this.cycle = cycle;
	}

	@Override
	public boolean getCycle() {
		return this.cycle;
	}

	@Override
	public void setNbrVolume(int nbrvolume) {
		this.nbrvolume = nbrvolume;
	}

	@Override
	public int getNbrVolume() {
		return this.nbrvolume;
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
	public void setCreationDate(DateTime creationdate) {
		this.creationdate = creationdate;
	}

	@Override
	public DateTime getCreationDate() {
		return this.creationdate;
	}

	@Override
	public void setListBook(Hashtable listbook) {
		this.listbook = listbook;
	}

	@Override
	public Hashtable<UUID, IBook> getListBook() {
		return this.listbook;
	}

	/**
	 * @param auhtor the auhtor to set
	 */
	public void setAuhtor(IAuthor author) {
		this.author = author;
	}
	
	public void refreshInfinispanSet(){
		inflistbook.clear();
		for(Entry<UUID, IBook> entry :  listbook.entrySet()){
			inflistbook.add((BookInfinispan) entry.getValue());
		}
	}

}
