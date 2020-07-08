package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputBoolean;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.core.ModifiedException;
import poof.core.NoFileException;
import poof.textui.Shell;

/**
 * Command for loading a file system and the last logged user stored in the given file.
 */
public class Open extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param Shell
   */
  public Open(Shell shell) {
    super(MenuEntry.OPEN, shell);
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
	    	 Form f = new Form(title());
	    	 InputString b = new InputString(f, Message.openFile());
	    	 f.parse();
	    	 try {
				entity().open(b.value());
				menu().entry(2).visible();
				menu().entry(3).visible();
				menu().entry(4).visible();
				menu().entry(5).visible();
			} catch (ClassNotFoundException e) {
				Display d = new Display();
				d.add("Ficheiro inexistente");
				d.display();
			} catch (IOException e) {
				Display d = new Display();
				d.add("IO Error");
				d.display();
			}
	    	
	    } 
	  }
}
  
