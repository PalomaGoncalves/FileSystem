package poof.textui.shell;

import java.util.Collection;
import java.util.Iterator;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import poof.core.Dir;
import poof.core.Entries;
import poof.textui.Shell;

/**
 * Command for showing the content of working directory.
 * §2.2.1.
 */
public class ListCurrentDir extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param Shell
   */
  public ListCurrentDir(Shell shell) {
    super(MenuEntry.LS, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() {
	  

	  Collection<Entries> i = entity().listDir();
	  Display d = new Display(title());
	  for(Entries e : i){
			String p;
			String dirName;
			if (e instanceof Dir) {
				Dir dir = (Dir)e;

				if (entity().isLoggedDir(dir))
					dirName = ".";
				else if (entity().isParentDir(dir))
					dirName = "..";
				else
					dirName = dir.getEntrieName();
			}
			else
				dirName = e.getEntrieName();
			if (e.getPermission())
				p = "w";
			else
				p = "-";
			if (e instanceof Dir)
				d.addNewLine( "d " + p + " " + e.getOwner().getUsername() + " " + e.getLength() + " " + dirName);
			else 
				d.addNewLine( "- " + p + " " + e.getOwner().getUsername() + " " + e.getLength() + " " + dirName);
		
		}
	  d.display();
  }
}
