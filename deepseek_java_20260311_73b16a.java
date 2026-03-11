import java.io.*;
import java.util.*;

public class Anagrams {
    
    public static void main(String[] args) {
        // Dictionary/HashMap to store signatures and their word lists
        Map<String, List<String>> anagramMap = new HashMap<>();
        
        try {
            // Step 1: Read the file
            BufferedReader reader = new BufferedReader(new FileReader("ulysses.text"));
            String line;
            
            // Step 2: Process each line
            while ((line = reader.readLine()) != null) {
                // Split line into words
                String[] words = line.split("\\s+");
                
                for (String word : words) {
                    // Step 3: Clean the word
                    String cleanedWord = cleanWord(word);
                    
                    if (!cleanedWord.isEmpty()) {
                        // Step 4: Create signature
                        String signature = alphabetize(cleanedWord);
                        
                        // Step 5: Store in HashMap
                        if (!anagramMap.containsKey(signature)) {
                            List<String> wordList = new ArrayList<>();
                            wordList.add(cleanedWord);
                            anagramMap.put(signature, wordList);
                        } else {
                            anagramMap.get(signature).add(cleanedWord);
                        }
                    }
                }
            }
            reader.close();
            
            // Step 6: Generate output
            generateOutput(anagramMap);
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    
    /**
     * Clean a word by removing punctuation but keeping apostrophes
     */
    private static String cleanWord(String word) {
        // Remove punctuation (keep letters, apostrophes, and hyphens if desired)
        String cleaned = word.replaceAll("[^a-zA-Z']", "").toLowerCase();
        return cleaned;
    }
    
    /**
     * Create signature by sorting letters alphabetically
     */
    private static String alphabetize(String word) {
        char[] letters = word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
    
    /**
     * Generate theAnagrams.tex file and print anagram lists
     */
    private static void generateOutput(Map<String, List<String>> anagramMap) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("theAnagrams.tex"));
            
            // LaTeX header
            writer.println("\\documentclass{article}");
            writer.println("\\begin{document}");
            writer.println("\\section*{Anagrams in Ulysses}");
            writer.println("\\begin{description}");
            
            // Sort signatures for consistent output
            List<String> signatures = new ArrayList<>(anagramMap.keySet());
            Collections.sort(signatures);
            
            for (String signature : signatures) {
                List<String> words = anagramMap.get(signature);
                
                // Only print if there's at least 2 words (actual anagrams)
                if (words.size() > 1) {
                    Collections.sort(words); // Sort words alphabetically
                    
                    // Print to console
                    System.out.println(signature + ": " + words);
                    
                    // Write to LaTeX file
                    writer.println("\\item[" + signature + "] " + 
                                 String.join(", ", words));
                }
            }
            
            // LaTeX footer
            writer.println("\\end{description}");
            writer.println("\\end{document}");
            writer.close();
            
            System.out.println("Output written to theAnagrams.tex");
            
        } catch (IOException e) {
            System.err.println("Error writing output: " + e.getMessage());
        }
    }
}