package files;

import java.io.BufferedReader;
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
        // Stringbuiler to store the chars read
        StringBuilder res = new StringBuilder();
        // BufferedReader for efficiant reading
        BufferedReader buffer = new BufferedReader(in);
        int currChar;

        // Read the chars until the end of the stream
        while ((currChar = buffer.read()) != -1) {
            res.append((char) currChar); // Append the chars to the 'res' Stringbuilder

            // Check if the endMark is in the recently read chars
            if (res.toString().endsWith(endMark)) {
                return res.substring(0, res.length() - endMark.length()); // Remove the endMark from res
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
        int currByte;
        // Read from the input stream until the end of the stream
        while ((currByte = in.read()) != -1) {
            // Check if the byte is not equal to badByte, if so write to the output stream
            if ((byte) currByte != badByte) {
                out.write(currByte);
            }
            // Else, ignore thr currByte and don't write it to the output
        }
        out.close();
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
