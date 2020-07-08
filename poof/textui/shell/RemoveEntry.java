package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.core.Entries;
import poof.textui.Shell;
import poof.textui.exception.AccessDeniedException;
import poof.textui.exception.EntryUnknownException;
import poof.textui.exception.IllegalRemovalException;
import poof.textui.shell.Message;
/**
 * Command for removing an entry of the current working directory.
 * §2.2.3.
 */
public class RemoveEntry extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public RemoveEntry(Shell shell) {
    super(MenuEntry.RM, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation, EntryUnknownException, AccessDeniedException, IllegalRemovalException {
	 
	  Form f = new Form(title());															 
    InputString entrieName = new InputString(f, Message.nameRequest());
    f.parse();
      
    if (!entity().exists(entrieName.value()))
      throw new EntryUnknownException(entrieName.value());
      
    if(!entity().verifyOwner() && entity().verifyPermission() && !entity().verifyPermission(entrieName.value()) && (!entity().getLoginUser().getUsername().equals("root")))
		  throw new AccessDeniedException(entity().getLoginUser().getUsername());
    if(!entity().verifyOwner() && !entity().verifyPermission() && entity().verifyPermission(entrieName.value()) && (!entity().getLoginUser().getUsername().equals("root")))
      throw new AccessDeniedException(entity().getLoginUser().getUsername());
    if(!entity().verifyOwner() && !entity().verifyPermission() && !entity().verifyPermission(entrieName.value()) && (!entity().getLoginUser().getUsername().equals("root")))
      throw new AccessDeniedException(entity().getLoginUser().getUsername());

    
    if(entrieName.value().equals(".") || entrieName.value().equals(".."))
      throw new IllegalRemovalException();
	  
	  entity().getLoginDir().remove(entrieName.value());
  }
}
