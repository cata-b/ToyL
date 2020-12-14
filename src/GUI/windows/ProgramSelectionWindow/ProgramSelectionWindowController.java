package GUI.windows.ProgramSelectionWindow;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.collections.nonObservable.MyDictionary;
import model.exceptions.TypeCheckException;
import model.statements.*;
import model.ExamplePrograms;

import java.net.URL;

public class ProgramSelectionWindowController {
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ListView<IStmt> listView;

    private final ReadOnlyObjectWrapper<IStmt> selectedStmt = new ReadOnlyObjectWrapper<>();
    public ReadOnlyObjectProperty<IStmt> selectedStmtProperty() {
        return selectedStmt.getReadOnlyProperty();
    }

    private final ReadOnlyBooleanWrapper confirmed = new ReadOnlyBooleanWrapper(false);
    public ReadOnlyBooleanProperty confirmedProperty() {
        return confirmed.getReadOnlyProperty();
    }

    @FXML
    public void initialize() {
        // listView
        listView.setItems(getExamples());
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        selectedStmt.bind(listView.getSelectionModel().selectedItemProperty());
        listView.getSelectionModel().selectFirst();
    }

    public void setCancelButtonVisibility(Boolean visibility) {
        cancelButton.setVisible(visibility);
    }

    @FXML
    private void okButtonClicked(ActionEvent e) {
        try {
            selectedStmt.get().typeCheck(new MyDictionary<>());
        } catch (TypeCheckException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Type checking failed");
            alert.setHeaderText("The selected program is not valid.");
            alert.setContentText("Type checking failed for the selected program.\nError: " + ex.getMessage());
            alert.showAndWait();
            return;
        }

        confirmed.set(true);
        ((Stage)okButton.getScene().getWindow()).close();
    }

    @FXML
    private void cancelButtonClicked(ActionEvent e) {
        confirmed.set(false);
        ((Stage)cancelButton.getScene().getWindow()).close();
    }

    public static URL getWindowResource() {
        return ProgramSelectionWindowController.class.getResource("ProgramSelectionWindow.fxml");
    }

    private ObservableList<IStmt> getExamples() {
        return FXCollections.observableList(ExamplePrograms.getExamples().getContent());
    }

}
