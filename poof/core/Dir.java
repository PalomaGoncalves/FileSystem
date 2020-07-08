package poof.core;

import java.util.*;
import poof.textui.exception.EntryUnknownException;
import poof.textui.exception.IsNotDirectoryException;


public class Dir extends Entries{
	
	private Map<String,Entries> entriesMap = new TreeMap<String,Entries>();

	public Dir(String dirName, Dir parent, User owner, boolean permission){
		super.setParent(parent);
		super.setOwner(owner);
		super.setPermission(permission);
		setEntrieName(dirName);
		entriesMap.put(".", this);
		entriesMap.put("..", _parent);
	}

	public void addDir(String dirName, User owner, boolean permission){
		Entries newDir = new Dir(dirName,this,owner,permission);
		entriesMap.put(dirName, newDir);
	}
	public void addFile(String fileName, Dir parent, User owner, boolean permission){
		Entries newFile  = new File(fileName, parent, owner, permission);
		entriesMap.put(fileName, newFile);
	}

	public void addFile(String fileName, Dir parent, User owner, boolean permission, String content){
		Entries newFile  = new File(fileName, parent, owner, permission, content);
		entriesMap.put(fileName, newFile);
	}

	public void remove(String entrie){
		entriesMap.remove(entrie);
	}
	
	public Dir getDir(String dir) {
		Dir ret = (Dir)entriesMap.get(dir);
		if (ret instanceof Dir)
			return ret;
		return null;
	}

	public File getFile(String file) {
		File ret = (File)entriesMap.get(file);
		if (ret instanceof File)
			return ret;
		return null;
	}
	
	public Dir getDirOrCreate(String name, User su){
		if (!entriesMap.containsKey(name)) {
			this.addDir(name,su,false);
		}
		return (Dir)entriesMap.get(name);
	}

	public Dir searchDir(String[] path, User su){
		if (!entriesMap.containsKey(path[0])) {
			this.addDir(path[0],su,false);
		}
		Dir dir = (Dir)entriesMap.get(path[0]);
		
		if (path.length == 1)
			return dir;
		
		String[] newPath = Arrays.copyOfRange(path, 1, path.length);
		return dir.searchDir(newPath,su);
	}

	public Entries getContent(String entrie) {
		return entriesMap.get(entrie);
	}
	
	public Map<String,Entries> getEntries(){
		return entriesMap;
	}
	
	public int getLength() {
		return entriesMap.size() * 8;
	}
	public Entries changeDir(String newDir){
		return entriesMap.get(newDir);
	}
	
	public boolean existsEntrie(String name){
		return entriesMap.containsKey(name);
	}
	
	public Dir changeCurrentDir(String name) throws IsNotDirectoryException, EntryUnknownException {
		if(!existsEntrie(name))
			throw new EntryUnknownException(name);
		
		if (!verifyDir(name))
			throw new IsNotDirectoryException(name);
	 	return getDir(name);
	}
	
	public boolean verifyDir (String name){
		Entries e = this.getContent(name);
		return (e instanceof Dir);
	}


	@Override
	public boolean equals(Object o) {
		if (o instanceof Dir) {
			Dir d = (Dir)o;
			return this.getAbsWay().equals(d.getAbsWay());
		}
		return false;
	}
}

