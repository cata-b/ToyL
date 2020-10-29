package model.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MyListTest implements MyIListTest<MyList<Object>> {
    MyList<Object> list;
    Object[] twoObjects;

    @Override
    public MyList<Object> getList() {
        if (list == null)
            list = new MyList<>();
        return list;
    }

    @Override
    public Object[] getTwoObjects() {
        if (twoObjects == null) {
            twoObjects = new Object[2];
            twoObjects[0] = new Object();
            twoObjects[1] = new Object();
        }
        return twoObjects;
    }

    @Test
    public void TestToString_ArrayListString() {
        ArrayList<Object> testExpectedResultSource = new ArrayList<>();
        testExpectedResultSource.add(getTwoObjects()[0]);
        testExpectedResultSource.add(getTwoObjects()[1]);
        getList().add(getTwoObjects()[1]);
        assertEquals(getList().toString(), testExpectedResultSource.toString());
    }
}
