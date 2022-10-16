import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeController {

    private Stage stage;
    private Scene scene;

    @FXML
    private Button btnHome,btnPlayers,btnMatches;
    

    @FXML
    private Pane homePane;

    @FXML
    void goToPlayers(ActionEvent event) throws IOException {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("players.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            }catch(Exception ex){
            ex.toString();
        }
    }

    @FXML
    void goToMatches(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("matches.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void goToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void onDisconnect(MouseEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
