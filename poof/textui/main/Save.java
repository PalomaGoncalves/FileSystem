package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.core.NoFileException;
import poof.textui.Shell;

/**
 * Command for saving the relevant applicaion state.
 */
public class Save extends Command<Shell>  {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public Save(Shell shell) {
    super(MenuEntry.SAVE,shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {

	 
	  try {
		  entity().save();
	  } catch (NoFileException e) {
		  Form f = new Form(title());
		  InputString arg = new InputString(f, Message.newSaveAs());
		  f.parse();
		  try {
			  entity().saveAs(arg.value());
		  } catch (IOException io) {
			  Display d = new Display();
			  d.add("IO Error");
			  d.display();
		  }
	  } catch (IOException e) {
		  Display d = new Display();
		  d.add("IO Error");
		  d.display();		  
	  }
  }
}


