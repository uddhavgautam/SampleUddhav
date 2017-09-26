import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileFlushCheck {
    public static void main(String[] args) throws InterruptedException {
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File("C:\\Users\\Administrator\\IdeaProjects\\untitled\\File1.txt");
                BufferedReader br = null;
                FileReader fr = null;

                try {
                    fr = new FileReader(file);
                    br = new BufferedReader(fr);

                    String sCurrentLine;

                    while ((sCurrentLine = br.readLine()) != null) {
                        System.out.println(sCurrentLine);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (br != null)
                            br.close();

                        if (fr != null)
                            fr.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        Thread.sleep(3000);
        thread2.start();
    }
}
