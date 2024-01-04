package files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Streams {
    /**
     * Read from an InputStream until a quote character (") is found, then read
     * until another quote character is found and return the bytes in between the
     * two quotes.
     * If no quote character was found return null, if only one, return the bytes
     * from the quote to the end of the stream.
     *
     * @param in
     * @return A list containing the bytes between the first occurrence of a quote
     *         character and the second.
     */
    public static List<Byte> getQuoted(InputStream in) throws IOException {
        List<Byte> res = new ArrayList<>();
        int currByte;

        while ((currByte = in.read()) != -1 && currByte != '\"') {
            // This way we are skipping all of the first chars until the first quote char
        }

        // If no quote char was found return null
        if (currByte == -1) {
            return null;
        }

        // Adding the chars to 'res' after the first quote char was founded,
        // until the second quote char or until the end of the stream
        while ((currByte = in.read()) != -1 && currByte != '\"') {
            res.add((byte) currByte);
        }

        // if only one quote char was found return the bytes from the quote to the end
        // of the stream
        if (currByte == -1 && res.isEmpty()) {
            return null;
        }

        return res;
    }

    /**
     * Read from the input until a specific string is read, return the string read
     * up to (not including) the endMark.
     *
     * @param in      the Reader to read from
     * @param endMark the string indicating to stop reading.
     * @return The string read up to (not including) the endMark (if the endMark is
     *         not found, return up to the end of the stream).
     */
    public static String readUntil(Reader in, String endMark) throws IOException {
        StringBuilder res = new StringBuilder();
        char[] buffer = new char[1024];// randomly chose 1024 (32*32=1024)
        int currChar;

        // Read the chars from the Reader until the end of the stream
        while ((currChar = in.read(buffer)) != -1) {
            res.append(buffer, 0, currChar); // Append the chars to the 'res' Stringbuilder

            // Check if the endMark is in the string
            int endIndex = res.indexOf(endMark);

            // If the end mark is found, return the substring up to the endMark (not
            // including)
            if (endIndex != -1) {
                return res.substring(0, endIndex);
            }
        }

        // If the endMark is not found, return up to the end of the stream
        return res.toString();
    }

    /**
     * Copy bytes from input to output, ignoring all occurrences of badByte.
     *
     * @param in
     * @param out
     * @param badByte
     */
    public static void filterOut(InputStream in, OutputStream out, byte badByte) throws IOException {
        // Using try-with-resources to automatically close input and output streams
        try (in; out) {
            int currByte;
            byte[] buffer = new byte[1024];

            // Read from the input stream and write to output stream while ignoring
            // occurrences of Badbyte
            while ((currByte = in.read(buffer)) != -1) {
                for (int i = 0; i < currByte; i++) {

                    // Check if the byte is not equal to badByte, if so write to the output stream
                    if (buffer[i] != badByte) {
                        out.write(buffer[i]);
                    }
                }

            }
        }
    }

    /**
     * Read a 40-bit (unsigned) integer from the stream and return it. The number is
     * represented as five bytes,
     * with the most-significant byte first.
     * If the stream ends before 5 bytes are read, return -1.
     *
     * @param in
     * @return the number read from the stream
     */
    public static long readNumber(InputStream in) throws IOException {
        long res = 0;

        // Read 5 bytes from the input stream
        for (int i = 0; i < 5; i++) {
            int byteVal = in.read();

            // If the stream ends before 5 bytes were read then return -1.
            if (byteVal == -1) {
                return -1;
            }

            // Constract the (5*8) 40-bit integer by shifting bits
            res = (res << 8) | (byteVal & 0xFF); // perform a bitwise AND operation, which clears all bits in 'byteVal'
                                                 // except the lower 8 bits.
        }
        return res;
    }
}
