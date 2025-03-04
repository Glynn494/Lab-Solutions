package lab2solution;

import javax.imageio.ImageIO;       // The javax.imageio package provides classes and interfaces for reading and writing images
import javax.swing.*;               // The javax.swing package provides classes for creating a graphical user interface (GUI)
import java.awt.*;                  // The java.awt package provides classes for creating GUI components
import java.io.IOException;         // The java.io package provides classes for input and output through data streams
import java.io.InputStream;
import java.net.URI;                // The java.net package provides classes for networking operations
import java.net.http.HttpClient;    // The java.net.http package provides classes for HTTP operations
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// AvatarGenerator is a class that can generate a random avatar image and display it in a window.
// It is in the lab2 package
public class AvatarGenerator {

    public static void main(String[] args) {

        try {
            // avatarStream is an InputStream, a reference type
            // getRandomAvatarStream is a class method that returns an InputStream containing the bytes for a PNG avatar image
            var avatarStream = AvatarGenerator.getRandomAvatarStream();
            // showAvatar is a class method that displays a PNG image in a window
            AvatarGenerator.showAvatar(avatarStream);
        } catch (IOException | InterruptedException e) {
            // e may be an IOException or an InterruptedException
            // printStackTrace is an instance method that prints the stack trace of the exception to the console
            e.printStackTrace();
        }

    }

    public static InputStream getRandomAvatarStream() throws IOException, InterruptedException {
        // Pick a random style
        // String is a reference type of the java.lang package
        // styles is an array of Strings, a reference type
        String[] styles = { "adventurer", "adventurer-neutral", "avataaars", "big-ears", "big-ears-neutral", "big-smile", "bottts", "croodles", "croodles-neutral", "fun-emoji", "icons", "identicon", "initials", "lorelei", "micah", "miniavs", "open-peeps", "personas", "pixel-art", "pixel-art-neutral" };
        // style is a String a reference type
        // Math is a class of the java.lang package that provides methods for performing basic numeric operations
        // random is a class method that returns a random double between 0.0 and 1.0
        // length is an instance variable that contains the number of elements in the array
        var style = styles[(int)(Math.random() * styles.length)];

        // Generate a random seed
        // seed is an int, a primitive type
        var seed = (int)(Math.random() * 10000);

        // Create an HTTP request for a random avatar
        // uri is a URI, a reference type of the java.net package
        // URI is a class that represents a Uniform Resource Identifier
        // create is a class methods that creates a URI object from a given string
        // formatted is a String instance method that returns a formatted string using the given arguments
        var uri = URI.create("https://api.dicebear.com/9.x/%s/png?seed=%d".formatted(style, seed));
        // request is an HttpRequest, a reference type
        // HttpRequest is a class of the java.net.http package that represents an HTTP request
        // newBuilder is a class method that creates a new HttpRequest.Builder, which is used to build an HttpRequest
        // build is an instance method that returns the HttpRequest from a HttpRequest.Builder
        var request = HttpRequest.newBuilder(uri).build();

        // Send the request
        // client is an HttpClient, a reference type of the java.net.http package
        // newHttpClient is a class method that returns a new HttpClient
        try (var client = HttpClient.newHttpClient()) {
            // response is an HttpResponse, a reference type of the java.net.http package
            // HttpResponse is a class that represents an HTTP response
            // send is an instance method that sends an HttpRequest and returns an HttpResponse
            // BodyHandlers is a class variable (a class defined inside the HttpResponse class) of the java.net.http package that provides factory methods for creating BodyHandler instances
            // ofInputStream is a class method that returns a BodyHandler for HTTP responses that contain byte arrays
            var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            // body is an instance method that returns the body of the HttpResponse (in this case, the avatar image data) as an InputStream
            return response.body();
        }
    }

    public static void showAvatar(InputStream imageStream) {
            // frame is a JFrame, a reference type of the javax.swing package
            // JFrame is a class that represents a window with a title bar and a border
            // A constructor method is used here to create a new JFrame object
            // The String passed to the constructor here is a reference
            JFrame frame = new JFrame("PNG Viewer");
            // setDefaultCloseOperation is an instance method that sets the default close operation of the JFrame
            // EXIT_ON_CLOSE is a class variable of the JFrame class that specifies that the application should exit when the JFrame is closed
            // EXIT_ON_CLOSE is an int, a primitive type
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // setResizable is an instance method that sets whether the JFrame is resizable
            // false is a boolean, a primitive type
            frame.setResizable(false);
            // setSize is an instance method that sets the size of the JFrame
            // 200 is an int, a primitive type
            frame.setSize(200, 200);
            // getContentPane is an instance method that returns a Container, the content pane of the JFrame
            // setBackground is an instance method that sets the background color of a Container
            // Color is a reference type of the java.awt package that represents a color
            // BLACK is a class variable of the Color class that represents the color black. It is itself a Color object, and hence is a reference here.
            frame.getContentPane().setBackground(Color.BLACK);

            try {
                // Load the PNG image
                // ImageIO is a reference type of the javax.imageio package that provides methods for reading and writing images
                // read is a class method that reads an image from the specified InputStream and returns an Image.
                // Image is a reference type of the java.awt package that represents an image
                // image is a reference to an Image object
                // imageStream is a reference to an InputStream object
                var image = ImageIO.read(imageStream);

                // Create a JLabel to display the image
                // JLabel is a class of the javax.swing package that represents a text label
                // ImageIcon is a class of the javax.swing package that represents an image icon
                // new ImageIcon(image) is a constructor method that creates a new ImageIcon object from an Image
                // imageLabel is a reference to a JLabel object
                // new JLabel(new ImageIcon(image)) is a constructor method that creates a new JLabel object with an ImageIcon
                JLabel imageLabel = new JLabel(new ImageIcon(image));

                // add is an instance method that adds a component to the JFrame
                // BorderLayout is a class of the java.awt package that provides a layout manager
                // CENTER is a class variable of the BorderLayout class that specifies the center position in a BorderLayout
                // BorderLayout.CENTER is a reference to an object of type String
                frame.add(imageLabel, BorderLayout.CENTER);

            } catch (IOException e) {
                // e is an IOException, a reference type
                // printStackTrace is an instance method that prints the stack trace of the exception to the console
                e.printStackTrace();
            }

            // setVisible is an instance method that sets the visibility of the JFrame
            // true is a boolean, a primitive type
            frame.setVisible(true);
    }
}
