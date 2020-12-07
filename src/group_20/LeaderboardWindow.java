import java.util.*;
import javafx.application.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.collections.transformation.*;
import javafx.beans.property.*;

public class LeaderboardWindow extends Application{
    private int boardIDInput = 0;

    private int getBoardIDInput() {
        return boardIDInput;
    }

    private void setBoardIDInput(int boardIDInput) {
        this.boardIDInput = boardIDInput;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        //--------------------
        //creating test Profile instances
        Profile.setNoOfBoard(3);

        int[][] profile0 = {{171,115,83}, {88,75,55} ,{83,40,28}};	//Alice
		int[][] profile1 = {{92,81,144}, {79,34,100} ,{13,47,44}};	//Bob
		int[][] profile2 = {{154,0,91}, {91,0,17} ,{62,0,74}};	//Chuck
		int[][] profile3 = {{88,96,68}, {59,66,47} ,{29,30,21}};	//Craig
		int[][] profile4 = {{155,115,101}, {55,37,79} ,{100,78,22}};	//Charlie

		Profile testprofile0 = new Profile("Alice", profile0[0], profile0[1], profile0[2]);
		Profile testprofile1 = new Profile("Bob", profile1[0], profile1[1], profile1[2]);
		Profile testprofile2 = new Profile("Chuck", profile2[0], profile2[1], profile2[2]);
		Profile testprofile3 = new Profile("Craig", profile3[0], profile3[1], profile3[2]);
		Profile testprofile4 = new Profile("Charlie", profile4[0], profile4[1], profile4[2]);
        //--------------------

        TableView<Profile> table = new TableView<Profile>();
        table.getItems().clear();
        table.setItems(Leaderboard.getProfilesByBoardID(getBoardIDInput()));

        TableColumn<Profile, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Profile, Integer> winColumn = new TableColumn<>("Wins");
        TableColumn<Profile, Integer> lossColumn = new TableColumn<>("Losses");
        TableColumn<Profile, Integer> gameColumn = new TableColumn<>("Games Played");

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        winColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumWins(getBoardIDInput())).asObject());
        lossColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumLosses(getBoardIDInput())).asObject());
        gameColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumGamesPlayed(getBoardIDInput())).asObject());
        
        table.getColumns().addAll(nameColumn,winColumn,lossColumn,gameColumn);
        winColumn.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().add(winColumn);

        //ToggleButton in a ToggleGroup
        ToggleButton toggleButton1 = new ToggleButton("Wins Ascending");
        ToggleButton toggleButton2 = new ToggleButton("Wins Descending");

        ToggleGroup toggleGroup = new ToggleGroup();

        toggleButton1.setToggleGroup(toggleGroup);
        toggleButton2.setToggleGroup(toggleGroup);

        toggleButton1.setOnAction(actionEvent -> {  //event handler for toggleButton1
            table.getItems().clear();
            table.setItems(Leaderboard.getProfilesByBoardID(getBoardIDInput()));
           
            table.getSortOrder().clear();
            table.getSortOrder().add(winColumn);
            
            winColumn.setSortType(TableColumn.SortType.ASCENDING);
        });

        toggleButton2.setOnAction(actionEvent -> {  //event handler for toggleButton2
            table.getItems().clear();
            table.setItems(Leaderboard.getProfilesByBoardID(getBoardIDInput()));

            table.getSortOrder().clear();
            table.getSortOrder().add(winColumn);

            winColumn.setSortType(TableColumn.SortType.DESCENDING);
        });

        toggleButton1.setDisable(true);
        toggleButton2.setDisable(true);

        TextField textField = new TextField();
        textField.setPromptText("Board ID: (1-" + Profile.getNoOfBoard() + ")");
        
        textField.textProperty().addListener((observable,oldValue,newValue) -> {
            try {
                if((Integer.parseInt(textField.getText()) > 0) && (Integer.parseInt(textField.getText())) <= Profile.getNoOfBoard()) {
                    setBoardIDInput(Integer.parseInt(textField.getText()) - 1);

                    table.getItems().clear();
                    table.setItems(Leaderboard.getProfilesByBoardID(getBoardIDInput()));

                    table.getSortOrder().clear();
                    table.getSortOrder().add(winColumn);

                    if(toggleButton1.isSelected()) {
                        winColumn.setSortType(TableColumn.SortType.ASCENDING);
                    } else {
                        winColumn.setSortType(TableColumn.SortType.DESCENDING);
                    }

                    textField.setStyle("-fx-text-fill: black;");

                    toggleButton1.setDisable(false);
                    toggleButton2.setDisable(false);
                } else {
                    textField.setStyle("-fx-text-fill: red;");
                }
            } catch (NumberFormatException e) {
                toggleButton1.setDisable(true);
                toggleButton2.setDisable(true);

                if(textField.getText().isEmpty()) {
                    textField.setStyle("-fx-text-fill: black;");
                } else {
                    textField.setStyle("-fx-text-fill: red;");
                }
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(toggleButton1,toggleButton2,textField,table);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}