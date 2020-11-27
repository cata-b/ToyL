package model.collections;

import model.exceptions.EmptyCollectionException;
import model.exceptions.IndexOutOfBoundsException;

import java.util.List;

public interface MyIList<T> {
    /**
     * Adds an element to the end of the list.
     * @param element the element to be added
     */
    void add(T element);

    /**
     * Adds an element at a specified index in the list
     * @param index the index where to place the element
     * @param element the element to insert in the list
     * @throws IndexOutOfBoundsException if index < 0 || index > size()
     */
    void add(int index, T element) throws IndexOutOfBoundsException;

    /**
     * Removes an element from a specified index
     * @param index the index to remove the element from
     * @return the removed element
     * @throws IndexOutOfBoundsException if index < 0 || index >= size()
     */
    T remove(int index) throws IndexOutOfBoundsException;

    /**
     * Removes the first occurrence of an element from the list
     * @param element the element to remove, if element.equals(item_in_list)
     * @return true if an element was removed
     */
    boolean remove(T element);

    /**
     * Gets an element at a specific index
     * @param index the index to get the element from
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if index < 0 || index >= size()
     */
    T get(int index) throws IndexOutOfBoundsException;

    /**
     * Checks if the list contains an element (based on equals())
     * @param element the element to look for
     * @return true if the element exists in the list
     */
    boolean contains(T element);

    /**
     * @return true if the list is empty
     */
    boolean isEmpty();

    /**
     * Gets the first position of an element in the list
     * @param element the element to look for (with equals())
     * @return the index at which the element was found or -1 if the list does not contain the element
     */
    int indexOf(T element);

    /**
     * Gets the last position of an element in the list
     * @param element the element to look for (with equals())
     * @return the index at which the element was last found or -1 if the list does not contain the element
     */
    int lastIndexOf(T element);

    /**
     * @return the number of elements in the list
     */
    int size();

    /**
     * Changes the element at a given index to a new value
     * @param index the index of the old element
     * @param element the new value to place at that index
     * @return the old element value
     * @throws IndexOutOfBoundsException if index <= 0 || index >= size()
     */
    T set(int index, T element) throws IndexOutOfBoundsException;

    /**
     * Removes all the elements from the list
     */
    void clear();

    List<T> getContent();
    void setContent(List<T> content);
}
