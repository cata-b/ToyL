package model.collections.observable;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import model.collections.nonObservable.MyList;
import model.exceptions.IndexOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;

public class MyObservableList<T> extends MyList<T> {
    private final ReadOnlyListWrapper<T> obs =  new ReadOnlyListWrapper<>(FXCollections.observableList(new ArrayList<>()));
    private Boolean doRunLater = true;

    public void enableRunLater() {
        doRunLater = true;
    }

    public void disableRunLater() {
        doRunLater = false;
    }

    private void runLaterIfNeeded(Runnable method) {
        if (doRunLater)
            Platform.runLater(method);
        else method.run();
    }

    @Override
    public synchronized void add(T element) {
        super.add(element);
        runLaterIfNeeded(() -> obs.add(element));
    }

    @Override
    public synchronized void add(int index, T element) throws IndexOutOfBoundsException {
        super.add(index, element);
        runLaterIfNeeded(() -> obs.add(index, element));
    }

    @Override
    public synchronized T remove(int index) throws IndexOutOfBoundsException {
        var retVal = super.remove(index);
        runLaterIfNeeded(() -> obs.remove(index));
        return retVal;
    }

    @Override
    public synchronized boolean remove(T element) {
        var retVal = super.remove(element);
        runLaterIfNeeded(() -> obs.remove(element));
        return retVal;
    }

    @Override
    public synchronized T set(int index, T element) throws IndexOutOfBoundsException {
        var retVal = super.set(index, element);
        runLaterIfNeeded(() -> obs.set(index, element));
        return retVal;
    }

    @Override
    public synchronized void clear() {
        super.clear();
        runLaterIfNeeded(obs::clear);
    }

    @Override
    public synchronized void setContent(List<T> content) {
        super.setContent(content);
        runLaterIfNeeded(() -> {
            obs.clear();
            obs.addAll(content);
        });
    }

    public ReadOnlyListProperty<T> getContentProperty() {
        return obs.getReadOnlyProperty();
    }
}
