package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Marker extends Model {

	@Id
	public Long id;
	
	public String name;
	
	public String fileName = null;
	
	@Column(columnDefinition = "TEXT")
	public String pattern = null;
	
	@OneToOne
	@JsonIgnore
	public Structure structure = null;
	
	@ManyToOne
	@JsonIgnore
	public Session session = null;

	public static Finder<Long, Marker> find = new Finder<Long, Marker>(Marker.class);

}
