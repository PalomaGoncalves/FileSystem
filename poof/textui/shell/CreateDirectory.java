package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.textui.Shell;
import poof.textui.exception.AccessDeniedException;
import poof.textui.exception.EntryExistsException;
import poof.textui.shell.Message;
/**
 * Command for creating a directory in the current working directory.
 * §2.2.6.
 */
public class CreateDirectory extends Command<Shell> {
  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public CreateDirectory(Shell shell) {
    super(MenuEntry.MKDIR, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation, EntryExistsException, AccessDeniedException{
	  
	  Form f = new Form(title());															 
    InputString dirName = new InputString(f, Message.directoryRequest());
    f.parse();
      
    if (entity().exists(dirName.value()))
      throw new EntryExistsException(dirName.value());
    if(!entity().verifyOwner() && !entity().verifyPermission() && (!entity().getLoginUser().getUsername().equals("root")))
      throw new AccessDeniedException(entity().getLoginUser().getUsername());
	  
	  entity().getLoginDir().addDir(dirName.value(), entity().getLoginUser(), false);
  }
}

