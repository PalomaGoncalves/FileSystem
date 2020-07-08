package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.textui.exception.AccessDeniedException;
import poof.textui.exception.EntryUnknownException;
import poof.textui.exception.IsNotDirectoryException;
import poof.textui.exception.UserUnknownException;
import poof.textui.user.Message;
import poof.textui.Shell;
/**
 * Command for changing the owner of an entry of the current working directory.
 * §2.2.11.
 */
public class ChangeOwner extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ChangeOwner(Shell shell) {
    super(MenuEntry.CHOWN, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation, EntryUnknownException, AccessDeniedException, UserUnknownException{
	  Form f = new Form(title());															 
    InputString name = new InputString(f, Message.nameRequest());
    InputString username = new InputString(f, Message.usernameRequest());
    f.parse();

    if(!entity().verifyOwner() && !entity().verifyPermission() && (!entity().getLoginUser().getUsername().equals("root")))
      throw new AccessDeniedException(entity().getLoginUser().getUsername());
    if (!entity().verifyDir(name.value()))
      throw new IsNotDirectoryException(name.value());
	  if(!entity().getLoginDir().existsEntrie(name.value())){
		  throw new EntryUnknownException(name.value());
	  }
	  entity().changeDir(name.value());
  }

}
