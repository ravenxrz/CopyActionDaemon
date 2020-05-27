package com.ravenxrz.copyactiondaemon.initiator;

import com.ravenxrz.copyactiondaemon.clipboard.ClipboardListenerThread;
import com.ravenxrz.copyactiondaemon.listener.GlobalMouseListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Raven
 * @version 1.0
 * @date 2020/5/6 12:59
 */
public class Initiator {
    public static void initialize(){
        initLog();
        initTextFormat();
        initMouseCopy();
        initSystemTray();
    }

    private static void initLog(){
        /* turn off the console output */
        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
    }

    private static void initMouseCopy(){
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        // Construct the example object.
        GlobalMouseListener example = new GlobalMouseListener();
        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(example);
    }

    private static void initSystemTray(){
        SystemTrayInitiator.initialize();
    }

    private static void initTextFormat(){
        new ClipboardListenerThread().start();
    }
}
