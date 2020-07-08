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
import poof.textui.exception.EntryUnknownException;
import poof.textui.exception.IsNotDirectoryException;
import poof.textui.shell.Message;
/**
 * Command for creating a file in the current working directory.
 * §2.2.5.
 */
public class CreateFile extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public CreateFile(Shell shell) {
    super(MenuEntry.TOUCH, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation, EntryExistsException, AccessDeniedException {
	  Form f = new Form(title());															 
      InputString fileName = new InputString(f, Message.fileRequest());
    f.parse();
    
    if (entity().exists(fileName.value()))
  	  throw new EntryExistsException(fileName.value());
	  if(!entity().verifyOwner() && !entity().verifyPermission() && (!entity().getLoginUser().getUsername().equals("root")))
      throw new AccessDeniedException(entity().getLoginUser().getUsername());
	  entity().getLoginDir().addFile(fileName.value(), entity().getLoginDir(), entity().getLoginUser(), false);
  }
}
