import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class WordCounter {
    public static Map<String, Integer> countFromFiles(List<Path> files) throws IOException {
        return countFromFiles(files, null);
    }

    public static Map<String, Integer> countFromFiles(List<Path> files, Set<String> stopwords) throws IOException {
        HashMap<String, Integer> map = new HashMap<>();
        for (Path f : files) {
            try (BufferedReader br = Files.newBufferedReader(f)) {
                String line;
                while ((line = br.readLine()) != null) {
                    for (String w : TextProcessor.tokenize(line)) {
                        if (w.isEmpty()) continue;
                        if (stopwords != null && stopwords.contains(w)) continue;
                        map.merge(w, 1, Integer::sum);
                    }
                }
            }
        }
        return map;
    }

    public static Set<String> loadStopwords(Path path) throws IOException {
        Set<String> set = new HashSet<>();
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String s = line.trim().toLowerCase();
                if (!s.isEmpty()) set.add(s);
            }
        }
        return set;
    }

    public static List<Map.Entry<String, Integer>> topK(Map<String, Integer> counts, int k) {
        return counts.entrySet().stream()
                .sorted(Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue).reversed()
                        .thenComparing(Map.Entry::getKey))
                .limit(k)
                .collect(Collectors.toList());
    }
}
