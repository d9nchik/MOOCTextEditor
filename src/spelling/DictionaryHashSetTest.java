package spelling;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryHashSetTest {
    DictionaryHashSet emptyDict;
    DictionaryHashSet smallDict;
    DictionaryHashSet largeDict;

    @Before
    public void setUp() {
        emptyDict = new DictionaryHashSet();
        smallDict = new DictionaryHashSet();
        largeDict = new DictionaryHashSet();

        smallDict.addWord("Hello");
        smallDict.addWord("HElLo");
        smallDict.addWord("help");
        smallDict.addWord("he");
        smallDict.addWord("hem");
        smallDict.addWord("hot");
        smallDict.addWord("hey");
        smallDict.addWord("a");
        smallDict.addWord("subsequent");

        String dictFile = "data/words.small.txt";
        DictionaryLoader.loadDictionary(largeDict, dictFile);
    }


    /**
     * Test if the size method is working correctly.
     */
    @Test
    public void testSize() {
        assertEquals("Testing size for empty dict", 0, emptyDict.size());
        assertEquals("Testing size for small dict", 8, smallDict.size());
        assertEquals("Testing size for large dict", 4438, largeDict.size());
    }

    /**
     * Test the isWord method
     */
    @Test
    public void testIsWord() {
        assertFalse("Testing isWord on empty: Hello", emptyDict.isWord("Hello"));
        assertTrue("Testing isWord on small: Hello", smallDict.isWord("Hello"));
        assertTrue("Testing isWord on large: Hello", largeDict.isWord("Hello"));

        assertFalse("Testing isWord on small: hello", smallDict.isWord("hello"));
        assertTrue("Testing isWord on large: hello", largeDict.isWord("hello"));

        assertFalse("Testing isWord on small: hellow", smallDict.isWord("hellow"));
        assertFalse("Testing isWord on large: hellow", largeDict.isWord("hellow"));

        commonTests();


    }

    /**
     * Test the addWord method
     */
    @Test
    public void testAddWord() {


        assertFalse("Asserting hellow is not in empty dict", emptyDict.isWord("hellow"));
        assertFalse("Asserting hellow is not in small dict", smallDict.isWord("hellow"));
        assertFalse("Asserting hellow is not in large dict", largeDict.isWord("hellow"));

        emptyDict.addWord("hellow");
        smallDict.addWord("hellow");
        largeDict.addWord("hellow");

        assertTrue("Asserting hellow is in empty dict", emptyDict.isWord("hellow"));
        assertTrue("Asserting hellow is in small dict", smallDict.isWord("hellow"));
        assertTrue("Asserting hellow is in large dict", largeDict.isWord("hellow"));

        assertFalse("Asserting xyzabc is not in empty dict", emptyDict.isWord("xyzabc"));
        assertFalse("Asserting xyzabc is not in small dict", smallDict.isWord("xyzabc"));
        assertFalse("Asserting xyzabc is in large dict", largeDict.isWord("xyzabc"));


        emptyDict.addWord("XYZAbC");
        smallDict.addWord("XYZAbC");
        largeDict.addWord("XYZAbC");

        assertFalse("Asserting xyzabc is in empty dict", emptyDict.isWord("xyzabc"));
        assertFalse("Asserting xyzabc is in small dict", smallDict.isWord("xyzabc"));
        assertFalse("Asserting xyzabc is large dict", largeDict.isWord("xyzabc"));


        commonTests();

        largeDict.addWord("Danylo");
        assertFalse(largeDict.isWord("danylo"));
        assertTrue(largeDict.isWord("Danylo"));


    }

    private void commonTests() {
        assertFalse("Testing isWord on empty: empty string", emptyDict.isWord(""));
        assertFalse("Testing isWord on small: empty string", smallDict.isWord(""));
        assertFalse("Testing isWord on large: empty string", largeDict.isWord(""));

        assertFalse("Testing isWord on small: no", smallDict.isWord("no"));
        assertTrue("Testing isWord on large: no", largeDict.isWord("no"));

        assertTrue("Testing isWord on small: subsequent", smallDict.isWord("subsequent"));
        assertTrue("Testing isWord on large: subsequent", largeDict.isWord("subsequent"));
    }
}