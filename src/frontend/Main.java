package frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithm.Edge;
import algorithm.Node;
import algorithm.AStar;

public class Main extends Application {

    private static Map<String, Node> nodeMap;
    private static List<Edge> edges;
    private WebEngine webEngine;
    private static String srcLocation;
    private static String destLocation;

    // Create NODES and EDGES
    public static void main(String[] args) {
        List<Node> nodes = new ArrayList<>();
        edges = new ArrayList<>();
        nodeMap = new HashMap<>();

        try {
            // Load JSON content
            String content = new String(Files.readAllBytes(Paths.get("src/backend/geopositions.json")));
            JSONObject jsonObject = new JSONObject(content);

            // Create nodes from JSON data
            JSONArray nodesArray = jsonObject.getJSONArray("nodes");
            for (int i = 0; i < nodesArray.length(); i++) {
                JSONObject nodeObject = nodesArray.getJSONObject(i);
                String location = nodeObject.getString("location");
                double latitude = nodeObject.getDouble("latitude");
                double longitude = nodeObject.getDouble("longitude");

                Node node = new Node(location, latitude, longitude);
                nodes.add(node);
                nodeMap.put(location, node);
            }

            // Create edges from JSON data
            JSONArray edgesArray = jsonObject.getJSONArray("edges");
            for (int i = 0; i < edgesArray.length(); i++) {
                JSONObject edgeObject = edgesArray.getJSONObject(i);
                String sourceLocation = edgeObject.getString("source");
                String destinationLocation = edgeObject.getString("destination");
                double distance = edgeObject.getDouble("distance");

                Node sourceNode = nodeMap.get(sourceLocation);
                Node destNode = nodeMap.get(destinationLocation);
                if (sourceNode != null && destNode != null) {
                    edges.add(new Edge(sourceNode, destNode, distance));
                } else {
                    System.out.println("Node not found for source or destination: " + srcLocation + " -> " + destLocation);
                }
            }
            
            // Launch the JavaFX application
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // WEBVIEW
    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();
        webEngine = webView.getEngine();

        System.setProperty("prism.forceGPU", "true");
        System.setProperty("sun.java2d.opengl", "true");

        String localUrl = Paths.get("src/backend/map.html").toUri().toString();
        webEngine.load(localUrl);

        Scene scene = new Scene(webView, 800, 630);
        stage.setTitle("NaviJeff");
        stage.setScene(scene);
        stage.show();

        // Log the current URL and any errors
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                System.out.println("Loaded: " + webEngine.getLocation());
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaApp", this);
            } else if (newState == javafx.concurrent.Worker.State.FAILED) {
                System.out.println("Failed to load: " + webEngine.getLocation());
                webEngine.getLoadWorker().getException().printStackTrace();
            }
        });
    }
    
    // SOURCE and DESTINATION input scanner
    public void setLocations(String source, String destination) {
        srcLocation = source;
        destLocation = destination;
        calculateAndDrawPath();
    }

    // Function to calculate and draw path using A* algorithm
    private void calculateAndDrawPath() {
        Node sourceNode = nodeMap.get(srcLocation);
        Node destNode = nodeMap.get(destLocation);

        if (sourceNode != null && destNode != null) {
            List<Node> path = AStar.findPath(sourceNode, destNode, edges);

            // Print the path for debugging purposes
            path.forEach(System.out::println);

            // Prepare the path data for JavaScript
            JSONArray pathArray = new JSONArray();
            for (Node node : path) {
                JSONObject point = new JSONObject();
                point.put("loc", node.getLocation());
                point.put("lat", node.getLatitude());
                point.put("lng", node.getLongitude());
                pathArray.put(point);
            }

            // Execute the drawPath function in JavaScript
            webEngine.executeScript("drawPath('" + pathArray.toString() + "');");
        } else {
            System.out.println("Source or destination node not found.");
        }
    }
    
}