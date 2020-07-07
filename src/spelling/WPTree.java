package spelling;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * WPTree implements WordPath by dynamically creating a tree of words during a Breadth First
 * Search of Nearby words to create a path between two words.
 *
 * @author d9nich
 */
public class WPTree implements WordPath {

    // used to search for nearby Words
    private final NearbyWords nw;
    // this is the root node of the WPTree
    private WPTreeNode root;

    // This constructor is used by the Text Editor Application
    // You'll need to create your own NearbyWords object here.
    public WPTree() {
        this.root = null;
        Dictionary d = new DictionaryHashSet();
        DictionaryLoader.loadDictionary(d, "data/dict.txt");
        this.nw = new NearbyWords(d);
    }

    //This constructor will be used by the grader code
    public WPTree(NearbyWords nw) {
        this.root = null;
        this.nw = nw;
    }

    // see method description in WordPath interface
    public List<String> findPath(String word1, String word2) {
        root = new WPTreeNode(word1);

        // initial variables
        final Queue<WPTreeNode> queue = new LinkedList<>();     // String to explore
        HashSet<String> visited = new HashSet<>();   // to avoid exploring the same
        visited.add(word1);
        queue.offer(root);
        while (!queue.isEmpty()) {
            WPTreeNode current = queue.remove();
            for (String word : nw.distanceOne(current.getWord(), true)) {
                if (!visited.contains(word)) {
                    if (word.equals(word2))
                        return current.addChild(word).buildPathToRoot();
                    visited.add(word);
                    queue.add(current.addChild(word));
                }
            }
        }

        return null;
    }

    // Method to print a list of WPTreeNodes (useful for debugging)
    private String printQueue(List<WPTreeNode> list) {
        StringBuilder ret = new StringBuilder("[ ");

        for (WPTreeNode w : list) {
            ret.append(w.getWord()).append(", ");
        }
        ret.append("]");
        return ret.toString();
    }

}

/* Tree Node in a WordPath Tree. This is a standard tree with each
 * node having any number of possible children.  Each node should only
 * contain a word in the dictionary and the relationship between nodes is
 * that a child is one character mutation (deletion, insertion, or
 * substitution) away from its parent
 */
class WPTreeNode {

    private final String word;
    private final List<WPTreeNode> children = new LinkedList<>();
    private final WPTreeNode parent;

    /**
     * Construct a node with the word w and the parent p
     * (pass a null parent to construct the root)
     *
     * @param w The new node's word
     * @param p The new node's parent
     */
    public WPTreeNode(String w, WPTreeNode p) {
        this.word = w;
        this.parent = p;
    }

    public WPTreeNode(String word) {
        this(word, null);
    }

    /**
     * Add a child of a node containing the String s
     * precondition: The word is not already a child of this node
     *
     * @param s The child node's word
     * @return The new WPTreeNode
     */
    public WPTreeNode addChild(String s) {
        WPTreeNode child = new WPTreeNode(s, this);
        this.children.add(child);
        return child;
    }

    /**
     * Get the list of children of the calling object
     * (pass a null parent to construct the root)
     *
     * @return List of WPTreeNode children
     */
    public List<WPTreeNode> getChildren() {
        return this.children;
    }

    /**
     * Allows you to build a path from the root node to
     * the calling object
     *
     * @return The list of strings starting at the root and
     * ending at the calling object
     */
    public List<String> buildPathToRoot() {
        WPTreeNode curr = this;
        List<String> path = new LinkedList<>();
        while (curr != null) {
            path.add(0, curr.getWord());
            curr = curr.parent;
        }
        return path;
    }

    /**
     * Get the word for the calling object
     *
     * @return Getter for calling object's word
     */
    public String getWord() {
        return this.word;
    }

    /**
     * toString method
     *
     * @return The string representation of a WPTreeNode
     */
    public String toString() {
        StringBuilder ret = new StringBuilder("Word: " + word + ", parent = ");
        if (this.parent == null)
            ret.append("null.\n");
        else
            ret.append(this.parent.getWord()).append("\n");
        ret.append("[ ");

        for (WPTreeNode curr : children)
            ret.append(curr.getWord()).append(", ");
        ret.append(" ]\n");
        return ret.toString();
    }

}

