package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SessionUser extends Model {

	@Id
	public Long id;

	@OneToOne
	@Column(unique = false)
	public User user;
	
	public boolean isHost = false;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	public Session session;
	
	public static Finder<Long, SessionUser> find = new Finder<Long, SessionUser>(SessionUser.class);
	
}
