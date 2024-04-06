package UniversalFileSharing;

import java.io.*;
import java.net.*;
import java.nio.channels.FileLockInterruptionException;
import java.util.Scanner;


public class clientSide {
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;

    public static void main(String[] args) throws IOException {
        final String SERVER_ADDRESS = "192.168.0.77";
        final int PORT = 8888;

        Scanner scan = new Scanner(System.in);

        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            readFile("test.pdf");

            dataOutputStream.close();
            dataInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(String filepath) throws IOException {
        int bytes = 0;
        File file = new File(filepath);
        FileInputStream fileInputStream = new FileInputStream(file);

        dataOutputStream.writeLong(file.length());
        byte[] buffer = new byte[4*1024];
        while((bytes = fileInputStream.read(buffer)) != -1) {
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }
}
