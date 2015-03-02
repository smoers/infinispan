import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.joda.time.DateTime;


@Entity
@Indexed
public class BookInfinispan implements IBook,Serializable {

	@Id
	private UUID id;
	@Field(store=Store.YES, index=Index.YES)
	private String title;
	@Field(store=Store.YES, index=Index.YES)
	private String style;
	@Field(store=Store.YES, index=Index.YES)
	private String presentation;
	private IPhotoOrCover cover;
	@Field(store=Store.YES, index=Index.YES)
	private String editor;
	@Field(store=Store.YES, index=Index.YES)
	private String collection;
	@Field(store=Store.YES, index=Index.YES)
	private String isbn;
	@Field(store=Store.YES, index=Index.YES)
	@FieldBridge(impl=JodaTimeSplitBridge.class)
	private DateTime creationdate;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE})
	@ContainedIn
	private ICycle cycle;
	
	public BookInfinispan(){
		this.id = UUID.randomUUID();
		this.creationdate = new DateTime();
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
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setStyle(String style) {
		this.style = style;
	}

	@Override
	public String getStyle() {
		return this.style;
	}

	@Override
	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	@Override
	public String getPresentation() {
		return this.presentation;
	}

	@Override
	public void setCover(IPhotoOrCover cover) {
		this.cover = cover;
	}

	@Override
	public IPhotoOrCover getCover() {
		return this.cover;
	}

	@Override
	public void setEditor(String editor) {
		this.editor = editor;
	}

	@Override
	public String getEditor() {
		return this.editor;
	}

	@Override
	public void setCollection(String collection) {
		this.collection = collection;
	}

	@Override
	public String getCollection() {
		return this.collection;
	}

	@Override
	public void setISBN(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public String getISBN() {
		return this.isbn;
	}

	@Override
	public void setCreationDate(DateTime creationdate) {
		this.creationdate = creationdate; 
	}

	@Override
	public DateTime getCreationDate() {
		return this.creationdate;
	}

	/**
	 * @param cycle the cycle to set
	 */
	public void setCycle(ICycle cycle) {
		this.cycle = cycle;
	}

}
