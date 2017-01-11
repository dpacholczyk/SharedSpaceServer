package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;

@Entity
public class User extends Model {
	
	@Id
	public Long id;
	
	public String name;
	
	public String deviceId;
	
	public static Finder<Long, User> find = new Finder<Long, User>(User.class);
	
	public static User findByDeviceId(String deviceId) {
		User user = User.find.where().eq("deviceId", deviceId).findUnique();
		
		return user;
	}
}
