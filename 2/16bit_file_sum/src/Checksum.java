import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Checksum {
    public static FileChannel getFileChannel(String fileName) throws FileNotFoundException {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            System.out.println(e.getCause());
        }
        return inputStream.getChannel();
    }

    public static int getFileChecksum(FileChannel file) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(5);
        int checksum = 0;
        var readResult = file.read(buff);
        while (file.read(buff) != -1) {
            buff.flip();
            while (buff.hasRemaining()) {
                var tmp = buff.get();
                checksum ^= tmp;
            }
            buff.clear();
        }
        return checksum;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(
                getFileChecksum(
                    getFileChannel("test.txt")
            )
        );
    }
}