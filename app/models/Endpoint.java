package models;

import java.util.List;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Endpoint extends Model {

	@Required
	public String url;
	
	@Required
	public String type;
	
	public static List<Endpoint> getFromType(String type) {
		return Endpoint.find("byType", type).fetch();
	}

}
