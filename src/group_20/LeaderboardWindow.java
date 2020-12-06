import javafx.application.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.collections.transformation.FilteredList;

public class LeaderboardWindow extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ObservableList<Profile> observableList = FXCollections.observableArrayList(Leaderboard.arrayListOfProfileInstances);
    
        FilteredList<Profile> filteredList = new FilteredList<>(observableList, p -> true);

        System.out.println(getProfile());
        System.out.println(observableList);
        System.out.println(filteredList);

        //-----
        //test code, create Profile instances
        Profile.controllerBoardID = 0;
        Profile.setNoOfBoard(3);

        int[][] profile0 = {{171,115,83}, {88,75,55} ,{83,40,28}};	//Alice
		int[][] profile1 = {{92,81,144}, {79,34,100} ,{13,47,44}};	//Bob
		int[][] profile2 = {{154,63,91}, {91,30,17} ,{62,33,74}};	//Chuck
		int[][] profile3 = {{88,96,68}, {59,66,47} ,{29,30,21}};	//Craig
		int[][] profile4 = {{155,115,101}, {55,37,79} ,{100,78,22}};	//Charlie

		Profile testprofile0 = new Profile("Alice", profile0[0], profile0[1], profile0[2]);
		Profile testprofile1 = new Profile("Bob", profile1[0], profile1[1], profile1[2]);
		Profile testprofile2 = new Profile("Chuck", profile2[0], profile2[1], profile2[2]);
		Profile testprofile3 = new Profile("Craig", profile3[0], profile3[1], profile3[2]);
		Profile testprofile4 = new Profile("Charlie", profile4[0], profile4[1], profile4[2]);
        //-----

        TableView<Profile> table = new TableView<Profile>();
        table.setItems(getProfile());

        TableColumn<Profile, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Profile, Integer> winColumn = new TableColumn<>("Wins");
        winColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));

        TableColumn<Profile, Integer> lossColumn = new TableColumn<>("Losses");
        lossColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));

        TableColumn<Profile, Integer> gameColumn = new TableColumn<>("Games Played");
        gameColumn.setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));
        
        table.getColumns().addAll(nameColumn,winColumn,lossColumn,gameColumn);
        winColumn.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().add(winColumn);

        ToggleButton toggleButton1 = new ToggleButton("Wins Ascending");
        ToggleButton toggleButton2 = new ToggleButton("Wins Descending");
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleButton1.setToggleGroup(toggleGroup);
        toggleButton2.setToggleGroup(toggleGroup);

        toggleButton1.setOnAction(actionEvent -> {
            winColumn.setSortType(TableColumn.SortType.ASCENDING);
        });

        toggleButton2.setOnAction(actionEvent -> {
            winColumn.setSortType(TableColumn.SortType.DESCENDING);
        });

        toggleButton1.setDisable(true);
        toggleButton2.setDisable(true);

        TextField textField = new TextField();
        textField.setPromptText("Board ID");
        textField.textProperty().addListener((observable,oldValue,newValue) -> {
            try {
                if((Integer.parseInt(textField.getText()) >= 0) && (Integer.parseInt(textField.getText())) < Profile.getNoOfBoard()) {
                    Profile.controllerBoardID = Integer.parseInt(textField.getText());

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

    public ObservableList<Profile> getProfile() {
        ObservableList<Profile> observableList = FXCollections.observableArrayList(Leaderboard.arrayListOfProfileInstances);
        return observableList;
    }
}