package poof.textui;

import poof.textui.main.*;
import poof.textui.exception.UserUnknownException;
import poof.core.Dir;
import poof.core.File;
import poof.core.FileSystem;
import poof.core.ModifiedException;
import poof.core.NoFileException;
import poof.core.User;
import poof.core.Entries;

import java.util.Iterator;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import poof.textui.exception.EntryUnknownException;
import poof.textui.exception.IsNotDirectoryException;


public class Shell{

	private FileSystem _fileSystem;
	private User _user;
	private Dir _dir;
	
	public boolean existsFileSystem() {
		return _fileSystem != null; 
	}

	public void New(){
		_fileSystem = new FileSystem();
		rootLogin();
	}
	
	public void rootLogin() {
		_user = _fileSystem.getUser("root");
		_dir = _user.getMainDir();
	}

	public FileSystem getFileSystem(){
		return _fileSystem;
	}

	
	public void testModified() throws ModifiedException{
		if (_fileSystem != null) {
			if (_fileSystem.getModified()) {
				throw new ModifiedException();
			}
		}
	}

	public void setFileSystem(FileSystem fileSystem){
		_fileSystem = fileSystem;
	}
	
	public void open(String file) throws IOException, ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream (new BufferedInputStream (new FileInputStream(file)));
		FileSystem fileSystem = (FileSystem) in.readObject();
		_user = (User) in.readObject();
		_dir = _user.getMainDir();
		in.close();
		_fileSystem = fileSystem;
		this.setLastLogin();
	}

	public void saveAs(String file) throws IOException{
		ObjectOutputStream out = new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream( file )));
		_fileSystem.setLoginUser(_user);
		_fileSystem.setLoginDir(_dir);
		out.writeObject(_fileSystem);
		out.writeObject(_user);
		out.close();
		_fileSystem.setFileName(file);
		_fileSystem.setModified(false);
	}
	
	public void save() throws NoFileException, IOException{
		if (_fileSystem.getFileName() == null)
			throw new NoFileException();
		else  {
			ObjectOutputStream out = new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (_fileSystem.getFileName())));
			_fileSystem.setLoginUser(_user);
			_fileSystem.setLoginDir(_dir);
			out.writeObject(_fileSystem);
			out.writeObject(_user);
			out.close();
			_fileSystem.setModified(false);
		}
	}

	public void setLastLogin() {
		_user = _fileSystem.getLoginUser();
		_dir = _user.getMainDir();
	}
	public void login(String username) throws UserUnknownException {
		if (!_fileSystem.userExists(username))
			throw new UserUnknownException(username);
		_user = _fileSystem.getUser(username);
		_dir = _user.getMainDir();
	}

	public void changePermission(String entrie, boolean permission) {
		_fileSystem.changePermission(entrie,_dir,_user,permission);
	}

	public Collection<Entries> listDir(){
		Collection<Entries> dirCollection = _dir.getEntries().values();
		return dirCollection;
	}

	public String showActualPath(){
		return _dir.getAbsWay();
	}

	public Iterator userList(){
		Collection userCollection = _fileSystem.getUserMap().values();
		return userCollection.iterator();
	}

	public void parse(String fileName) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
		
			 _fileSystem = new FileSystem();
			 String line;
			 while ((line = reader.readLine()) != null) {
				 parseLine(line);
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void parseLine(String line) {
		String[] args = line.split("\\|");
		switch (args[0]) {
		 
		case "USER":
			createUser(args[1], args[2]);
			break;
			 
		case "DIRECTORY":
			createDirectory(args[1], args[2], args[3]);
			break;
			 
		case "FILE":
			createFile(args[1], args[2], args[3], args[4]);
			break;
		}	 
	}

	public void createUser(String username, String name) {

		_fileSystem.addUser(username, name);
	}

	private void createEntry(String path, String username, String permission, boolean isDir, String content) {
		int last = path.lastIndexOf('/');
		String parentPath = null;
		if (last != 0)
			parentPath = path.substring(0, last);

		
		String entryName = path.substring(last + 1);
    
		User u = _fileSystem.getUser(username);
		Dir parent = _fileSystem.getDir(parentPath);

		if (isDir){
			parent.addDir(entryName, u, permission.equals("public"));
			}
		else{
			parent.addFile(entryName, parent,u , permission.equals("public"), content);
		}
	}

	public void createFile(String path, String username, String permission, String content) {	
		createEntry(path, username, permission, false, content);
	}
  
	public void createDirectory(String path, String username, String permission) {
		createEntry(path, username, permission, true, "");
    } 
	
	public boolean exists(String name){
		return _dir.existsEntrie(name);		
	}
	
	public boolean verifyOwner(){
		return (_dir.getOwner().getUsername().equals(_user.getUsername()));
	}
	
	public boolean verifyOwner(String name){
		return _dir.getContent(name).getOwner().equals(_user);
	}

	public boolean verifyPermission(){
		return (_dir.getPermission());
	}

	public boolean verifyPermission(String name) {
		return _dir.getContent(name).getPermission();
	}
	
	public boolean getModified() {
		return _fileSystem.getModified();
	}
	
	public boolean isLoggedDir(Dir dir) {
		return _dir.equals(dir);
	}
	
	public boolean isParentDir(Dir dir) {
		return _dir.getParent().equals(dir);
	}
	
	public Dir getLoginDir(){
		return _dir;
	}
	
	public User getLoginUser(){
		return _user;
	}
	
	public void changeDir(String name) throws IsNotDirectoryException, EntryUnknownException {
		_dir = _dir.changeCurrentDir(name);
	}
	
	public boolean verifyDir (String name){
		Dir d = _dir.getDir(name);
		return (d instanceof Dir);
	}
	
	public boolean verifyFile (String name){
		Entries e = _dir.getContent(name);
		return (e instanceof File);

	}
	
	public static void main(String[] args){
		String fileToImport = System.getProperty("import");
		Shell shell = new Shell();
		if (fileToImport != null){
			shell.parse(fileToImport);
			shell.rootLogin();
		}
		
		MainMenu menu = new MainMenu(shell);
		menu.open();
	}
}

