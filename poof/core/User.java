package poof.core;

import java.io.Serializable;

public class User implements Serializable, Comparable<User>{
	private String _username;
	private String _name;
	private Dir _mainDir;

	public User(String userName, String name){
		_username = userName;
		_name = name;
	}

	public String getUsername(){
		return _username;
	}

	public void setUsername(String newUsername){
		_username = newUsername;
	}
	
	public String getName(){
		return _name;
	}

	public void setName(String newName){
		_name = newName;
	}

	public Dir getMainDir(){
		return _mainDir;
	}

	public void setMainDir(Dir newMainDir){
		_mainDir = newMainDir;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {
			User u = (User)o;
			return u.getUsername().equals(this.getUsername());
		}
		return false;
	}

	public void changeOwner(Dir dir, User newOwner){
		if(dir.getPermission() == true)
			dir.setOwner(newOwner);
	}

	public int compareTo(User u) {
		return _username.toLowerCase().compareTo(u.getUsername().toLowerCase());
	}
}

