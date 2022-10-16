import java.sql.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static Statement stmt;

    public static void main(String[] args) throws Exception {
        String jdbcURL = "jdbc:mysql://localhost:3306/javaprojet";
        String username = "lucas";
        String password = "admin";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(jdbcURL, username, password);
        stmt = con.createStatement();
        launch(args);
        
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    
}