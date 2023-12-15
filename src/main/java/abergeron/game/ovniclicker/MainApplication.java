package abergeron.game.ovniclicker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author  BERGERON Alexandre
 *
 * @version 1.0.0
 */
public class MainApplication extends Application {
    /**
     * The start function of the game.
     * The scene set on the stage is loaded with FXML. The default scene is in the format 800x600 and can't be smaller.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-page-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setTitle("OvniClicker");
        stage.setScene(scene);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("images/ovni.png")));
        stage.show();
    }

    /**
     * The main function.
     * It launches the game set up in the start function.
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}