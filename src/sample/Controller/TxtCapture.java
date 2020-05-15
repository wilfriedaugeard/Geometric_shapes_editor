package sample.Controller;

import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import sample.View.ViewJavaFX;

public class TxtCapture extends Dialog<String> {

    private final GridPane rows;
    private final TextArea text;
    private final String valDef;
    private Controller controller;

    public TxtCapture(Controller controller, String title) {
        final DialogPane dialogPane = getDialogPane();

        this.controller = controller;
        this.valDef = "";
        this.rows = new GridPane();
        this.rows.setHgap(10);
        this.rows.setMaxWidth(Double.MAX_VALUE);
        this.rows.setAlignment(Pos.CENTER_LEFT);
        this.text = new TextArea(valDef);
        this.text.setMaxWidth(Double.MAX_VALUE);

        setTitle(title);
        dialogPane.contentTextProperty().addListener(o -> updateRows());
        dialogPane.getStyleClass().add("text-input-dialog");
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        setHeight(controller.getView().getHEIGHT()/16);

        GridPane.setHgrow(text, Priority.ALWAYS);
        GridPane.setFillWidth(text, true);

        updateRows();

        setResultConverter((dialogButton) -> {
            ButtonBar.ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
            return data == ButtonBar.ButtonData.OK_DONE ? text.getText() : null;
        });
    }

    public final TextArea getEditor() {
        return text;
    }

    public final String getValDef() {
        return valDef;
    }

    private void updateRows() {
        rows.getChildren().clear();

        rows.add(text, 1, 0);
        getDialogPane().setContent(rows);

        Platform.runLater(() -> text.requestFocus());
    }
}