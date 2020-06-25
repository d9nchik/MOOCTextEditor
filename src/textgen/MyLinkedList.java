package textgen;

import java.util.AbstractList;


/**
 * A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;
    LLNode<E> tail;
    int size;

    /**
     * Create a new empty LinkedList
     */
    public MyLinkedList() {
        size = 0;
        head = new LLNode<>(null);
        tail = new LLNode<>(null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Appends an element to the end of the list
     *
     * @param element The element to add
     */
    public boolean add(E element) {
        add(size, element);
        return true;
    }

    /**
     * Get the element at position index
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index");
        LLNode<E> current = getNode(index);
        return current.data;
    }

    /**
     * Add an element to the list at the specified index
     *
     * @param element The element to add
     * @param index   where the element should be added
     */
    public void add(int index, E element) {
        if (element == null)
            throw new NullPointerException("Null element is not accepted");
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Invalid index");

        LLNode<E> current = index == size ? tail : getNode(index);

        LLNode<E> inserted = new LLNode<>(element);
        inserted.next = current;
        inserted.prev = current.prev;
        inserted.prev.next = inserted;
        current.prev = inserted;

        ++size;
    }

    private LLNode<E> getNode(int index) {
        LLNode<E> current;
        if (index <= size / 2) {
            current = head.next;
            for (int i = 0; i < index; i++)
                current = current.next;
        } else {
            current = tail.prev;
            for (int i = size - 1; i > index; i--)
                current = current.prev;
        }
        return current;
    }


    /**
     * Return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Remove a node at the specified index and return its data element.
     *
     * @param index The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException If index is outside the bounds of the list
     */
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index");
        LLNode<E> current = getNode(index);
        current.prev.next = current.next;
        current.next.prev = current.prev;
        --size;
        return current.data;
    }

    /**
     * Set an index position in the list to a new element
     *
     * @param index   The index of the element to change
     * @param element The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E set(int index, E element) {
        if (element == null)
            throw new NullPointerException("Null element is not accepted");
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index");
        LLNode<E> current = getNode(index);

        E previous = current.data;
        current.data = element;

        return previous;
    }
}

class LLNode<E> {
    LLNode<E> prev;
    LLNode<E> next;
    E data;

    // TODO: Add any other methods you think are useful here
    // E.g. you might want to add another constructor

    public LLNode(E e) {
        this.data = e;
        this.prev = null;
        this.next = null;
    }

}
