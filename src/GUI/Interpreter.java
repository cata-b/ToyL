package GUI;

import GUI.windows.MainWindow.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Interpreter extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(MainWindowController.getWindowResource());
        stage.setTitle("Interpreter");
        stage.setScene(new Scene(root, 500, 300));
        stage.show();
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
