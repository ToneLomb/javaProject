import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import exceptions.InvalidFieldException;
import exceptions.InvalidFormatException;
import exceptions.InvalidPlayerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MatchesController implements Initializable{

    private Stage stage;
    private Scene scene;

    @FXML
    private TableView<Matches> matchesTable;

    @FXML
    private TableColumn<Matches, Integer> matchColumn;

    @FXML
    private TableColumn<Matches, String> dateColumn,resultsColumn,courtColumn,player1Column,player2Column;

    @FXML
    private ComboBox<String> courtBox;

    @FXML
    private TextField p1Text,p2Text,dateText,resText,searchText;
    
    private String[] courts = {"Roland Garros", "Wimbledon", "US Open"};
    private int selectedRaw;


    @FXML
    void goToPlayers(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("players.fxml"));
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
    void goToHome(ActionEvent event) throws IOException {
        /* homePane.getChildren().clear();
        homePane.getChildren().add((Node) FXMLLoader.load(getClass().getResource("./fxmlfiles/home.fxml"))); */
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    ObservableList<Matches> getMatchesList(String query) {

        ObservableList<Matches> list = FXCollections.observableArrayList();
        
        try {

            ResultSet res = App.stmt.executeQuery(query);

            while (res.next()) {
                int IDmatch = res.getInt(1);
                String n1 = res.getString(2) + " " + res.getString(3);
                String n2 = res.getString(4) + " " + res.getString(5);
                String court = res.getString(6);
                String date = res.getString(7);
                String results = res.getString(8);


                Matches match = new Matches(IDmatch, n1, n2, court, date, results);
                list.add(match);
            }
            return list;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return null;
        }
    }

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        matchColumn.setCellValueFactory(new PropertyValueFactory<Matches, Integer>("Match"));
        player1Column.setCellValueFactory(new PropertyValueFactory<Matches, String>("Player1"));
        player2Column.setCellValueFactory(new PropertyValueFactory<Matches, String>("Player2"));
        courtColumn.setCellValueFactory(new PropertyValueFactory<Matches, String>("Court"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Matches, String>("Date"));
        resultsColumn.setCellValueFactory(new PropertyValueFactory<Matches, String>("Results"));

        matchesTable.setItems(getMatchesList("select IDmatch, p1.firstname as f1, p1.lastname AS l1, p2.firstname AS f2, p2.lastname AS l2, location, date,results from matches Join players as p1 on p1.IDplayer = matches.IDplayer1 Join players as p2 on p2.IDplayer = matches.IDplayer2 Join courts on matches.IDcourt = courts.IDcourt"));
        matchesTable.getColumns().setAll(matchColumn, player1Column, player2Column, courtColumn, dateColumn, resultsColumn);

        courtBox.getItems().addAll(courts);
        courtBox.getSelectionModel().selectFirst();

        matchesTable.setOnMousePressed(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                try{
                    selectedRaw = matchesTable.getSelectionModel().getSelectedItem().getMatch();
                }catch(Exception ex){
                    ex.toString();
                }
                
            }
            
        });
        
    }


    @FXML
    void onInsert(ActionEvent event) throws InvalidFieldException, InvalidPlayerException, SQLException, InvalidFormatException{
        try{
            String p1 = p1Text.getText();
            String p2 = p2Text.getText();
            String date = dateText.getText();
            String results = resText.getText();
            int ID1,ID2,IDcourt;

            

            if(!p1.equals("") && p1.contains(" ")){
                String[] p1Name = p1.split(" ");
                ResultSet res = App.stmt.executeQuery("Select IDplayer from players where firstname = '" + p1Name[0] + "' and lastname =   '" + p1Name[1] + "'");
                if(res.next()){
                    ID1 = res.getInt(1);
                    res.close();
                }else{
                    throw new InvalidPlayerException(p1Name[0],p1Name[1]);
                }
            }else{
                throw new InvalidFieldException("Player 1");
            }
            

            if(!p2.equals("") && p2.contains(" ")){
                String[] p2Name = p2.split(" ");
                ResultSet res = App.stmt.executeQuery("Select IDplayer from players where firstname = '" + p2Name[0] + "' and lastname =   '" + p2Name[1] + "'");
                if(res.next()){
                    ID2 = res.getInt(1);
                    res.close();
                }else{
                    throw new InvalidPlayerException(p2Name[0],p2Name[1]);
                }
            }else{
                throw new InvalidFieldException("Player 2");
            }

            if(!date.matches("^(19[0-9]{2}|2[0-9]{3})[-](0[1-9]|1[0-2])[-](0[1-9]|1[0-9]|2[0-9]|3[0-1])$")){
                throw new InvalidFormatException("Date");
            }

            if(!results.matches("^([W][-][L])|([L][-][W])$")){
                throw new InvalidFormatException("Results");
            }

            ResultSet res1 = App.stmt.executeQuery("Select IDcourt from courts where location = '" + courtBox
            .getSelectionModel().getSelectedItem()+ "'");
            res1.next();
            IDcourt = res1.getInt(1);
            res1.close();

            String query = "insert into matches (IDplayer1,IDplayer2,IDCourt,date,results) values ('"+ID1+"','"+ID2+"','"+IDcourt+"','"+date+"','"+results+"')";
            App.stmt.executeUpdate(query);
            refresh();

        }catch(InvalidFieldException | InvalidPlayerException | SQLException | InvalidFormatException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }


    @FXML
    void onDelete(MouseEvent event) throws SQLException {
        App.stmt.executeUpdate("Delete from matches where IDMatch = '"+ selectedRaw +"'");
        refresh();
    }

    @FXML 
    void onSearch(MouseEvent event){
        String search = searchText.getText();
        String query = "select IDmatch, p1.firstname as f1, p1.lastname AS l1, p2.firstname AS f2, p2.lastname AS l2, location, date,results from matches Join players as p1 on p1.IDplayer = matches.IDplayer1 Join players as p2 on p2.IDplayer = matches.IDplayer2 Join courts on matches.IDcourt = courts.IDcourt where p1.firstname like '%"+search+"%' or p1.lastname like '%"+search+"%' or p2.firstname like '%"+search+"%' or p2.lastname like '%"+search+"%';";
        matchesTable.setItems(getMatchesList(query));;
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
        ObservableList<Matches> list = getMatchesList("select IDmatch, p1.firstname as f1, p1.lastname AS l1, p2.firstname AS f2, p2.lastname AS l2, location, date,results from matches Join players as p1 on p1.IDplayer = matches.IDplayer1 Join players as p2 on p2.IDplayer = matches.IDplayer2 Join courts on matches.IDcourt = courts.IDcourt");
        matchesTable.setItems(list);
    }

}
