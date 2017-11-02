
import javafx.application.Application;
import javafx.stage.Stage;
import mmm.gui.WelcomeDialogSingleton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author steve
 */
public class Test extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) {
        WelcomeDialogSingleton singleton = WelcomeDialogSingleton.getSingleton();
        
        singleton.init(primaryStage);
        singleton.show();
    }
}
