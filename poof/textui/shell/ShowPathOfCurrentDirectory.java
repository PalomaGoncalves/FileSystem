package poof.textui.shell;
import poof.core.FileSystem;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;
/**
 * Command for showing the full path of the current working directory.
 * §2.2.7.
 */
public class ShowPathOfCurrentDirectory extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ShowPathOfCurrentDirectory(Shell shell) {
    super(MenuEntry.PWD, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
	  Display d = new Display();
	  d.add(entity().showActualPath());
	  d.display();
  }
}
