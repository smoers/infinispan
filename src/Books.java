

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
public class Books implements Serializable{
	
	@Field private String title;
	@Field private String tome;
	@Field private UUID id;

	public Books(UUID id) {
		super();
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the tome
	 */
	public String getTome() {
		return tome;
	}

	/**
	 * @param tome the tome to set
	 */
	public void setTome(String tome) {
		this.tome = tome;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Books [title=" + title + ", tome=" + tome + ", id=" + id + "]";
	}


}
