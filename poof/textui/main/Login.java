package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;
import poof.textui.exception.UserUnknownException;
/**
 * Command for the login option.
 * §2.1.2.
 */
public class Login extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public Login(Shell shell) {
    super(MenuEntry.LOGIN, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    try {
      Form f = new Form(title());															 
      InputString username = new InputString(f, Message.usernameRequest());					
      f.parse();											
      entity().login(username.value());						
    }
    catch (UserUnknownException e) {
      throw new InvalidOperation(e.getMessage());
    }
  }
}

