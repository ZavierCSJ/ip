package teemo;

import javafx.application.Application;
import teemo.gui.Main;

/**
 * A launcher class to workaround classpath issues
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
