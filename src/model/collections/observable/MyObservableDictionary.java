package model.collections.observable;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.collections.FXCollections;
import model.collections.interfaces.MyIDictionary;
import model.collections.nonObservable.MyDictionary;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MyObservableDictionary<K, V> extends MyDictionary<K, V> {
    private final ReadOnlyMapWrapper<K, V> obs = new ReadOnlyMapWrapper<>(FXCollections.observableHashMap());
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
    public synchronized void clear() {
        super.clear();
        runLaterIfNeeded(obs::clear);
    }

    @Override
    public synchronized V put(@NotNull K key, @NotNull V value) {
        var retVal = super.put(key, value);
        runLaterIfNeeded(() -> obs.put(key, value));
        return retVal;
    }

    @Override
    public synchronized V remove(@NotNull K key) {
        var retVal =  super.remove(key);
        runLaterIfNeeded(() -> obs.remove(key));
        return retVal;
    }

    @Override
    public synchronized Map<K, V> setContent(Map<K, V> content) {
        var oldMap = super.setContent(content);
        runLaterIfNeeded(() -> {
            obs.clear();
            obs.putAll(content);
        });
        return oldMap;
    }

    @Override
    public MyIDictionary<K, V> shallowCopy() {
        MyObservableDictionary<K, V> copy = new MyObservableDictionary<>();
        copy.disableRunLater();
        copy.setContent(getContent());
        copy.doRunLater = this.doRunLater;
        return copy;
    }

    public ReadOnlyMapProperty<K, V> getContentProperty() {
        return obs.getReadOnlyProperty();
    }
}
