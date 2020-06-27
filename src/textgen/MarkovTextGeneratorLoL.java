package textgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * An implementation of the MTG interface that uses a list of lists.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

    // The random number generator
    private final Random rnGenerator;
    // The list of words with their next words
    private HashMap<String, ListNode> wordList;
    // The starting "word"
    private String starter;

    public MarkovTextGeneratorLoL(Random generator) {
        wordList = new HashMap<>();
        starter = "";
        rnGenerator = generator;
    }

    /**
     * This is a minimal set of tests.  Note that it can be difficult
     * to test methods/classes with randomized behavior.
     *
     * @param args - not used
     */
    public static void main(String[] args) {
        // feed the generator a fixed random value for repeatable behavior
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        System.out.println(textString);
        gen.train(textString);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
        String textString2 = "You say yes, I say no, " +
                "You say stop, and I say go, go, go, " +
                "Oh no. You say goodbye and I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "I say high, you say low, " +
                "You say why, and I say I don't know. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "Why, why, why, why, why, why, " +
                "Do you say goodbye. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "You say yes, I say no, " +
                "You say stop and I say go, go, go. " +
                "Oh, oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello,";
        System.out.println(textString2);
        gen.retrain(textString2);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
    }

    /**
     * Train the generator by adding the sourceText
     */
    @Override
    public void train(String sourceText) {
        if (sourceText.equals(""))
            return;
        String[] words = sourceText.split(" +");
        if (starter.equals(""))
            starter = words[0];
        String prevWord = words[words.length - 1];
        for (String word : words) {
            if (wordList.containsKey(prevWord))
                wordList.get(prevWord).addNextWord(word);
            else {
                final ListNode value = new ListNode();
                value.addNextWord(word);
                wordList.put(prevWord, value);
            }
            prevWord = word;
        }

    }

    /**
     * Generate the number of words requested.
     */
    @Override
    public String generateText(int numWords) {
        if (numWords == 0 || starter.equals(""))
            return "";
        StringBuilder stringBuilder = new StringBuilder(starter);
        String currentWord = starter;
        for (int i = 0; i < numWords - 1; i++) {
            currentWord = wordList.get(currentWord).getRandomNextWord(rnGenerator);
            stringBuilder.append(" ").append(currentWord);
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MarkovTextGeneratorLoL{");
        sb.append("wordList=");
        wordList.forEach((k, v) -> sb.append(k).append(":").append(v));
        sb.append('}');
        return sb.toString();
    }

    /**
     * Retrain the generator from scratch on the source text
     */
    @Override
    public void retrain(String sourceText) {
        starter = "";
        wordList = new HashMap<>();
        train(sourceText);
    }

}

/**
 * Links a word to the next words in the list
 * You should use this class in your implementation.
 */
class ListNode {

    // The next words that could follow it
    private final ArrayList<String> nextWords = new ArrayList<>();

    public void addNextWord(String nextWord) {
        nextWords.add(nextWord);
    }

    public String getRandomNextWord(Random generator) {
        return nextWords.get(generator.nextInt(nextWords.size()));
    }

    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        for (String s : nextWords) {
            toReturn.append(s).append("->");
        }
        toReturn.append("\n");
        return toReturn.toString();
    }
}


