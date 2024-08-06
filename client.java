package chatbot;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234; // Adjusted to match the server's port

    public static void main(String[] args) {
        try {
            // Connect to the server
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to the chat server!");

            // Setting up input and output streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start a thread to handle incoming messages
            new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    System.out.println("Connection to server lost.");
                }
            }).start();

            // Read messages from the console and send to the server
            Scanner scanner = new Scanner(System.in);
            String userInput;
            while (true) {
                userInput = scanner.nextLine();
                out.println(userInput);

                // Exit command
                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            // Clean up resources
            in.close();
            out.close();
            socket.close();
            scanner.close();
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + SERVER_ADDRESS);
        } catch (IOException e) {
            System.out.println("Unable to connect to server: " + SERVER_ADDRESS + " on port " + SERVER_PORT);
        }
    }
}
