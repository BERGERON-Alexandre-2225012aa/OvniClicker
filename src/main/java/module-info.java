module abergeron.game.ovniclicker {
    requires javafx.controls;
    requires javafx.fxml;


    opens abergeron.game.ovniclicker to javafx.fxml;
    exports abergeron.game.ovniclicker;
}