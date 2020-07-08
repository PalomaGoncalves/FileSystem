package poof.textui.user;

import poof.textui.Shell;
import poof.core.User;
import poof.core.FileSystem;

import java.io.IOException;

import java.util.Iterator;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.InvalidOperation;

import java.util.Iterator;


/**
 * Command for the showing existing users.
 * §2.3.2.
 */
public class ListUsers extends Command<Shell>  {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ListUsers(Shell shell) {
    super(MenuEntry.LIST_USERS, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {

	  Iterator i = entity().userList();
	  Display d = new Display(title());
	  while(i.hasNext()) {
		  User u = (User)i.next();
		  d.addNewLine(u.getUsername() + ":" + u.getName() + ":" + u.getMainDir().getAbsWay());
	  }
	  d.display();
  }
}
