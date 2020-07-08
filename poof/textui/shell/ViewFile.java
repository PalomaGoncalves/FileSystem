package poof.textui.shell;
import poof.core.FileSystem;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.textui.Shell;
import poof.textui.exception.AccessDeniedException;
import poof.textui.exception.EntryUnknownException;
import poof.textui.exception.IsNotFileException;
import poof.textui.shell.Message;
/**
 * Command for viewing the content of a file of the current working directory.
 * §2.2.9.
 */
public class ViewFile extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ViewFile(Shell shell) {
    super(MenuEntry.CAT, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation, EntryUnknownException, IsNotFileException {
	  
	  Form f = new Form(title());															 
      InputString fileName = new InputString(f, Message.fileRequest());
      f.parse();
      
      Display d = new Display();
      
      if (!entity().exists(fileName.value()))
    	  throw new EntryUnknownException(fileName.value());
      
      if(!entity().verifyFile(fileName.value()))
    	  throw new IsNotFileException(fileName.value());

	  d.add(entity().getLoginDir().getFile(fileName.value()).getContent());
	  d.display();
    
  }
}
