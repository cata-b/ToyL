package GUI.windows.MainWindow;

import GUI.windows.ProgramSelectionWindow.ProgramSelectionWindowController;
import controller.Controller;
import controller.IController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.PrgState;
import model.collections.nonObservable.MyDictionary;
import model.collections.nonObservable.MyStack;
import model.collections.observable.MyObservableDictionary;
import model.collections.observable.MyObservableHeap;
import model.collections.observable.MyObservableList;
import model.exceptions.MyException;
import model.exceptions.ProgramFinishedException;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class MainWindowController {
    @FXML
    private Button btnOneStep;
//    @FXML
//    private Button btnRestart;
//    @FXML
//    private Button btnSelectExample;

    @FXML
    private TableView<Map.Entry<Integer, IValue>> tableViewHeap;
    private final ListProperty<Map.Entry<Integer, IValue>> tableViewHeapContent = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, String> tableViewHeapKey;
    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, String> tableViewHeapValue;

    @FXML
    private ListView<String> listViewFiles;
    private final ListProperty<String> listViewFilesContent = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
    @FXML
    private ListView<IValue> listViewOut;
    @FXML
    private ListView<PrgState> listViewPrgStates;

    @FXML
    private ListView<IStmt> listViewExeStack;

    @FXML
    private TableView<Map.Entry<String, IValue>> tableViewSymTable;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> tableViewSymTableKey;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> tableViewSymTableValue;

    @FXML
    private TextField textFieldPrgStateCount;

    private IStmt currentProgram;

    private final ObjectProperty<PrgState> selectedPrgState = new SimpleObjectProperty<>();
    private final ObjectProperty<IController> currentController = new SimpleObjectProperty<>();
    private final ObjectProperty<IRepository> currentRepository = new SimpleObjectProperty<>();

    public static URL getWindowResource() {
        return MainWindowController.class.getResource("MainWindow.fxml");
    }

    private IStmt showProgramSelectionWindow(Boolean withCancel) {
        var selectedProgram = new SimpleObjectProperty<>();
        ProgramSelectionWindowController selectPrgController;
        Stage selectPrgWindow;
        try {
            var loader = new FXMLLoader(ProgramSelectionWindowController.getWindowResource());
            selectPrgWindow = loader.load();
            selectPrgController = loader.getController();
        } catch (IOException ignored) {return null;}

        selectPrgController.setCancelButtonVisibility(withCancel);

        selectedProgram.bind(selectPrgController.selectedStmtProperty());

        BooleanProperty dialogConfirmed = new SimpleBooleanProperty();
        dialogConfirmed.bind(selectPrgController.confirmedProperty());

        selectPrgWindow.initModality(Modality.APPLICATION_MODAL);
        selectPrgWindow.centerOnScreen();
        selectPrgWindow.showAndWait();

        if (dialogConfirmed.get())
            return (IStmt) selectedProgram.get();
        else
            return null;
    }

    /**
     * Binds the heap tableView to the heap of the given PrgState
     * @param heap The heap of the initial PrgState in the repository
     */
    private void prepareTableViewHeap(MyObservableHeap heap) {
        if (tableViewHeap.prefHeightProperty().isBound())
            tableViewHeap.prefHeightProperty().unbind();
        tableViewHeapContent.addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<Map.Entry<Integer, IValue>>> observableValue, ObservableList<Map.Entry<Integer, IValue>> oldMap, ObservableList<Map.Entry<Integer, IValue>> newMap) {
                if (newMap.size() > 0) {
                    Platform.requestNextPulse();
                    Platform.runLater(() -> {
                        var tableElement = tableViewHeap.lookup(".table-row-cell");
                        if (tableElement != null) {
                            tableViewHeap.prefHeightProperty().bind(Bindings.size(tableViewHeap.itemsProperty().get()).add(1).multiply(tableElement.prefHeight(1)).add(24));
                            tableViewHeapContent.removeListener(this);
                        }
                    });
                }
            }
        });
        heap.getContentProperty().addListener((observableValue, oldMap, newMap) -> {
            tableViewHeapContent.clear();
            tableViewHeapContent.addAll(newMap.entrySet());
        });
        tableViewHeap.setItems(tableViewHeapContent);
    }

    /**
     * Binds the FileTable listView with the fileTable of the given PrgState
     * and makes the listView adapt to the height of its contents
     * @param fileTable The file table of the initial PrgState in the repository
     */
    private void prepareListViewFiles(MyObservableDictionary<StringValue, BufferedReader> fileTable) {
        if (listViewFiles.prefHeightProperty().isBound())
            listViewFiles.prefHeightProperty().unbind();
        listViewFilesContent.addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<String>> observableValue, ObservableList<String> oldList, ObservableList<String> newList) {
                if (newList.size() > 0) {
                    Platform.requestNextPulse();
                    Platform.runLater(() -> {
                        var listElement = listViewFiles.lookup(".list-cell");
                        if (listElement != null) {
                            // bind height of the list to item no. * item height when data becomes non-empty
                            listViewFiles.prefHeightProperty().bind(Bindings.size(listViewFiles.itemsProperty().get()).multiply(listViewFiles.lookup(".list-cell").prefHeight(1)));
                            // remove the change listener afterwards
                            listViewFilesContent.removeListener(this);
                        }
                    });
                }
            }

        });
        fileTable.getContentProperty().addListener((observableValue, oldMap, newMap) -> {
            listViewFilesContent.clear();
            listViewFilesContent.addAll(newMap.keySet().stream().map(StringValue::getVal).collect(Collectors.toList()));
        });
        listViewFiles.setItems(listViewFilesContent);
    }

    /**
     * Binds the Out listView with the Out list of the given PrgState
     * and makes the listView adapt to the height of its contents
     * @param outList The initial PrgState's output list
     */
    private void prepareListViewOut(MyObservableList<IValue> outList) {
        if (listViewOut.prefHeightProperty().isBound())
            listViewOut.prefHeightProperty().unbind();
        outList.getContentProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<IValue>> observableValue, ObservableList<IValue> oldList, ObservableList<IValue> newList) {
                if (newList.size() > 0)
                    Platform.runLater(() -> {
                        var listElement = listViewOut.lookup(".list-cell");
                        if (listElement != null) {
                            listViewOut.prefHeightProperty().bind(Bindings.size(listViewOut.itemsProperty().get()).multiply(listElement.prefHeight(1)));
                            outList.getContentProperty().removeListener(this);
                        }
                    });
            }
        });
        listViewOut.setItems(outList.getContentProperty());
    }

    private static Controller createControllerWithProgram(IStmt program, IRepository repository) throws MyException {
        program.typeCheck(new MyDictionary<>());
        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyObservableList<>(),
                new MyObservableDictionary<>(),
                new MyObservableHeap(),
                program);
        repository.addProgram(state);
        return new Controller(repository);
    }

    /**
     * Binds listViewPrgStates to the PrgState list from the given repository
     * and makes the listView adapt to the height of its contents
     * @param prgStateList the program state list of the repository
     */
    private void prepareListViewPrgStates(MyObservableList<PrgState> prgStateList) {
        if (listViewPrgStates.prefHeightProperty().isBound())
            listViewPrgStates.prefHeightProperty().unbind();
        prgStateList.getContentProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<PrgState>> observableValue, ObservableList<PrgState> oldList, ObservableList<PrgState> newList) {
                if (newList.size() > 0)
                    Platform.runLater(() -> {
                        var listElement = listViewPrgStates.lookup(".list-cell");
                        if (listElement != null) {
                            listViewPrgStates.prefHeightProperty().bind(Bindings.size(listViewPrgStates.itemsProperty().get()).multiply(listElement.prefHeight(1)));
                            prgStateList.getContentProperty().removeListener(this);
                        }
                    });
            }
        });
        listViewPrgStates.setItems(prgStateList.getContentProperty());
    }

    private void prepareCurrentProgram(IStmt programContent) throws MyException {
        var prgList = new MyObservableList<PrgState>();
        prgList.disableRunLater();
        currentRepository.set(new Repository(prgList, "log.out"));
        currentController.set(createControllerWithProgram(programContent, currentRepository.get()));
        var initialState = currentRepository.get().getPrgList().get(0);

        prepareTableViewHeap((MyObservableHeap)(initialState.getHeap()));
        prepareListViewFiles((MyObservableDictionary<StringValue, BufferedReader>)(initialState.getFileTable()));
        prepareListViewOut((MyObservableList<IValue>)(initialState.getOut()));

        prgList.enableRunLater();
        prepareListViewPrgStates(prgList);

        textFieldPrgStateCount.textProperty().bind(Bindings.size(listViewPrgStates.getItems()).asString());
    }

    @FXML
    public void initialize() {
        tableViewHeapKey.setCellValueFactory(entryStringCellDataFeatures ->
                new SimpleStringProperty(entryStringCellDataFeatures.getValue().getKey().toString()));
        tableViewHeapValue.setCellValueFactory(entryStringCellDataFeatures ->
                new SimpleStringProperty(entryStringCellDataFeatures.getValue().getValue().toString()));

        tableViewSymTableKey.setCellValueFactory(entryStringCellDataFeatures ->
                new SimpleStringProperty(entryStringCellDataFeatures.getValue().getKey().toString()));
        tableViewSymTableValue.setCellValueFactory(entryStringCellDataFeatures ->
                new SimpleStringProperty(entryStringCellDataFeatures.getValue().getValue().toString()));

        listViewPrgStates.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listViewPrgStates.getSelectionModel().selectedItemProperty().addListener((observableValue, oldState, newState) -> {
            if (newState != null) {
                selectedPrgState.set(newState);
                listViewExeStack.setItems(FXCollections.observableList(selectedPrgState.get().getExeStack().getContent()));
                tableViewSymTable.setItems(FXCollections.observableList(new ArrayList<>(selectedPrgState.get().getSymTable().getContent().entrySet())));
            }
        });
        listViewPrgStates.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(PrgState item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(Integer.toString(item.getId()));
                }
            }
        });

        currentProgram = showProgramSelectionWindow(false);
        if (currentProgram == null) {
            Platform.runLater(() -> ((Stage)btnOneStep.getScene().getWindow()).close());
            return;
        }
        Platform.runLater(() -> {
            try {
                prepareCurrentProgram(currentProgram);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void btnOneStepClicked(ActionEvent e) {
        try {
            currentController.get().oneStep();
            if (selectedPrgState.get() != null) {
                listViewExeStack.setItems(FXCollections.observableList(selectedPrgState.get().getExeStack().getContent()));
                tableViewSymTable.setItems(FXCollections.observableList(new ArrayList<>(selectedPrgState.get().getSymTable().getContent().entrySet())));
            }
        } catch (ProgramFinishedException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Program finished");
            alert.setHeaderText("The current program is finished.");
            alert.setContentText("Use \"Restart\" to restart it, or select another program with \"Select example\"");
            alert.showAndWait();
        }

    }

    private void clearUI() {
        listViewFilesContent.clear();
        //listViewFiles.setItems(null);
        listViewOut.prefHeightProperty().unbind();
        listViewOut.prefHeightProperty().set(0);
        //listViewOut.setItems(null);
        listViewPrgStates.prefHeightProperty().unbind();
        listViewPrgStates.prefHeightProperty().set(24);
        //listViewPrgStates.setItems(null);
        tableViewHeapContent.clear();
        //tableViewHeap.setItems(null);
        selectedPrgState.set(null);
        listViewExeStack.setItems(null);
        tableViewSymTable.setItems(null);
    }

    @FXML
    private void btnRestartClicked(ActionEvent e) throws MyException {
        clearUI();
        prepareCurrentProgram(currentProgram);
    }

    @FXML
    private void btnSelectExampleClicked(ActionEvent e) throws MyException {
        var selectedProgram = showProgramSelectionWindow(true);
        if (selectedProgram != null) {
            clearUI();
            currentProgram = selectedProgram;
            prepareCurrentProgram(selectedProgram);
        }
    }
}
