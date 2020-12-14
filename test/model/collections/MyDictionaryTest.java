package model.collections;

import model.collections.nonObservable.MyDictionary;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.Hashtable;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MyDictionaryTest implements MyIDictionaryTest<MyDictionary<Object, Object>> {
    MyDictionary<Object, Object> dict;
    Map.Entry<Object, Object>[] entries;

    @Override
    public MyDictionary<Object, Object> getDict() {
        if (dict == null)
            dict = new MyDictionary<>();
        return dict;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map.Entry<Object, Object>[] getTwoEntries() {
        if (entries == null) {
            entries = (Map.Entry<Object, Object>[]) Array.newInstance(AbstractMap.SimpleEntry.class, 2);
            entries[0] = new AbstractMap.SimpleEntry<Object, Object>(new Object(), new Object());
            entries[1] = new AbstractMap.SimpleEntry<Object, Object>(new Object(), new Object());
        }
        return entries;
    }

    @Test
    public void TestToString_HashtableToString() {
        Hashtable<Object, Object> testExpectedResultSource = new Hashtable<>();
        testExpectedResultSource.put(getTwoEntries()[0].getKey(), getTwoEntries()[0].getValue());
        testExpectedResultSource.put(getTwoEntries()[1].getKey(), getTwoEntries()[1].getValue());
        getDict().put(getTwoEntries()[1].getKey(), getTwoEntries()[1].getValue());
        assertEquals(getDict().toString(), testExpectedResultSource.toString());
    }
}
