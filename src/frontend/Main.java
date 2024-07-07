package frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;
import algorithm.Edge;
import algorithm.Node;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    private static Map<String, Node> nodeMap;
    private static List<Edge> edges;

    // Getter for the nodeMap
    public static Map<String, Node> getNodeMap() {
        return nodeMap;
    }

    // Getter for the edges
    public static List<Edge> getEdges() {
        return edges;
    }
    
    // Main method with node and edge creation
    public static void main(String[] args) {
        List<Node> nodes = new ArrayList<>();
        edges = new ArrayList<>();
        nodeMap = new HashMap<>();

        try {
            // Load JSON content from file
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
                    System.out.println("Node not found for source or destination: " + sourceLocation + " -> " + destinationLocation);
                }
            }
            
            // Launch JavaFX application
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method invoked by JavaFX to start the application
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Parent root = loader.load();

            Controller controller = loader.getController();
            controller.setGraphData(nodeMap, edges);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Image appIcon = new Image("file:src/images/icon.jpeg");
            stage.getIcons().add(appIcon);
            stage.setResizable(false); 
            stage.setScene(scene);
            stage.setTitle("NaviJeff");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}