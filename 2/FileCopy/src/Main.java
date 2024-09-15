import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CopyService cs = new CopyService();
        cs.copyFilesByStreams();
        cs.deleteCopiedFile();
        cs.copyFilesByChannels();
        cs.deleteCopiedFile();
        cs.copyFileByApacheIo();
        cs.deleteCopiedFile();
        cs.copyFileByFiles();
        cs.deleteCopiedFile();
    }
}