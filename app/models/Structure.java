package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Structure extends Model {

	@Id
	public Long id;
	
	public String name;

	@OneToOne
	@JsonIgnore
	public Session session = null;
	
	@Column(columnDefinition = "LONGTEXT")
	public String definition = null;
	
	public static Finder<Long, Structure> find = new Finder<Long, Structure>(Structure.class);

}
