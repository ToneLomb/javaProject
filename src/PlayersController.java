
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import exceptions.InvalidFieldException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PlayersController implements Initializable{

    private Stage stage;
    private Scene scene;

    @FXML
    private TableView<Player> playerTable;

    @FXML
    private TableColumn<Player, String> fnColumn,lnColumn,nationalityColumn,sexColumn;

    @FXML
    private TableColumn<Player, Integer> playerColumn;

    @FXML
    private TextField fnText,lnText,nationalityText;

    @FXML
    private ComboBox<String> sexBox;

    private String[] sexes = {"Man","Woman"};

    private int selectedRaw;


    @FXML
    void goToHome(ActionEvent event) throws IOException {
        /* homePane.getChildren().clear();
        homePane.getChildren().add((Node) FXMLLoader.load(getClass().getResource("./fxmlfiles/home.fxml"))); */
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void goToMatches(ActionEvent event) throws IOException {
        //homePane.getChildren().clear();
        //homePane.getChildren().add(mainPane);
        Parent root = FXMLLoader.load(getClass().getResource("matches.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void goToPlayers(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("players.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onDelete(MouseEvent event) throws SQLException {
        App.stmt.executeUpdate("Delete from players where IDPlayer = '"+ selectedRaw +"'");
        refresh();
    }

    @FXML
    void onInsert(ActionEvent event) throws InvalidFieldException, SQLException{
        try{
            String ln = lnText.getText();
            String fn = fnText.getText();
            String nationality = nationalityText.getText();
            String sex = sexBox.getSelectionModel().getSelectedItem();
            int ID;

            if(!ln.matches("^[A-Z][a-z]*$")){
                throw new InvalidFieldException("Last name");
            }

            if(!fn.matches("^[A-Z][a-z]*$")){
                throw new InvalidFieldException("First name");
            }

            if(nationality.equals("")) throw new InvalidFieldException("Nationality");

            String query = "insert into players (sex,firstname,lastname,nationality) values ('"+sex+"','"+fn+"','"+ln+"','"+nationality+"')";
            App.stmt.executeUpdate(query);
            refresh();

        }catch(InvalidFieldException | SQLException  ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    @FXML
    void onSelect(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("ID"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("sex"));
        lnColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("ln"));
        fnColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("fn"));
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("nationality"));
       

        playerTable.setItems(getPlayerList());
        playerTable.getColumns().setAll(playerColumn, sexColumn, lnColumn, fnColumn, nationalityColumn);

        sexBox.getItems().addAll(sexes);
        sexBox.getSelectionModel().selectFirst();

        playerTable.setOnMousePressed(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                try{
                    selectedRaw = playerTable.getSelectionModel().getSelectedItem().getID();
                }catch(Exception ex){
                    ex.toString();
                }
                
            }
            
        });
        
    }

    ObservableList<Player> getPlayerList() {

        ObservableList<Player> list = FXCollections.observableArrayList();
        String query = "select * from players";
        
        try {

            ResultSet res = App.stmt.executeQuery(query);

            while (res.next()) {
                int IDPlayer = res.getInt(1);
                String sex = res.getString(2);
                String ln = res.getString(3);
                String fn = res.getString(4);
                String nationality = res.getString(5);


                Player match = new Player(IDPlayer, sex, ln, fn, nationality);
                list.add(match);
            }
            return list;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return null;
        }
    }
    
    @FXML
    void onDisconnect(MouseEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void refresh() {
        ObservableList<Player> list = getPlayerList();
        playerTable.setItems(list);
    }

}
