import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedDemo {
    public static void main(String[] args) throws IOException {
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader();

        writer.connect(reader);

        Thread printThread = new Thread(new Print(reader), "PrintThread");
        printThread.start();
        int receive = 0;

        try {
            while ((receive = System.in.read()) != -1) {
                writer.write(receive);
            }
        } finally {
            writer.close();
        }
    }

    static class Print implements Runnable {
        private PipedReader reader;

        public Print(PipedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = reader.read()) != -1) {
                    System.out.println((char) receive);
                }
            } catch (IOException exception) {

            }
        }
    }
}
