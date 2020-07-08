package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InputBoolean;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.textui.exception.AccessDeniedException;
import poof.textui.exception.EntryUnknownException;
import poof.textui.shell.Message;
import poof.textui.Shell;

/**
 * Command for changing the permission of an entry of the current working directory.
 * §2.2.10.
 */
public class ChangePermission extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ChangePermission(Shell shell) {
    super(MenuEntry.CHMOD, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation, EntryUnknownException, AccessDeniedException {
	  boolean permission;
	  Form f = new Form(title());															 
    InputString name = new InputString(f, Message.nameRequest());
    InputString write = new InputString(f, Message.writeRequest());
    f.parse();

    if(!entity().getLoginDir().existsEntrie(name.value()))
		  throw new EntryUnknownException(name.value());
	  if(!entity().verifyOwner() && !entity().verifyPermission() && (!entity().getLoginUser().getUsername().equals("root")))
      throw new AccessDeniedException(entity().getLoginUser().getUsername());
  
    if(write.value().equals("S"))
    	permission = true;
    else
    	permission=false;
    	  
      
      entity().changePermission(name.value(), permission);
  }
}
