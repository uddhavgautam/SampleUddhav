import java.io.*;

public class FileWriteHere {

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File("C:\\Users\\Administrator\\IdeaProjects\\untitled\\File1.txt");
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(file, false);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
//                try {
//                    fileOutputStream.flush(); /* flush data from the stream into the buffer */
//                    fileOutputStream.getFD().sync()/* Force (confirms) all system buffers to synchronize with the underlying device.*/;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                PrintWriter printWriter = new PrintWriter(fileOutputStream, true);
                printWriter.append("added from thread1");
                printWriter.close(); /* it explicitly calls flush */
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        /*
        By default, UNIX systems read from and write to a buffer cache that is kept in memory, and avoid actually transferring data to
        disk until the buffer is full, or until the application calls a sync function to flush the buffer cache.
        This increases performance by avoiding the relatively slow mechanical process of writing to disk more often then necessary.
         */
            }
        });
        thread1.start();

//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                File file = new File("C:\\Users\\Administrator\\IdeaProjects\\untitled\\File1.txt");
//                BufferedReader br = null;
//                FileReader fr = null;
//
//                try {
//
//                    //br = new BufferedReader(new FileReader(FILENAME));
//                    fr = new FileReader(file);
//                    br = new BufferedReader(fr);
//
//                    String sCurrentLine;
//
//                    while ((sCurrentLine = br.readLine()) != null) {
//                        System.out.println(sCurrentLine);
//                    }
//
//                } catch (IOException e) {
//
//                    e.printStackTrace();
//
//                } finally {
//
//                    try {
//
//                        if (br != null)
//                            br.close();
//
//                        if (fr != null)
//                            fr.close();
//
//                    } catch (IOException ex) {
//
//                        ex.printStackTrace();
//
//                    }
//
//                }
//            }
//        });
//        thread2.sleep(3000);
//        thread2.start();
    }
}
