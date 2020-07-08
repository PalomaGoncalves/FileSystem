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
import poof.textui.exception.EntryExistsException;
import poof.textui.exception.EntryUnknownException;
import poof.textui.exception.IsNotFileException;
import poof.textui.shell.Message;
/**
 * Command for writing in a file of the current working directory.
 * §2.2.8.
 */
public class WriteFile extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public WriteFile(Shell shell) {
    super(MenuEntry.APPEND, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation, EntryUnknownException, IsNotFileException, AccessDeniedException{
	  
	  Form f = new Form(title());															 
    InputString fileName = new InputString(f, Message.fileRequest());
    InputString text = new InputString(f, Message.textRequest());
    f.parse();

    String fName = fileName.value();
    if (!entity().exists(fName))
  	  throw new EntryUnknownException(fName);
    if(!entity().verifyFile(fName))
    	throw new IsNotFileException(fName);
    if(!entity().verifyPermission() && !entity().verifyOwner() && (!entity().getLoginUser().getUsername().equals("root")))
      throw new AccessDeniedException(entity().getLoginUser().getUsername());
	  
	  entity().getLoginDir().getFile(fileName.value()).addChars(text.value()); 
  }
}

