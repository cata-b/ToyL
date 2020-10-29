package model.collections;

import model.exceptions.InvalidParameterException;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public interface MyIDictionaryTest<T extends MyIDictionary<Object, Object>> {
    T getDict();
    Map.Entry<Object, Object>[] getTwoEntries();

    @BeforeEach
    default void SetUp() throws InvalidParameterException {
        getDict().put(getTwoEntries()[0].getKey(), getTwoEntries()[0].getValue());
    }

    @Test
    default void TestClear_DictEmpty() {
        getDict().clear();
        final boolean[] result = new boolean[1];
        assertDoesNotThrow(() -> { result[0] = getDict().containsKey(getTwoEntries()[0].getKey());});
        assertFalse(result[0]);
    }

    @Test
    default void TestContains_TrueThenFalse() {
        final boolean[] result = new boolean[1];
        assertDoesNotThrow(() -> {result[0] = getDict().contains(getTwoEntries()[0].getValue());});
        assertTrue(result[0]);
        assertDoesNotThrow(() -> {result[0] = getDict().contains(getTwoEntries()[1].getValue());});
        assertFalse(result[0]);
    }

    @Test
    default void TestContains_Throw() {
        assertThrows(InvalidParameterException.class, () -> getDict().contains(null));
    }

    @Test
    default void TestContainsKey_TrueThenFalse() {
        final boolean[] result = new boolean[1];
        assertDoesNotThrow(() -> {result[0] = getDict().containsKey(getTwoEntries()[0].getKey());});
        assertTrue(result[0]);
        assertDoesNotThrow(() -> {result[0] = getDict().containsKey(getTwoEntries()[1].getKey());});
        assertFalse(result[0]);
    }

    @Test
    default void TestContainsKey_Throw() {
        assertThrows(InvalidParameterException.class, () -> getDict().containsKey(null));
    }


    @Test
    default void TestGet_ItemThenNull() {
        final Object[] result = new Object[1];
        assertDoesNotThrow(() -> { result[0] = getDict().get(getTwoEntries()[0].getKey()); });
        assertSame(result[0], getTwoEntries()[0].getValue());

        assertDoesNotThrow(() -> { result[0] = getDict().get(getTwoEntries()[1].getKey()); });
        assertSame(result[0], null);
    }

    @Test
    default void TestGet_Throw() {
        assertThrows(InvalidParameterException.class, () -> getDict().get(null));
    }

    @Test
    default void TestIsEmpty_FalseThenTrue() {
        assertFalse(getDict().isEmpty());
        getDict().clear();
        assertTrue(getDict().isEmpty());
    }

    @Test
    default void TestPut_ValueIsAdded() {
        assertDoesNotThrow(() -> getDict().put(getTwoEntries()[1].getKey(), getTwoEntries()[1].getValue()));
        final Object[] result = new Object[1];
        assertDoesNotThrow(() -> {result[0] = getDict().get(getTwoEntries()[1].getKey());});
        assertSame(result[0], getTwoEntries()[1].getValue());
    }

    @Test
    default void TestPut_Throws() {
        assertThrows(InvalidParameterException.class, () -> getDict().put(null, null));
    }

    @Test
    default void TestRemove_PairRemovedThenNull() {
        final Object[] result = new Object[1];
        assertDoesNotThrow(() -> result[0] = getDict().remove(getTwoEntries()[0].getKey()));
        assertSame(result[0], getTwoEntries()[0].getValue());

        assertDoesNotThrow(() -> result[0] = getDict().remove(getTwoEntries()[1].getKey()));
        assertSame(result[0], null);
    }

    @Test
    default void TestRemove_Throws() {
        assertThrows(InvalidParameterException.class, () -> getDict().remove(null));
    }

    @Test
    default void TestSize_1() {
        assertEquals(getDict().size(), 1);
    }
}
