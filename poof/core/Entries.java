package poof.core;

import java.io.Serializable;



public abstract class Entries implements Serializable {

	private int _length;
	private String _name;
	private User _owner;
	private boolean _permission;
	protected Dir _parent;

	
	public abstract int getLength();
	
	public void setLength(int newLength){
		_length = newLength;
	}

	public User getOwner(){
		return _owner;
	}

	public void setOwner(User newOwner){
		_owner = newOwner;
	}

	public boolean getPermission(){
		return _permission;
	}

	public void setPermission(boolean newPermission){
		_permission = newPermission;
	}

	public String getEntrieName(){
		return _name;
	}

	public void setEntrieName(String newName){
		_name = newName;
	}
	
	public void setParent(Dir parent) {
		_parent = parent;
	}
	
	public Dir getParent() {
		return _parent;
	}

	public String getAbsWay(){
		if(getEntrieName().equals("/"))
			return getEntrieName();
		if (getParent().getEntrieName().equals("/"))
			return getParent().getEntrieName() + getEntrieName();
		return _parent.getAbsWay() + '/' + getEntrieName();
	}
}

