import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Administrator on 9/25/2017.
 */

public class MyServerSocket {
    private static ServerSocket socket;
    private static BufferedReader bufferedReader;
    private static volatile Socket clientSocket;
    private static OutputStreamWriter outputStreamWriter;
    private static PrintWriter printWriter;
    private static Scanner scanner;

    public static void main(String[] args) throws IOException {
        socket = new ServerSocket(9999); //server listens on port 9999
        clientSocket = getClientSocket(); //socket.accept()


        //read from client
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //read from socket
                    bufferedReader = getBufferedReader();
                    String line = null;
                    System.out.println("Chat with Client, type \"exit\" to exit");

                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println("At server side: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bufferedReader.close();
                        clientSocket.close();
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
                        System.out.println("From self (Server): " + line1);
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
                        clientSocket.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        thread2.start();

    }

    public synchronized static ServerSocket getSocket() {
        return socket;
    }

    public synchronized static void setSocket(ServerSocket socket) {
        MyServerSocket.socket = socket;
    }

    public synchronized static BufferedReader getBufferedReader() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return bufferedReader;
    }

    public synchronized static void setBufferedReader(BufferedReader bufferedReader) {
        MyServerSocket.bufferedReader = bufferedReader;
    }

    public synchronized static Socket getClientSocket() throws IOException {
        clientSocket = socket.accept();
        return clientSocket;
    }

    public synchronized static void setClientSocket(Socket clientSocket) {
        MyServerSocket.clientSocket = clientSocket;
    }

    public synchronized static OutputStreamWriter getOutputStreamWriter() throws IOException {
        outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
        return outputStreamWriter;
    }

    public synchronized static void setOutputStreamWriter(OutputStreamWriter outputStreamWriter) {
        MyServerSocket.outputStreamWriter = outputStreamWriter;
    }

    public synchronized static PrintWriter getPrintWriter() {
        printWriter = new PrintWriter(outputStreamWriter);
        return printWriter;
    }

    public synchronized static void setPrintWriter(PrintWriter printWriter) {
        MyServerSocket.printWriter = printWriter;
    }

    public synchronized static Scanner getScanner() {
        return scanner;
    }

    public synchronized static void setScanner(Scanner scanner) {
        MyServerSocket.scanner = scanner;
    }
}
