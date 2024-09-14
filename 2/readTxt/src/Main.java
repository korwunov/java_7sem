public class Main {
    public static void main(String[] args) {
        File fileService = new File("test.txt");
        System.out.println(fileService.readFile());
        fileService.writeToFile();
        System.out.println(fileService.readFile());
    }
}