package com.marklalor.javasim;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marklalor.javasim.home.Home;
import com.marklalor.javasim.misc.TempFileShutdownHook;
import com.marklalor.javasim.misc.osx.OSXUtils;
import com.marklalor.javasim.preferences.ApplicationPreferences;

public class JavaSim
{
    private static Logger logger;
    private static Home home;
    
    public static void main(final String[] arguments)
    {
        initializeLogger();
        setNativeSystemLookAndFeel();
        
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                // Create the preferences object (it will let us detect the
                // OS cleanly to find the OS-dependent preferences file).
                ApplicationPreferences preferences = createApplicationPreferences(arguments);
                
                //This one seems to need to be registered earlier.
                if (preferences.isMacOSX())
                    OSXUtils.registerFullyQualifiedClassName("com.marklalor.javasim.misc.osx.OSXOpenFilesHandler");
                
                // Use OS X native menu bar (should pretty much always be yes, only disable to debug).
                if(preferences.getUseScreenMenuBar())
                    System.setProperty("apple.laf.useScreenMenuBar", "true");
                
                setTemporaryFolderDeletionThread(preferences);
                
                // Finally, start the home panel.
                home = new Home(preferences);
                home.getFrame().setSize(800, 500);
                home.getFrame().setVisible(true);
                
                //Might as well register these ones later because at least at this point in the thread we KNOW home has been initialized.
                if (preferences.isMacOSX())
                {
                    OSXUtils.registerFullyQualifiedClassName("com.marklalor.javasim.misc.osx.OSXPreferencesHandler");
                    OSXUtils.registerFullyQualifiedClassName("com.marklalor.javasim.misc.osx.OSXAppReOpenedHandler");
                    OSXUtils.registerFullyQualifiedClassName("com.marklalor.javasim.misc.osx.OSXQuitHandler");
                    //No need for this just yet... the default apple one is good for now.
                    //OSXUtils.registerfullyQualifiedClassName("com.marklalor.javasim.misc.osx.OSXAboutHandler");
                }
            }
        });
    }
    
    private static void initializeLogger()
    {
        JavaSim.logger = LoggerFactory.getLogger(JavaSim.class);
        PropertyConfigurator.configure(JavaSim.class.getResourceAsStream("log4j.properties"));
    }
    
    private static void setNativeSystemLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
            JavaSim.getLogger().info("Failed to set system look and feel.", e);
        }
    }
    
    private static File resolvePreferencesFile(ApplicationPreferences preferences)
    {
        if(preferences.isMacOSX())
            return new File(System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator + "JavaSim" + File.separator + "preferences.json");
        else if(preferences.isWindows())
            return new File(System.getenv("APPDATA") + File.separator + "JavaSim" + File.separator + "preferences.json");
        else if(preferences.isLinux())
            return new File(File.separator + "var" + File.separator + "lib" + File.separator + "javasim" + File.separator + "preferences.json");
        else
            return new File(System.getProperty("user.home") + File.separator + "JavaSim" + File.separator + "preferences.json");
    }
    
    private static ApplicationPreferences createApplicationPreferences(String[] arguments)
    {
        ApplicationPreferences preferences = new ApplicationPreferences();
        File preferencesFile = resolvePreferencesFile(preferences);
        JavaSim.getLogger().info("Resolved preferences file: {}", preferencesFile.getAbsolutePath());
        
        preferences.parseCommandLineArguments(arguments);
        preferences.parsePreferencesFile(preferencesFile);
        
        return preferences;
    }
    
    private static void setTemporaryFolderDeletionThread(ApplicationPreferences preferences)
    {
        // Set the shutdown hook to delete the temporary directory on program exit.
        Thread deleteTempFolderThread = new Thread(new TempFileShutdownHook(preferences.getTempDirectory()));
        deleteTempFolderThread.setName("TempFolderDelete");
        Runtime.getRuntime().addShutdownHook(deleteTempFolderThread);
        
        // Delete a temp folder that may have remained due to an error.
        try
        {
            FileUtils.deleteDirectory(preferences.getTempDirectory());
        }
        catch(IOException e)
        {
            JavaSim.getLogger().error("Could not delete temp folder {}", preferences.getTempDirectory(), e);
        }
        
        // Recreate it, of course!
        preferences.getTempDirectory().mkdirs();
    }
    
    public static String getVersion()
    {
        return JavaSim.class.getPackage().getSpecificationVersion();
    }
    
    public static Logger getLogger()
    {
        return logger;
    }
    
    public static Home getHome()
    {
        return home;
    }
}