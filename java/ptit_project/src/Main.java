import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java -cp out Main [-k=N] <file-or-directory> [more files or dirs]");
            return;
        }

        List<Path> files = new ArrayList<>();
        int topK = 20;

        for (String a : args) {
            if (a.startsWith("-k=")) {
                try {
                    topK = Integer.parseInt(a.substring(3));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid -k value, using default 20");
                }
                continue;
            }

            Path p = Paths.get(a);
            if (Files.isDirectory(p)) {
                try (DirectoryStream<Path> ds = Files.newDirectoryStream(p, "*.txt")) {
                    for (Path f : ds) files.add(f);
                }
            } else if (Files.isRegularFile(p)) {
                files.add(p);
            } else {
                System.err.println("Not found: " + a);
            }
        }

        if (files.isEmpty()) {
            System.err.println("No .txt files found. Provide files or a directory containing .txt files.");
            return;
        }

        // Check for -s (use stopwords) or -s=path
        Set<String> stopwords = null;
        for (String a : args) {
            if (a.equals("-s")) {
                // use bundled stopwords file
                try {
                    stopwords = WordCounter.loadStopwords(Paths.get("ptit_project/stopwords_vn.txt"));
                } catch (Exception e) {
                    System.err.println("Failed to load bundled stopwords: " + e.getMessage());
                }
            } else if (a.startsWith("-s=")) {
                String path = a.substring(3);
                try {
                    stopwords = WordCounter.loadStopwords(Paths.get(path));
                } catch (Exception e) {
                    System.err.println("Failed to load stopwords from " + path + ": " + e.getMessage());
                }
            }
        }

        Map<String, Integer> counts = stopwords == null ? WordCounter.countFromFiles(files)
                : WordCounter.countFromFiles(files, stopwords);
        System.out.println("Total unique words: " + counts.size());

        System.out.println("\nTop " + topK + " words:");
        List<Map.Entry<String, Integer>> top = WordCounter.topK(counts, topK);
        for (Map.Entry<String, Integer> e : top) {
            System.out.printf("%-20s %d%n", e.getKey(), e.getValue());
        }

        System.out.println("\nWords sorted alphabetically (TreeMap) — first 50 shown:");
        TreeMap<String, Integer> tree = new TreeMap<>(counts);
        int i = 0;
        for (Map.Entry<String, Integer> e : tree.entrySet()) {
            System.out.printf("%-20s %d%n", e.getKey(), e.getValue());
            if (++i >= 50) {
                System.out.println("...");
                break;
            }
        }
    }
}
