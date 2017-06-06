package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by dpach on 01.06.2017.
 */
@Entity
public class SyncHistory extends Model {
    @Id
    public Long id;

    @ManyToOne
    public Structure structure;

    @ManyToOne
    public Session session;

    @ManyToOne
    public User sender;

    public String activityType;

    public boolean active;
}
