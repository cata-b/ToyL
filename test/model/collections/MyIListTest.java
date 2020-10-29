package model.collections;

import model.exceptions.IndexOutOfBoundsException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public interface MyIListTest<T extends MyIList<Object>> {
    T getList();
    Object[] getTwoObjects();

    @BeforeEach
    default void SetUp() {
        Object[] obs = getTwoObjects();
        getList().add(obs[0]);
    }

    @Test
    default void TestAdd_AddsAtEnd() {
        getList().add(getTwoObjects()[1]);
    }

    @Test
    default void TestAdd_AddsAtIndexThenThrowsTwice() {
        assertDoesNotThrow(() -> getList().add(1, getTwoObjects()[0]));
        assertThrows(IndexOutOfBoundsException.class, () -> getList().add(-1, getTwoObjects()[0]));
        assertThrows(IndexOutOfBoundsException.class, () -> getList().add(3, getTwoObjects()[0]));
    }

    @Test
    default void TestRemove_True() {
        assertTrue(() -> getList().remove(getTwoObjects()[0]));
    }

    @Test
    default void TestRemove_False() {
        assertFalse(() -> getList().remove(getTwoObjects()[1]));
    }

    @Test
    default void TestRemove_PassThenThrow() {
        assertDoesNotThrow(() -> getList().remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> getList().remove(0));
    }

    @Test
    default void TestGet_FirstElementThenThrow() {
        final Object[] ob = new Object[1];
        assertDoesNotThrow(() -> { ob[0] = getList().get(0);});
        assertSame(ob[0], getTwoObjects()[0]);
        assertThrows(IndexOutOfBoundsException.class, () -> getList().get(1));
    }

    @Test
    default void TestContains_TrueThenFalse() {
        assertTrue(getList().contains(getTwoObjects()[0]));
        assertFalse(getList().contains(getTwoObjects()[1]));
    }

    @Test
    default void TestIsEmpty_FalseThenTrue() {
        assertFalse(getList().isEmpty());
        assertDoesNotThrow(() -> getList().remove(0));
        assertTrue(getList().isEmpty());
    }

    @Test
    default void TestIndexOf_0ThenMinus1() {
        assertEquals(getList().indexOf(getTwoObjects()[0]), 0);
        assertEquals(getList().indexOf(getTwoObjects()[1]), -1);
    }

    @Test
    default void TestLastIndexOf_1ThenMinus1() {
        assertDoesNotThrow(() -> getList().add(getTwoObjects()[0]));
        assertEquals(getList().lastIndexOf(getTwoObjects()[0]), 1);
        assertEquals(getList().lastIndexOf(getTwoObjects()[1]), -1);
    }

    @Test
    default void TestSize_1() {
        assertEquals(getList().size(), 1);
    }

    @Test
    default void TestSet_SetsThenThrows() {
        assertDoesNotThrow(() -> getList().set(0, getTwoObjects()[1]));
        final Object[] ob = new Object[1];
        assertDoesNotThrow(() -> {ob[0] = getList().get(0);});
        assertSame(ob[0], getTwoObjects()[1]);

        assertThrows(IndexOutOfBoundsException.class, () -> getList().set(1, getTwoObjects()[0]));
    }

    @Test
    default void TestClear_Clears() {
        getList().clear();
        assertEquals(getList().size(), 0);
    }

}
