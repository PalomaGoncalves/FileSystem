package poof.core;

public class SuperUser extends User{

	public SuperUser(){
		
		super("root","Super User");
	}

	public void changePermission(Entries entry){
		if(entry.getPermission() == true)
			entry.setPermission(false);
		else{
			entry.setPermission(true);
		}
	}

	public void changeOwner(Entries entry, User user){
		entry.setOwner(user);
	}
}

