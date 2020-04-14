package sample.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.shape.Shape;
import sample.Model.RGB;
import sample.Model.ShapeInter;

import java.util.ArrayList;

public class EditShapeEvent implements Events{
    private Controller controller;

    public EditShapeEvent(Controller controller) {
        this.controller = controller;
    }


    EventHandler<ActionEvent> editShape = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            System.out.println("Cas 4. Édition des propriétés des objets.");
            System.out.println("Une boite de dialogue s'ouvre : elle contient les paramètres que l'on peut modifier.\n" +
                    "L'utilisateur change un ou plusieurs des paramètres.\n" +
                    "Il appuie sur :\n" +
                    "\n" +
                    "    \"Appliquer\": les modifications sont appliquées, et il peut continuer à changer des paramètres.\n" +
                    "    \"Ok\": les modifications sont appliquées et le dialogue se ferme.\n" +
                    "    \"Annuler\": toutes les modifications réalisées depuis l'ouverture du dialogue sont annulées");
        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_editShape(editShape);
    }
}
