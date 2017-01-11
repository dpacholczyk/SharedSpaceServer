package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.avaje.ebean.Model;

@Entity
public class Session extends Model {

	@Id
	public Long id;
	
	@OneToMany
	public List<SessionUser> users;
	
	public static Finder<Long, Session> find = new Finder<Long, Session>(Session.class);
	
	public Session() {
		this.users = new ArrayList<SessionUser>();
	}
}
