package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputBoolean;
import pt.utl.ist.po.ui.InputInteger;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.textui.Shell;
import poof.core.ModifiedException;
import poof.core.NoFileException;

/**
 * Command for creating a new file system and logging the root user.
 */
public class New extends Command<Shell>  {
  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public New(Shell shell) {
    super(MenuEntry.NEW, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
	    Command<Shell> tmp;
	    try {
	      entity().testModified();
	    }
	    catch (ModifiedException e) {
	    	  Form f = new Form(title());
	    	  InputBoolean b = new InputBoolean(f, Message.saveBeforeExit());
	    	  f.parse();
	    	  if (b.value()) {
	    		  tmp = new Save(entity());;
	    		  tmp.execute();
	    	  }
	      
	    }
	    finally {
	      entity().New();
	      menu().entry(2).visible();
	      menu().entry(3).visible();
	      menu().entry(4).visible();
	      menu().entry(5).visible();
	    }
	  }
  }

