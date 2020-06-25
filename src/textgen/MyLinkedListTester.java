package textgen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author UC San Diego MOOC team
 */
public class MyLinkedListTester {

    private static final int LONG_LIST_LENGTH = 10;

    MyLinkedList<String> shortList;
    MyLinkedList<Integer> emptyList;
    MyLinkedList<Integer> longerList;
    MyLinkedList<Integer> list1;

    /**
     *
     */
    @Before
    public void setUp() {
        // Feel free to use these lists, or add your own
        shortList = new MyLinkedList<>();
        shortList.add("A");
        shortList.add("B");
        emptyList = new MyLinkedList<>();
        longerList = new MyLinkedList<>();
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            longerList.add(i);
        }
        list1 = new MyLinkedList<>();
        list1.add(65);
        list1.add(21);
        list1.add(42);

    }


    /**
     * Test if the get method is working correctly.
     */
    /*You should not need to add much to this method.
     * We provide it as an example of a thorough test. */
    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored ")
    public void testGet() {
        //test empty list, get should throw an exception
        try {
            emptyList.get(0);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException ignored) {

        }

        // test short list, first contents, then out of bounds
        assertEquals("Check first", "A", shortList.get(0));
        assertEquals("Check second", "B", shortList.get(1));

        try {
            shortList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException ignored) {

        }
        try {
            shortList.get(2);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException ignored) {

        }
        // test longer list contents
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            assertEquals("Check " + i + " element", (Integer) i, longerList.get(i));
        }

        // test off the end of the longer array
        try {
            longerList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException ignored) {

        }
        try {
            longerList.get(LONG_LIST_LENGTH);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException ignored) {
        }

    }


    /**
     * Test removing an element from the list.
     * We've included the example from the concept challenge.
     * You will want to add more tests.
     */
    @Test
    public void testRemove() {
        int a = list1.remove(0);
        assertEquals("Remove: check a is correct ", 65, a);
        assertEquals("Remove: check element 0 is correct ", (Integer) 21, list1.get(0));
        assertEquals("Remove: check size is correct ", 2, list1.size());

        // TODO: Add more tests here
    }

    /**
     * Test adding an element into the end of the list, specifically
     * public boolean add(E element)
     */
    @Test
    public void testAddEnd() {
        emptyList.add(1);
        assertEquals((Integer) 1, emptyList.get(0));
        assertEquals(1, emptyList.size());
        emptyList.add(2);
        assertEquals((Integer) 2, emptyList.get(1));
        assertEquals(2, emptyList.size());
        emptyList.add(3);
        assertEquals((Integer) 3, emptyList.get(2));
        assertEquals(3, emptyList.size());
        try {
            emptyList.add(null);
            fail("Check Null Pointer");
        } catch (NullPointerException ignored) {

        }
    }


    /**
     * Test the size of the list
     */
    @Test
    public void testSize() {
        // TODO: implement this test
    }


    /**
     * Test adding an element into the list at a specified index,
     * specifically:
     * public void add(int index, E element)
     */
    @Test
    public void testAddAtIndex() {
        longerList.add(5, 10);
        assertEquals((Integer) 10, longerList.get(5));
        assertEquals(LONG_LIST_LENGTH + 1, longerList.size);
        longerList.add(0, 100);
        assertEquals((Integer) 100, longerList.get(0));
        assertEquals(LONG_LIST_LENGTH + 2, longerList.size);
        try {
            emptyList.add(20, null);
            fail("Check Null Pointer");
        } catch (NullPointerException ignored) {

        }
        try {
            emptyList.add(-1, 20);
            fail("Check Index out of bounds");
        } catch (IndexOutOfBoundsException ignored) {

        }
        try {
            emptyList.add(100, 20);
            fail("Check Index out of bounds");
        } catch (IndexOutOfBoundsException ignored) {

        }
    }

    /**
     * Test setting an element in the list
     */
    @Test
    public void testSet() {
        // TODO: implement this test

    }


    // TODO: Optionally add more test methods.

}
