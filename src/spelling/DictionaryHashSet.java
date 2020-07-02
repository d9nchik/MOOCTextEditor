package spelling;

import java.util.HashSet;


/**
 * A class that implements the Dictionary interface with a HashSet
 */
public class DictionaryHashSet implements Dictionary {

    private final HashSet<String> words;

    public DictionaryHashSet() {
        words = new HashSet<>();
    }

    /**
     * Add this word to the dictionary.
     *
     * @param word The word to add
     * @return true if the word was added to the dictionary
     * (it wasn't already there).
     */
    @Override
    public boolean addWord(String word) {
        if (isWord(word))
            return false;
        if (isWord((char) ('A' - 'a' + word.charAt(0)) + word.substring(1)))
            words.remove((char) ('A' - 'a' + word.charAt(0)) + word.substring(1));

        String toLowerCase = word.toLowerCase();
        if ('A' <= word.charAt(0) && word.charAt(0) <= 'Z')
            toLowerCase = word.charAt(0) + toLowerCase.substring(1);
        return words.add(toLowerCase);
    }

    /**
     * Return the number of words in the dictionary
     */
    @Override
    public int size() {
        return words.size();
    }

    /**
     * Is this a word according to this dictionary?
     */
    @Override
    public boolean isWord(String s) {
        String toLowerCase = s.toLowerCase();
        if (words.contains(toLowerCase))
            return true;
        if (s.length() != 0 && 'A' <= s.charAt(0) && s.charAt(0) <= 'Z')
            return words.contains(s.charAt(0) + toLowerCase.substring(1));
        return false;
    }
}
