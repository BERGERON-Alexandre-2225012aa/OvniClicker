package abergeron.game.ovniclicker;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is the controller of the game application.
 */
public class MainPageController implements Initializable {
    /**
     * The label that shows the score of the player on the top of the scene.
     */
    @FXML
    private Label scoreLabel;
    /**
     * The label that shows the value of Ovni power in the top left corner of the scene.
     */
    @FXML
    private Label ovniPowerLabel;
    /**
     * The label that shows the value of Automatic ovni power in the top right corner of the scene.
     */
    @FXML
    private Label automaticOvniPowerLabel;
    /**
     * The StackPane set in the center of the scene.
     */
    @FXML
    private StackPane centerStackPane;
    /**
     * The label that shows "CLICK" blinking onto the moon in the center of the scene.
     */
    @FXML
    private Label clickLabel;
    /**
     * The path shown around the moon which is the path of Ovnis.
     */
    @FXML
    private Path ovniPath;
    /**
     * The button shown in the bottom left corner of the scene. If the player has enough score, clicking on it will improve the Ovni power.
     */
    @FXML
    private Button ovniPowerBuyButton;
    /**
     * The button shown in the bottom right corner of the scene. If the player has enough score, clicking on it will improve the Automatic ovni power.
     */
    @FXML
    private Button automaticOvniPowerBuyButton;
    /**
     * The label that shows the cost of Ovni power in the bottom left corner of the scene.
     */
    @FXML
    private Label ovniPowerCostLabel;
    /**
     * The label that shows the cost of Automatic ovni power in the bottom right corner of the scene.
     */
    @FXML
    private Label automaticOvniPowerCostLabel;
    /**
     * The button used to load the last save. It appears on the Load-save Pop-up at the launching of the game or while opening it with the game menu while playing.
     */
    @FXML
    private Button yesLoadButton;
    /**
     * The button used to refuse the loading of the last save. It appears on the Load-save Pop-up at the launching of the game or while opening it with the game menu while playing.
     */
    @FXML
    private Button noLoadButton;
    /**
     * The HBox that contains the Load-save Pop-up. It appears at the launching of the game or while opening it with the game menu while playing.
     */
    @FXML
    private HBox loadLastSavePopUp;
    /**
     * The menu shown by clicking on the Menu button in the top left corner of the center part of the game while playing.
     */
    @FXML
    private HBox menu;
    /**
     * The button in the top left corner of the center part of the game that shows the menu when clicked.
     */
    @FXML
    private Button menuButton;
    /**
     * The button shown in the menu to open the Load-save Pop-up while playing.
     */
    @FXML
    private Button menuLoadSaveButton;
    /**
     * The button shown in the menu to open the New-save Pop-up while playing.
     */
    @FXML
    private Button menuNewSaveButton;
    /**
     * The button used to refuse the saving of a new progression. It appears on the New-save Pop-up while opening it with the game menu while playing.
     */
    @FXML
    private Button noSaveButton;
    /**
     * The button used to save a new progression. It appears on the New-save Pop-up while opening it with the game menu while playing.
     */
    @FXML
    private Button yesSaveButton;
    /**
     * The HBox that contains the New-save Pop-up. It appears while opening it with the game menu while playing.
     */
    @FXML
    private HBox newSavePopUp;
    /**
     * The Timeline of the Automatic ovni. It runs indefinitely every 5 seconds.
     */
    private Timeline automaticOvniTimer;
    /**
     * The ArrayList that contains every Timeline of each Ovni launched by the launchOvni() function. Each Timeline runs once for 5 seconds.
     */
    private ArrayList<Timeline> timers;
    /**
     * The value of the player's score.
     */
    private long score;
    /**
     * The value of the player's Ovni power.
     */
    private int ovniPower;
    /**
     * The value of the cost of the next improvement of Ovni power.
     */
    private long ovniPowerCost;
    /**
     * The value of the cost of the next improvement of Automatic ovni power.
     */
    private long automaticOvniPowerCost;
    /**
     * The value of the player's Automatic ovni power.
     */
    private int automaticOvniPower;

    /**
     * The function used to launch an Ovni by clicking on the moon. At the end of the ovni's path, the player has his score improved.
     */
    @FXML
    public void launchOvni() {
        ImageView ovniImageView = new ImageView(new Image(this.getClass().getResourceAsStream("images/ovni.png")));
        ovniImageView.setFitHeight(50);
        ovniImageView.setFitWidth(50);
        centerStackPane.getChildren().add(ovniImageView);

        PathTransition ovniTransition = new PathTransition();
        ovniTransition.setDuration(Duration.seconds(5));
        ovniTransition.setPath(ovniPath);
        ovniTransition.setNode(ovniImageView);
        ovniTransition.play();

        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            centerStackPane.getChildren().remove(ovniImageView);
            score += 10 * ovniPower;
            scoreLabel.setText(formatNumber(score));
        }));
        timers.add(timer);
        timer.setCycleCount(1);
        timer.play();
    }

    /**
     * The function used to launch the Automatic ovni at the loading of a save or by buying it for the first time. At each end of the Automatic ovni's path, the player has his score improved.
     */
    public void launchAutomaticOvni() {
        ImageView automaticOvniImageView = new ImageView(new Image(this.getClass().getResourceAsStream("images/automaticOvni.png")));
        automaticOvniImageView.setFitHeight(50);
        automaticOvniImageView.setFitWidth(50);
        centerStackPane.getChildren().add(automaticOvniImageView);
        PathTransition ovniTransition = new PathTransition();
        ovniTransition.setDuration(Duration.seconds(5));
        ovniTransition.setPath(ovniPath);
        ovniTransition.setNode(automaticOvniImageView);
        ovniTransition.setCycleCount(Animation.INDEFINITE);
        ovniTransition.play();
        automaticOvniTimer = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            score += 10 * ovniPower * Math.pow(10, automaticOvniPower);
            scoreLabel.setText(formatNumber(score));
        }));
        automaticOvniTimer.setCycleCount(Animation.INDEFINITE);
        automaticOvniTimer.play();
    }

    /**
     * The function executed by clicking on the button used to buy an improvement of the Ovni power.
     * It verifies if the player's score is upper or equal to the cost : if it's the case, it executes the code, if not, it does nothing.
     */
    @FXML
    public void onOvniPowerBuyButtonClicked() {
        if (score >= ovniPowerCost) {
            score -= ovniPowerCost;
            scoreLabel.setText(formatNumber(score));
            ovniPower += 1;
            ovniPowerLabel.setText(formatNumber(ovniPower));
            ovniPowerCost *= 2;
            ovniPowerCostLabel.setText(formatNumber(ovniPowerCost));
            ovniPowerCostLabel.setStyle("-fx-text-fill: red");
        }
    }

    /**
     * The function executed by clicking on the button used to buy an improvement of the Automatic ovni power.
     * It verifies if the player's score is upper or equal to the cost : if it's the case, it executes the code, if not, it does nothing.
     */
    @FXML
    public void onAutomaticOvniPowerBuyButtonClicked() {
        if (score >= automaticOvniPowerCost) {
            score -= automaticOvniPowerCost;
            scoreLabel.setText(formatNumber(score));
            automaticOvniPower += 1;
            automaticOvniPowerLabel.setText(formatNumber(automaticOvniPower));
            automaticOvniPowerCost *= 10;
            automaticOvniPowerCostLabel.setText(formatNumber(automaticOvniPowerCost));
            automaticOvniPowerCostLabel.setStyle("-fx-text-fill: red");
            if (automaticOvniPower == 1) {
                launchAutomaticOvni();
            }
        }
    }

    /**
     * The function used at the launch of the game application to initialize the animation of the "CLICK" text blinking in the center of the scene.
     */
    public void clickAnimation() {
        AtomicInteger timerInt = new AtomicInteger(0);
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timerInt.addAndGet(1);
            if (timerInt.get() % 2 == 0) {
                clickLabel.setStyle("-fx-text-fill: red");
            }
            else {
                clickLabel.setStyle("-fx-text-fill: white");
            }
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    /**
     * The function used at the launch of the game application : every 5 seconds, it looks if the score is upper or equal to the costs, if it's the case, their label style turns to green, if not, it stays red.
     */
    public void buyable() {
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (score >= ovniPowerCost) {
                ovniPowerCostLabel.setStyle("-fx-text-fill: green");
            }
            else {
                ovniPowerCostLabel.setStyle("-fx-text-fill: red");
            }
            if (score >= automaticOvniPowerCost) {
                automaticOvniPowerCostLabel.setStyle("-fx-text-fill: green");
            }
            else {
                automaticOvniPowerCostLabel.setStyle("-fx-text-fill: red");
            }
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    /**
     * The function used to format the display of every value shown on the scene.
     * @param number
     * @return
     */
    public String formatNumber(long number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(number);
        return formattedNumber;
    }

    /**
     * The function used to load a save.
     */
    public void loadSave() {
        try {
            URL saveUrl = getClass().getResource("save.txt");
            File save = new File(saveUrl.getPath());
            FileReader fr = new FileReader(save);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> saveContent = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                saveContent.add(line);
            }
            fr.close();
            score = Integer.valueOf(saveContent.get(0).split(":")[1]);
            scoreLabel.setText(formatNumber(score));
            ovniPower = Integer.valueOf(saveContent.get(1).split(":")[1]);
            ovniPowerLabel.setText(formatNumber(ovniPower));
            ovniPowerCost = Integer.valueOf(saveContent.get(2).split(":")[1]);
            ovniPowerCostLabel.setText(formatNumber(ovniPowerCost));
            automaticOvniPower = Integer.valueOf(saveContent.get(3).split(":")[1]);
            automaticOvniPowerLabel.setText(formatNumber(automaticOvniPower));
            automaticOvniPowerCost = Integer.valueOf(saveContent.get(4).split(":")[1]);
            automaticOvniPowerCostLabel.setText(formatNumber(automaticOvniPowerCost));
            if (automaticOvniPower > 0) {
                launchAutomaticOvni();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The function used to create a new save : it's executed by clicking on the "YES" New-save button.
     */
    @FXML
    public void newSave() {
        try {
            URL newSaveURL = getClass().getResource("save.txt");
            File newSave = new File(newSaveURL.getPath());
            FileWriter fr = new FileWriter(newSave);
            BufferedWriter bw = new BufferedWriter(fr);

            bw.write("score:"+score);
            bw.newLine();
            bw.write("ovniPower:"+ovniPower);
            bw.newLine();
            bw.write("ovniPowerCost:"+ovniPowerCost);
            bw.newLine();
            bw.write("automaticOvniPower:"+automaticOvniPower);
            bw.newLine();
            bw.write("automaticOvniPowerCost:"+automaticOvniPowerCost);
            bw.newLine();

            bw.close();

            newSavePopUp.setDisable(true);
            newSavePopUp.setVisible(false);
            menuButton.setDisable(false);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The function that contains all the instructions to execute at the launching of the app.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clickAnimation();
        buyable();
        timers = new ArrayList<>();
        ovniPower = 1;
        score = 0;
        automaticOvniPower = 0;
        ovniPowerCost = 1000;
        automaticOvniPowerCost = 5000;
    }

    /**
     * The function used to refuse the loading of a save : it's executed by clicking on the "NO" Load-save button.
     */
    @FXML
    public void noLoadButtonClicked() {
        loadLastSavePopUp.setDisable(true);
        loadLastSavePopUp.setVisible(false);
        menuButton.setDisable(false);
    }

    /**
     * The function used to load a save : it's executed by clicking on the "YES" Load-save button.
     */
    @FXML
    public void yesLoadButtonClicked() {
        loadLastSavePopUp.setDisable(true);
        loadLastSavePopUp.setVisible(false);
        centerStackPane.getChildren().remove(7, centerStackPane.getChildren().size());
        for (Timeline timer : timers) {
            timer.stop();
        }
        timers.clear();
        if (!(automaticOvniTimer == null)) {
            automaticOvniTimer.stop();
        }
        loadSave();
        menuButton.setDisable(false);
    }

    /**
     * The function used to close the menu while playing. It's executed by clicking on the red button in the top right corner of the menu.
     */
    @FXML
    public void closeMenu() {
        menu.setVisible(false);
        menu.setDisable(true);
        menuButton.setDisable(false);
    }

    /**
     * The function used to open the menu while playing. It's executed by clicking on the Menu button in the top left corner of the center of the scene.
     */
    @FXML
    public void openMenu() {
        menu.setDisable(false);
        menu.setVisible(true);
        menuButton.setDisable(true);
    }

    /**
     * The function used to display the Load-save Pop-up while playing. It's executed by clicking the adequate button in the menu.
     */
    @FXML
    public void menuLoadSaveButtonClicked() {
        closeMenu();
        menuButton.setDisable(true);
        loadLastSavePopUp.setDisable(false);
        loadLastSavePopUp.setVisible(true);
    }

    /**
     * The function used to display the New-save Pop-up while playing. It's executed by clicking the adequate button in the menu.
     */
    @FXML
    public void openNewSavePopUp() {
        newSavePopUp.setVisible(true);
        newSavePopUp.setDisable(false);
        menu.setDisable(true);
        menu.setVisible(false);
    }

    /**
     * The function used to refuse the creating of a new save : it's executed by clicking on the "NO" New-save button.
     */
    @FXML
    public void noSaveButtonClicked() {
        newSavePopUp.setVisible(false);
        newSavePopUp.setDisable(true);
        menuButton.setDisable(false);
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor enters it.
     */
    @FXML
    public void mouseEnterOvniPowerBuyButton() {
        ovniPowerBuyButton.setStyle("-fx-background-color: #CFCFCF ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor exits it.
     */
    @FXML
    public void mouseExitOvniPowerBuyButton() {
        ovniPowerBuyButton.setStyle("-fx-background-color: white ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor enters it.
     */
    @FXML
    public void mouseEnterAutomaticOvniPowerBuyButton() {
        automaticOvniPowerBuyButton.setStyle("-fx-background-color: #CFCFCF ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor exits it.
     */
    @FXML
    public void mouseExitAutomaticOvniPowerBuyButton() {
        automaticOvniPowerBuyButton.setStyle("-fx-background-color: white ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor enters it.
     */
    @FXML
    public void mouseEnterYesLoadButton() {
        yesLoadButton.setStyle("-fx-background-color: #CFCFCF ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor exits it.
     */
    @FXML
    public void mouseExitYesLoadButton() {
        yesLoadButton.setStyle("-fx-background-color: white ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor enters it.
     */
    @FXML
    public void mouseEnterNoLoadButton() {
        noLoadButton.setStyle("-fx-background-color: #CFCFCF ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor exits it.
     */
    @FXML
    public void mouseExitNoLoadButton() {
        noLoadButton.setStyle("-fx-background-color: white ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor enters it.
     */
    @FXML
    public void mouseEnterMenuButton() {
        menuButton.setStyle("-fx-background-color: #001FFF ; -fx-border-color: white");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor exits it.
     */
    @FXML
    public void mouseExitMenuButton() {
        menuButton.setStyle("-fx-background-color: #000c40 ; -fx-border-color: white");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor enters it.
     */
    @FXML
    public void mouseEnterMenuLoadSaveButton() {
        menuLoadSaveButton.setStyle("-fx-background-color: #CFCFCF ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor exits it.
     */
    @FXML
    public void mouseExitMenuLoadSaveButton() {
        menuLoadSaveButton.setStyle("-fx-background-color: white ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor enters it.
     */
    @FXML
    public void mouseEnterMenuNewSaveButton() {
        menuNewSaveButton.setStyle("-fx-background-color: #CFCFCF ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor exits it.
     */
    @FXML
    public void mouseExitMenuNewSaveButton() {
        menuNewSaveButton.setStyle("-fx-background-color: white ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor enters it.
     */
    @FXML
    public void mouseEnterYesSaveButton() {
        yesSaveButton.setStyle("-fx-background-color: #CFCFCF ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor exits it.
     */
    @FXML
    public void mouseExitYesSaveButton() {
        yesSaveButton.setStyle("-fx-background-color: white ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor enters it.
     */
    @FXML
    public void mouseEnterNoSaveButton() {
        noSaveButton.setStyle("-fx-background-color: #CFCFCF ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }

    /**
     * The function used to change the background color of the adequate button while the mouse cursor exits it.
     */
    @FXML
    public void mouseExitNoSaveButton() {
        noSaveButton.setStyle("-fx-background-color: white ; -fx-background-radius: 10 ; -fx-border-radius: 10");
    }
}