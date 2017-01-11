package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Model;

@Entity
public class SessionUser extends Model {

	@Id
	public Long id;

	public User user;
	
	public boolean isHost = false;
	
	@ManyToOne(cascade = CascadeType.ALL)
	public Session session;
	
	public static Finder<Long, SessionUser> find = new Finder<Long, SessionUser>(SessionUser.class);
	
}
