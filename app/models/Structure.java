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
	public Marker marker = null;
	
	public float colorR = 0;
	
	public float colorG = 0;
	
	public float colorB = 0;
	
	public double positionX = 0;
	
	public double positionY = 0;
	
	@Column(columnDefinition = "TEXT")
	public String definition = null;
	
	public static Finder<Long, Structure> find = new Finder<Long, Structure>(Structure.class);

}
