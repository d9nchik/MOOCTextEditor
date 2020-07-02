package spelling;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 *
 * @author You
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

    private final TrieNode root = new TrieNode();
    private int size;

    private static LinkedList<String> breadthFirstTraversal(TrieNode node, int size) {
        int counter = 0;
        LinkedList<String> words = new LinkedList<>();
        final Queue<TrieNode> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty() && counter < size) {
            TrieNode current = queue.poll();
            if (current.isEndsWord()) {
                ++counter;
                words.add(current.getText());
            }

            for (char character : current.getValidNextCharacters())
                queue.offer(current.getChild(character));
        }

        return words;
    }

    /**
     * Insert a word into the trie.
     * For the basic part of the assignment (part 2), you should convert the
     * string to all lower case before you insert it.
     * <p>
     * This method adds a word by creating and linking the necessary trie nodes
     * into the trie, as described outlined in the videos for this week. It
     * should appropriately use existing nodes in the trie, only creating new
     * nodes when necessary. E.g. If the word "no" is already in the trie,
     * then adding the word "now" would add only one additional node
     * (for the 'w').
     *
     * @return true if the word was successfully added or false if it already exists
     * in the dictionary.
     */
    public boolean addWord(String word) {
        if (isWord(word))
            return false;
        if (isWord((char) ('A' - 'a' + word.charAt(0)) + word.substring(1)))
            deleteWord((char) ('A' - 'a' + word.charAt(0)) + word.substring(1));

        String lowercaseWord = word.toLowerCase();
        TrieNode current = root.getValidNextCharacters().contains(word.charAt(0))
                ? root.getChild(word.charAt(0)) : root.insert(word.charAt(0));
        for (int i = 1; i < lowercaseWord.length(); i++) {
            char symbol = lowercaseWord.charAt(i);
            if (current.getValidNextCharacters().contains(symbol))
                current = current.getChild(symbol);
            else
                current = current.insert(symbol);
        }

        current.setEndsWord();

        ++size;
        return true;
    }

    /**
     * Return the number of words in the dictionary.  This is NOT necessarily the same
     * as the number of TrieNodes in the trie.
     */
    public int size() {
        return size;
    }

    /**
     * Returns whether the string is a word in the trie, using the algorithm
     * described in the videos for this week.
     */
    @Override
    public boolean isWord(String word) {
        String lowercaseWord = word.toLowerCase();
        if (containsWord(lowercaseWord))
            return true;
        if (word.length() != 0 && 'A' <= word.charAt(0) && word.charAt(0) <= 'Z') {
            lowercaseWord = word.charAt(0) + lowercaseWord.substring(1);
            return containsWord(lowercaseWord);
        }
        return false;
    }

    private boolean containsWord(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char symbol = word.charAt(i);
            if (current.getValidNextCharacters().contains(symbol))
                current = current.getChild(symbol);
            else
                return false;
        }

        return current.isEndsWord();
    }

    private void deleteWord(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char symbol = word.charAt(i);
            if (current.getValidNextCharacters().contains(symbol))
                current = current.getChild(symbol);
            else
                return;
        }
        current.resetEndsWord();
        --size;
    }

    /**
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions
     * of the prefix string. All legal completions must be valid words in the
     * dictionary. If the prefix itself is a valid word, it is included
     * in the list of returned words.
     * <p>
     * The list of completions must contain
     * all of the shortest completions, but when there are ties, it may break
     * them in any order. For example, if there the prefix string is "ste" and
     * only the words "step", "stem", "stew", "steer" and "steep" are in the
     * dictionary, when the user asks for 4 completions, the list must include
     * "step", "stem" and "stew", but may include either the word
     * "steer" or "steep".
     * <p>
     * If this string prefix is not in the trie, it returns an empty list.
     *
     * @param prefix         The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */
    @Override
    public List<String> predictCompletions(String prefix, int numCompletions) {
        String lowercaseWord = prefix.toLowerCase();
        TrieNode current = root;
        for (int i = 0; i < lowercaseWord.length(); i++) {
            char symbol = lowercaseWord.charAt(i);
            if (current.getValidNextCharacters().contains(symbol))
                current = current.getChild(symbol);
            else
                break;
        }

        LinkedList<String> strings = (current.getText().equals(lowercaseWord)) ?
                breadthFirstTraversal(current, numCompletions) : new LinkedList<>();

        if (strings.size() < numCompletions && 'A' <= prefix.charAt(0) && prefix.charAt(0) <= 'Z') {
            lowercaseWord = prefix.charAt(0) + lowercaseWord.substring(1);
            current = root;
            for (int i = 0; i < lowercaseWord.length(); i++) {
                char symbol = lowercaseWord.charAt(i);
                if (current.getValidNextCharacters().contains(symbol))
                    current = current.getChild(symbol);
                else
                    return strings;
            }
            strings.addAll(breadthFirstTraversal(current, numCompletions - strings.size()));
        }

        return strings;
    }

    // For debugging
    public void printTree() {
        printNode(root);
    }

    /**
     * Do a pre-order traversal from this node down
     */
    public void printNode(TrieNode curr) {
        if (curr == null)
            return;

        System.out.println(curr.getText());

        TrieNode next;
        for (Character c : curr.getValidNextCharacters()) {
            next = curr.getChild(c);
            printNode(next);
        }
    }


}