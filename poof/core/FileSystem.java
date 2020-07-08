package poof.core;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Arrays;

public class FileSystem implements Serializable{

	private Dir _root;
	private boolean _modified;
	private String _filename;
	
	private User _loginUser;
	private Dir _loginDir;

	private Map<String,User> userMap = new TreeMap<String,User>();

	public FileSystem(){
		User su = new SuperUser();
		_root = new Dir("/",null,su,false);
		_root.setParent(_root);
		userMap.put(su.getUsername(),su);
		_root.addDir("home",su,false);
		_root.getDir("home").addDir("root",su,false);
		su.setMainDir(_root.getDir("home").getDir("root"));	
	}

	public User getLoginUser() {
		return _loginUser;
	}

	public void setLoginUser(User _loginUser) {
		this._loginUser = _loginUser;
	}

	public Dir getLoginDir() {
		return _loginDir;
	}

	public void setLoginDir(Dir _loginDir) {
		this._loginDir = _loginDir;
	}

	public void addUser(String username, String name){ 
		User newUser = new User(username, name);
		_root.getDir("home").addDir(username,newUser,false);
		newUser.setMainDir(_root.getDir("home").getDir(username));
		userMap.put(username, newUser);
		_modified = true;
	}

	public boolean userExists(String username) {
		return userMap.containsKey(username);
	}

	public User getUser(String username) {
		return userMap.get(username);
	}

	public boolean getModified() {
		return _modified;
	}

	public void setModified(boolean modified) {
		_modified = modified;
	}

	public String getFileName() {
		return _filename;
	}

	public void setFileName(String filename) {
		_filename = filename;
	}

	public void changePermission(String arg, Dir dir, User user, boolean permission) {
		Entries entrie = dir.getContent(arg);
		
		if((user.equals(entrie.getOwner()) ) || ((entrie.getPermission()) == true)){
			entrie.setPermission(permission);
		}
	}

	public Dir getDir(String path) {
		if ((path == "") || path == null)
			return _root;
		String[] array = path.split("\\/");
		array = Arrays.copyOfRange(array, 1, array.length);
		return _root.searchDir(array,userMap.get("root"));
	}
	
	public Dir getDir(String[] path) {
		return _root.searchDir(path,userMap.get("root"));
	}

	public Dir getUserDir(String username) {
		return _root.getDir("home").getDir(username);
	}

	public Map<String,User> getUserMap() {
		return userMap;
	}
}

