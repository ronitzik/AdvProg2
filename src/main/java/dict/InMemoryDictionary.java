package dict;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implements a persistent dictionary that can be held entirely in memory.
 * When flushed, it writes the entire dictionary back to a file.
 * <p>
 * The file format has one keyword per line:
 * 
 * <pre>
 * word:def
 * </pre>
 * <p>
 * Note that an empty definition list is allowed (in which case the entry would
 * have the form:
 * 
 * <pre>
 * word:
 * </pre>
 *
 * @author talm
 */
public class InMemoryDictionary extends TreeMap<String, String> implements PersistentDictionary {
    private static final long serialVersionUID = 1L; // (because we're extending a serializable class)
    private final File dictFile;

    /**
     * // Constructs an InMemortDictionary with the specified file as its persistent
     * store file
     *
     * @param dictFile the file to be used as the persistenr store for the
     *                 dictionary.
     */
    public InMemoryDictionary(File dictFile) {
        super();
        this.dictFile = dictFile;
    }

    /**
     * opens and loads the dictionary from the persistent store file.
     * Discards any exsiting data in memory.
     * 
     * @throws IOException if an I/O error occurs while reading the file
     */
    @Override
    public void open() throws IOException {
        clear(); // Discard any existing data in memory

        if (dictFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(dictFile))) {
                String line;

                // Read each line from the file
                while ((line = reader.readLine()) != null) {
                    // Check if the line is not emptry or only whitespaces
                    if (!line.trim().isEmpty()) {
                        // Split the current line into two parts using ":" as the delimeter
                        String[] parts = line.split(":", 2);
                        String word = parts[0]; // assign the first part to the word
                        String def;
                        if (parts.length > 1) {
                            def = parts[1]; // assign the second part to the def
                        } else {
                            def = "";
                        }
                        put(word, def); // add the matching word and def to the dictionary

                    }
                }
            }
        }

    }

    /**
     * Flushes the contents of the dictionary to the persistent store file and
     * closes it.
     * 
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    @Override
    public void close() throws IOException {
        try (FileWriter writer = new FileWriter(dictFile)) {
            // Get an iterator for the entry set
            Iterator<Map.Entry<String, String>> iterator = entrySet().iterator();

            // Iterate through the entries
            for (int i = 0; i < size(); i++) {
                // get the curr entry
                Map.Entry<String, String> entry = iterator.next();
                // Writing the key-val pair to the file
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        }

    }
}
