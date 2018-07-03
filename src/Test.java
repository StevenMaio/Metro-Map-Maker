
import javafx.application.Application;
import javafx.stage.Stage;
import mmm.gui.dialog.BorderedMessageDialogSingleton;
import mmm.gui.dialog.EnterTextDialogSingleton;
import mmm.gui.dialog.MetroLineSettingsDialogSingleton;
import mmm.gui.dialog.WelcomeDialogSingleton;

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
        WelcomeDialogSingleton welcomeDialog = WelcomeDialogSingleton.getSingleton();
        welcomeDialog.init(primaryStage);
        
        EnterTextDialogSingleton enterTextDialog = 
                EnterTextDialogSingleton.getSingleton();
        enterTextDialog.init(primaryStage);
        
//        welcomeDialog.show();

        MetroLineSettingsDialogSingleton metroLineSettings = 
                MetroLineSettingsDialogSingleton.getSingleton();
        metroLineSettings.init(primaryStage);
        
//        metroLineSettings.show();

        BorderedMessageDialogSingleton borderedMessage =
                BorderedMessageDialogSingleton.getSingleton();
        borderedMessage.init(primaryStage);
        
        borderedMessage.show();
    }
}
