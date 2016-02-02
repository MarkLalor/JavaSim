package com.marklalor.javasim.menu.menus;

import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.marklalor.javasim.menu.Menu;
import com.marklalor.javasim.menu.MenuHandler;
import com.marklalor.javasim.menu.MenuHeader;
import com.marklalor.javasim.menu.MenuItem;
import com.marklalor.javasim.simulation.Simulation;
import com.marklalor.javasim.simulation.frames.FrameHolder;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

public class SimulationMenu extends Menu
{
    private Simulation simulation;
    
	@MenuHeader(text="File", children={
	        "newSimulation",
	        "openSimulation",
	         MenuHeader.SEPERATOR,
	        "saveImage",
	        "saveImageAs",
	        "animateMenuItem",
	         MenuHeader.SEPERATOR,
	        "openHomeFolder",
	        "openContentFolder",
	         MenuHeader.SEPERATOR,
	        "closeSimulation",
	        "print"}
	) private JMenu file;
	
	@MenuHeader(text="Edit", children={
	        "copy"}
	) private JMenu edit;
	
	@MenuHeader(text="Animation", children={
            "play",
            "playUntilBreakpoint",
            "stop",
            MenuHeader.SEPERATOR,
            "nextFrame",
            MenuHeader.SEPERATOR,
            "decreaseSpeed",
            "increaseSpeed"}
    ) private JMenu animation;
	
	@MenuHeader(text="Simulation", children={
            "reset",
            "reloadSimulation",
            MenuHeader.SEPERATOR,
            "resizeMenuItem",
            "fullscreen"}
    ) private JMenu _simulation;
	
	@MenuHeader(text="Window", children={
	        "showConsole",
	        "minimize"}
	) private JMenu window;
    
    // FILE
	@MenuItem(text="New Simulation",        keyCode=KeyEvent.VK_N,      keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem newSimulation;
	@MenuItem(text="Open Simulation",       keyCode=KeyEvent.VK_O,      keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem openSimulation;
	@MenuItem(text="Save Image",            keyCode=KeyEvent.VK_S,      keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem saveImage;
	@MenuItem(text="Save Image As",         keyCode=KeyEvent.VK_S,      keyModifiers={MenuItem.COMMAND_OR_CONTROL, Event.SHIFT_MASK}) private JMenuItem saveImageAs;
	@MenuItem(text="Create Animated Gif",   keyCode=KeyEvent.VK_I,      keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem animateMenuItem;
	@MenuItem(text="Open Home Folder",      keyCode=KeyEvent.VK_D,      keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem openHomeFolder;
	@MenuItem(text="Open Content Folder",   keyCode=KeyEvent.VK_D,      keyModifiers={MenuItem.COMMAND_OR_CONTROL, Event.SHIFT_MASK}) private JMenuItem openContentFolder;
	@MenuItem(text="Close",                 keyCode=KeyEvent.VK_W,      keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem closeSimulation;
	@MenuItem(text="Print",                 keyCode=KeyEvent.VK_P,      keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem print;
	@MenuItem(text="Properties",            keyCode=KeyEvent.VK_W,      keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem openProperties;
	
	// EDIT
	@MenuItem(text="Copy",                  keyCode=KeyEvent.VK_C,      keyModifiers= MenuItem.COMMAND_OR_CONTROL)                    private JMenuItem copy;
	
	// ANIMATION
	@MenuItem(text="Play",                  keyCode=KeyEvent.VK_L,      keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem play;
	@MenuItem(text="Play Until Breakpoint", keyCode=KeyEvent.VK_L,      keyModifiers={MenuItem.COMMAND_OR_CONTROL, Event.SHIFT_MASK}) private JMenuItem playUntilBreakpoint;
	@MenuItem(text="Stop",                  keyCode=KeyEvent.VK_PERIOD, keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem stop;
	@MenuItem(text="Next Frame",            keyCode=KeyEvent.VK_SPACE)                                                                private JMenuItem nextFrame;
	@MenuItem(text="Decrease Speed",        keyCode=KeyEvent.VK_OPEN_BRACKET)                                                         private JMenuItem decreaseSpeed;
	@MenuItem(text="Increase Speed",        keyCode=KeyEvent.VK_CLOSE_BRACKET)                                                        private JMenuItem increaseSpeed;
	
	// SIMULATION
	@MenuItem(text="Reset",                 keyCode=KeyEvent.VK_R,      keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem reset;
    @MenuItem(text="Reload",                keyCode=KeyEvent.VK_F5)                                                                   private JMenuItem reloadSimulation;
    @MenuItem(text="Resize")                                                                                                          private JMenuItem resizeMenuItem;
    @MenuItem(text="Enter Full Screen",     keyCode=KeyEvent.VK_F,      keyModifiers={MenuItem.COMMAND_OR_CONTROL, Event.CTRL_MASK})  private JMenuItem fullscreen;
    
    // WINDOW
    @MenuItem(text="Show Console",          keyCode=KeyEvent.VK_J,      keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem showConsole;
    @MenuItem(text="Minimize",              keyCode=KeyEvent.VK_M,      keyModifiers={MenuItem.COMMAND_OR_CONTROL})                   private JMenuItem minimize;
	
	public SimulationMenu(Simulation simulation, FrameHolder frameHolder, MenuHandler menuHandler)
	{
        this.simulation = simulation;
	    createMenu(frameHolder, menuHandler);
	}

    public Simulation getSimulation()
    {
        return simulation;
    }

    @Override
    protected void finishInitializingMenuItems()
    {
        newSimulation.setText("New " + getSimulation().getInfo().getName());
//      //Fullscreen – Command + Shift + F (OS X) F11 (Windows)
//      if (getSimulation().getHome().getApplicationPreferences().isMacOSX())
//          fullscreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, (Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | InputEvent.CTRL_MASK)));
//      else
//          fullscreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
    }

    @Override
    protected void finishInitializingMenuHeaders()
    {
        // TODO Auto-generated method stub
    }
}
