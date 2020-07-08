package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;
import pt.utl.ist.po.ui.Menu;
import poof.textui.Shell;
import poof.textui.exception.UserUnknownException;

/**
 * Represents the main menu of this apllication. This is the first menu
 * shown to the users.
 ***/
public class MainMenu extends Menu {
  /**
   * Constructor
   **/
  public MainMenu(Shell shell) {
    super(MenuEntry.TITLE,
	  new Command<?>[] {
	    new New(shell),
	      new Open(shell),
	      new Save(shell),
	      new Login(shell),
	      new ShowMenuShell(shell),
	      new ShowMenuUser(shell),
	      });
    if (!shell.existsFileSystem())
    	hideOptionsEmpty();
  }

  /**
   * Hide options when the application does not have a file system.
   **/
  public void hideOptionsEmpty() {
    entry(2).invisible();
    entry(3).invisible();
    entry(4).invisible();
    entry(5).invisible();
  }

  /**
   * Show hidden options when the application has a file system.
   **/
  public void showOptionsNonEmpty() {
    entry(2).visible();
    entry(3).visible();
    entry(4).visible();
    entry(5).visible();
  }
}


