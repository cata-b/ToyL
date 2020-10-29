package model.collections;

import model.exceptions.EmptyCollectionException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public interface MyIStackTest<T extends MyIStack<Object>> {
    T getStack();
    Object getObject();

    @BeforeEach
    default void setUp() {
        getStack().push(getObject());
    }

    @Test
    default void TestPush_Pushes() {
        getStack().push(getObject());
    }

    @Test
    default void TestPop_PopsThenThrowsEmptyCollectionException() {
        assertDoesNotThrow(() -> getStack().pop());
        assertThrows(EmptyCollectionException.class, () -> getStack().pop());
    }

    @Test
    default void TestPeek_TopObjectThenThrowsEmptyCollectionException() {
        final Object[] ob = new Object[1];
        assertDoesNotThrow(() -> {ob[0] = getStack().peek();});
        assert(ob[0] == getObject());
        assertDoesNotThrow(() -> getStack().pop());
        assertThrows(EmptyCollectionException.class, () -> getStack().peek());
    }

    @Test
    default void TestEmpty_FalseThenTrue() {
        assertFalse(getStack().empty());
        assertDoesNotThrow(() -> getStack().pop());
        assertTrue(getStack().empty());
    }
}