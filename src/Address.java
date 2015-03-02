import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Indexed
public class Address implements Serializable {

	@Id
	@GeneratedValue
	@DocumentId
	private Long Id;
	
	@Field
	private String street;
	
	@Field
	private String city;	
	
	@OneToMany(mappedBy="address")
	@IndexedEmbedded
	private Set<Place> places;

	public Address(){}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return Id;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the places
	 */
	public Set<Place> getPlaces() {
		return places;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		Id = id;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param places the places to set
	 */
	public void setPlaces(Set<Place> places) {
		this.places = places;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Address [Id=" + Id + ", street=" + street + ", city=" + city
				+ ", places=" + places.toString() + "]";
	}
	
	
}
