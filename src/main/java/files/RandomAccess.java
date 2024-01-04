package files;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccess {
    /**
     * Treat the file as an array of (unsigned) 8-bit values and sort them
     * in-place using a bubble-sort algorithm.
     * You may not read the whole file into memory!
     *
     * @param file
     */
    public static void sortBytes(RandomAccessFile file) throws IOException {
        long length = file.length();

        // Iterate through the entire file
        for (long i = 0; i < length - 1; i++) {
            // Inner loop for each pass
            for (long j = 0; j < length - i - 1; j++) {
                // Read two bytes at positions j and j+1
                file.seek(j);
                int byte1 = file.readUnsignedByte();

                file.seek(j + 1);
                int byte2 = file.readUnsignedByte();

                // Swap if the curr byte is greater than the next byte
                if (byte1 > byte2) {
                    file.seek(j);
                    file.writeByte(byte2);

                    file.seek(j + 1);
                    file.writeByte(byte1);
                }
            }
        }
    }

    /**
     * Treat the file as an array of unsigned 24-bit values (stored MSB first) and
     * sort
     * them in-place using a bubble-sort algorithm.
     * You may not read the whole file into memory!
     *
     * @param file
     * @throws IOException
     */
    public static void sortTriBytes(RandomAccessFile file) throws IOException {
        long length = file.length();

        // Iterate through hte entire file
        for (long i = 0; i < length - 3; i += 3) {
            // Inner loop for each pass
            for (long j = 0; j < length - i - 3; j += 3) {
                // Read three bytes at positions j, j+1 and j+2
                file.seek(j);
                int triByte1 = readTriBytes(file);

                file.seek(j + 3);
                int triByte2 = readTriBytes(file);

                // swap if the curr triByte is greater than the next triByte
                if (triByte1 > triByte2) {
                    file.seek(j);
                    writeTriBytes(file, triByte2);

                    file.seek(j + 3);
                    writeTriBytes(file, triByte1);
                }

            }
        }
    }

    // Helper method to read a 24-bit value from the file (MSB first)
    private static int readTriBytes(RandomAccessFile file) throws IOException {
        int byte1 = file.readUnsignedByte();
        int byte2 = file.readUnsignedByte();
        int byte3 = file.readUnsignedByte();

        return (byte1 << 16) | (byte2 << 8) | byte3;
    }

    // Helper method to write a 24-bit value from the file (MSB first)
    private static void writeTriBytes(RandomAccessFile file, int triByte) throws IOException {
        file.write((triByte >> 16) & 0xFF);
        file.write((triByte >> 8) & 0xFF);
        file.write(triByte & 0xFF);
    }
}
