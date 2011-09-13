/**
 * 
 */
package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

/**
 * @author chamerling
 *
 */
@Entity
public class Subscription extends Model {

	@ManyToOne
	public Topic topic;

	public String uuid;
	
	public String subscriber;
}
