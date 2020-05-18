package sample.Controller;

import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.View.ViewJavaFX;

public class TxtCapture extends Dialog<String> {
    private Controller controller;
    private String title;
    private final TextField resize;
    private final TextField rotation;

    private Button submit;
    private Button clear;
    private Button apply;
    private GridPane grid;
    private Stage stage;



    public TxtCapture(Controller controller, String title) {
        this.controller = controller;
        this.title = title;
        grid = new GridPane();

        resize = new TextField();
        rotation = new TextField();
        submit = new Button("Done");
        clear = new Button("Reset");
        apply = new Button("Apply");
        ui();

        VBox vBox = new VBox(grid);
        Scene scene = new Scene(vBox, 300,200);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void closeWindow(){
        stage.close();
    }


    public void ui(){

        //Creating a GridPane container
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Text scenetitle = new Text("SHAPE EDITOR");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0,0);

        //Defining theresize text field
        resize.setPromptText("Size of shape %");
        resize.setPrefColumnCount(10);
        resize.getText();
        GridPane.setConstraints(resize, 0, 1);
        grid.getChildren().add(resize);

        //Defining the Lastresize text field
        rotation.setPromptText("Rotate (-360 to 360)");
        GridPane.setConstraints(rotation, 0, 2);
        grid.getChildren().add(rotation);

        //Defining the Submit button
        GridPane.setConstraints(submit, 1, 1);
        grid.getChildren().add(submit);

        //Defining the Apply button
        GridPane.setConstraints(apply, 1, 2);
        grid.getChildren().add(apply);

        //Defining the Clear button
        GridPane.setConstraints(clear, 1, 3);
        grid.getChildren().add(clear);



    }

    public Button getApply() {
        return apply;
    }

    public TextField getResize() {
        return resize;
    }

    public TextField getRotation() {
        return rotation;
    }

    public Button getSubmit() {
        return submit;
    }

    public Button getClear() {
        return clear;
    }

}