import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Administrator on 9/25/2017.
 */

public class MyClientSocket {
    private static Socket socket;
    private static PrintWriter printWriter;
    private static OutputStreamWriter outputStreamWriter;
    private static Scanner scanner;
    private static BufferedReader bufferedReader;

    public static void main(String[] args) throws IOException {
        socket = new Socket("localhost", 9999);


        //read from server
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //read from socket
                    bufferedReader = getBufferedReader();
                    String line = null;
                    System.out.println("Chat with Server, type \"exit\" to exit");

                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println("At Client side: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bufferedReader.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread1.start();


        //write to socket
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //input from console and write to socket
                    outputStreamWriter = getOutputStreamWriter();

                    printWriter = getPrintWriter();
                    String line1 = null;

                    scanner = new Scanner(System.in);
                    boolean exitBoolean = true;
                    while (exitBoolean) {
                        line1 = scanner.nextLine();
                        System.out.println("From self (Client): " + line1);
                        if (line1.toString().equals("exit\n")) {
                            exitBoolean = false;
                            break;
                        }
                        printWriter.write(line1 + "\n");
                        printWriter.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        System.in.close();
                        scanner.close();
                        outputStreamWriter.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        thread2.start();

    }


    public synchronized static BufferedReader getBufferedReader() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return bufferedReader;
    }

    public synchronized static void setBufferedReader(BufferedReader bufferedReader) {
        MyClientSocket.bufferedReader = bufferedReader;
    }


    public synchronized static OutputStreamWriter getOutputStreamWriter() throws IOException {
        outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        return outputStreamWriter;
    }

    public synchronized static void setOutputStreamWriter(OutputStreamWriter outputStreamWriter) {
        MyClientSocket.outputStreamWriter = outputStreamWriter;
    }

    public synchronized static PrintWriter getPrintWriter() {
        printWriter = new PrintWriter(outputStreamWriter);
        return printWriter;
    }

    public synchronized static void setPrintWriter(PrintWriter printWriter) {
        MyClientSocket.printWriter = printWriter;
    }

    public synchronized static Scanner getScanner() {
        return scanner;
    }

    public synchronized static void setScanner(Scanner scanner) {
        MyClientSocket.scanner = scanner;
    }
}
