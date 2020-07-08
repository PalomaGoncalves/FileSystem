package poof.textui.user;

import poof.textui.Shell;
import poof.textui.exception.AccessDeniedException;
import poof.textui.exception.EntryUnknownException;
import poof.textui.exception.IsNotDirectoryException;
import poof.textui.exception.UserExistsException;
import poof.textui.main.Message;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;


/**
 * Command for creating a user.
 * §2.3.1.
 */
public class CreateUser extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public CreateUser(Shell shell) {
    super(MenuEntry.CREATE_USER, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation, AccessDeniedException, UserExistsException {
	  
	  Form f = new Form(title());
	  InputString username = new InputString(f, Message.usernameRequest());
      InputString name = new InputString(f, Message.nameRequest());
      f.parse();
      if (!entity().getLoginUser().getUsername().equals("root"))
    	  throw new AccessDeniedException(entity().getLoginUser().getUsername());
      if (!entity().getLoginUser().getName().equals("Super User"))
    	  throw new AccessDeniedException(entity().getLoginUser().getUsername());
	  if(entity().getFileSystem().userExists(username.value()))
		  throw new UserExistsException(username.value());
	  
	  entity().getFileSystem().addUser(username.value(), name.value());
  }
}
