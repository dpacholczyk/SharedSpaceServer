package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Session extends Model {

	@Id
	public Long id;
	
	public String name;
	
	@OneToMany
	@Column(unique = false)
	public List<SessionUser> users;
	
	@OneToMany
	public List<Structure> structures;

	public SessionUser getHost() {
		for(SessionUser user : users) {
			if(user.isHost) {
				return user;
			}
		}

		return null;
	}
	
	public static Finder<Long, Session> find = new Finder<Long, Session>(Session.class);
	
	public Session() {
		this.users = new ArrayList<SessionUser>();
	}
}
