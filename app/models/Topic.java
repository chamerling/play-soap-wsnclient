package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Topic extends Model {

	public String name;

	public String prefix;

	public String uri;
}
