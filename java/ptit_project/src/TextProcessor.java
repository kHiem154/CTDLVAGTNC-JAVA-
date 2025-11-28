import java.util.*;

public class TextProcessor {
    // Tokenize text into words: lower-case, split on non-letter characters
    public static List<String> tokenize(String text) {
        if (text == null) return Collections.emptyList();
        String[] parts = text.toLowerCase().split("\\P{L}+");
        List<String> words = new ArrayList<>();
        for (String p : parts) {
            if (!p.isEmpty()) words.add(p);
        }
        return words;
    }
}
