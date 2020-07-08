package poof.textui.shell;

import java.io.IOException;
import java.util.Collection;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.core.Dir;
import poof.core.Entries;
import poof.textui.exception.EntryUnknownException;
import poof.textui.shell.Message;
import poof.textui.Shell;
/**
 * Command for showing an entry of the current working directory.
 * §2.2.2.
 */
public class ListEntry extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ListEntry(Shell shell) {
    super(MenuEntry.LS_ENTRY, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation, EntryUnknownException {
	  
	  Form f = new Form(title());															 
      InputString name = new InputString(f, Message.nameRequest());
      f.parse();
      
	  if(!entity().getLoginDir().existsEntrie(name.value())){
		  throw new EntryUnknownException(name.value());
	  }

	  Display d = new Display(title());
	  entity().getLoginDir().getContent(name.value());
	  String p;
	  String entryName;
	  String type;
	  Entries e = entity().getLoginDir().getContent(name.value());
	  if (e instanceof Dir) {
	  	type = "d";
	  	Dir dir = (Dir)e;
	  	if (entity().isLoggedDir(dir))
	  		entryName = ".";
	  	else if (entity().isParentDir(dir))
	  		entryName = ".";
	  	else
	  		entryName = dir.getEntrieName();
	  }
	  else {
	  	type = "-";
	  	entryName = e.getEntrieName();
	  }
	  if (e.getPermission())
		  p = "w";
	  else
		  p = "-";


	  if (entity().getLoginDir().getContent(name.value()) instanceof Dir)
		 d.addNewLine( type + " " + p + " " + e.getOwner().getUsername() + " " + e.getLength() + " " + entryName);
	  else 
		  d.addNewLine( "- " + p + " " + e.getOwner().getUsername() + " " + e.getLength() + " " + entryName);
		
  		d.display();
  }
}
