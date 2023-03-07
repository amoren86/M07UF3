package cat.institutmarianao.dao;

import java.sql.Timestamp;

public class User {
	private int id;
	private String username;
	private String name;
	private String email;
	private int rank;
	private Timestamp createdOn;
	private boolean active;

	public User(int id, String username, String name, String email, int rank, Timestamp createdOn, boolean active) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.email = email;
		this.rank = rank;
		this.createdOn = createdOn;
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public int getRank() {
		return rank;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public String toString() {
		return "Id: " + id + ", " + "Username: " + username + ", " + "Name: " + name + ", " + "Email: " + email;
	}

}
