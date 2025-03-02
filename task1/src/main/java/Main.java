public class Main {
    public static void main(String[] args) {
        String inFilePath;
        if (args.length < 1) {
            System.out.println("Warning: no file path provided; Default file used");
            inFilePath = "data.txt";
        } else {
            inFilePath = args[0];
        }

        WordCounter wordCounter = new WordCounter();
        wordCounter.readData(inFilePath);
        wordCounter.writeData("out.csv");
    }
}