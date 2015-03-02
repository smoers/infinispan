

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Indexed
public class Author implements Serializable {
	
	@Field	private UUID id;
	@Field private String lastname;
	@Field private String firstname;
	@OneToMany(mappedBy="author")
	@IndexedEmbedded
	private HashMap<UUID, Books> list;

	public Author(){}
	
	public Author(UUID id) {
		this.id = id;
		list = new HashMap<UUID,Books>();
	}
	
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @return the list
	 */
	public HashMap<UUID, Books> getList() {
		return list;
	}
	
	/**
	 * @param list the list to set
	 */
	public void setList(HashMap<UUID, Books> list) {
		this.list = list;
	}
	
	public void setBook(Books book){
		list.put(book.getId(), book);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Author [id=" + id + ", lastname=" + lastname + ", firstname="
				+ firstname + ", Books List = " + list.toString() + "]";
	}
}
