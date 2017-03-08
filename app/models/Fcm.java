package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;

@Entity
public class Fcm extends Model {
	@Id
	public Long id;
	
	public String token;
	
	public Fcm() {
		
	}
	
	public Fcm(String token) {
		this.token = token;
	}
	
	public static Finder<Long, Fcm> find = new Finder<Long, Fcm>(Fcm.class);
}
