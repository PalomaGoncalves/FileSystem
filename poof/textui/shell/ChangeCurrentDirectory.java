package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputBoolean;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.core.Dir;
import poof.core.ModifiedException;
import poof.textui.Shell;
import poof.textui.exception.EntryUnknownException;
import poof.textui.exception.IsNotDirectoryException;
import poof.textui.shell.Message;
import poof.textui.main.Save;


/**
 * Command for changing current working directory.
 * §2.2.4.
 */
public class ChangeCurrentDirectory extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ChangeCurrentDirectory(Shell shell) {
    super(MenuEntry.CD, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation{
	  
	  Form f = new Form(title());															 
    InputString name = new InputString(f, Message.directoryRequest());
    f.parse();
    
	  entity().changeDir(name.value());
  }
}
