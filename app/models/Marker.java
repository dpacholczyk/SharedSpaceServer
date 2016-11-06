package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;

@Entity
public class Marker extends Model {

	@Id
	public Long id;
	
	public String name;
	
	public String fileName = null;
	
	public String pattern = null;

	public static Finder<Long, Marker> find = new Finder<Long, Marker>(Marker.class);

}
