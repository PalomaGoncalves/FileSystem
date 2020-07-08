package poof.core;


public class File extends Entries{
	private String _content = "";

	public File(String fileName, Dir parent, User owner, boolean permission, String content){
		
		super.setParent(parent);
		super.setOwner(owner);
		super.setPermission(permission);
		setEntrieName(fileName);
		setContent(content);
		
	}
	
	public File (String fileName, Dir parent, User owner, boolean permission) {
		super.setParent(parent);
		super.setOwner(owner);
		super.setPermission(permission);
		setEntrieName(fileName);
	}

	public void addChars(String string){
		_content += string  + '\n';
	}

	public String getContent(){
		return _content;
	}

	public void setContent(String newString){
		_content=newString + '\n';
	}
	
	public int getLength(){
		return _content.length();
	}
}

