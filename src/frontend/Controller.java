package frontend;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import algorithm.Node;
import algorithm.Edge;
import algorithm.AStar;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class Controller implements Initializable {

    @FXML
    private WebView webView;
    private WebEngine webEngine;

    private Map<String, Node> nodeMap;
    private List<Edge> edges;
    private String srcLocation;
    private String destLocation;

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private ComboBox<String> currLocation;
    @FXML
    private ComboBox<String> trgtLocation;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        currLocation.setOnAction(event -> getCurrentLocation(event));
        trgtLocation.setOnAction(event -> getTargetLocation(event));
        
        if (currLocation != null && trgtLocation != null) {
            currLocation.setItems(FXCollections.observableArrayList(
                "Sto. Niño de Bagong Silang Parish", "Novaliches Jeepney Terminal", "Jeepney Terminal: VGC",
                "Monumento", "Polo Public Market", "Naval St. Navotas", "Malabon City Square", "Manila: Quiapo",
                "San Juan Comelec", "Pasig Blvd. Ext.", "SM City Marikina", "EDSA Central Jeep Terminal",
                "Gateway Mall", "SM City Fairview"
            ));
            trgtLocation.setItems(FXCollections.observableArrayList(
                "Sto. Niño de Bagong Silang Parish", "Novaliches Jeepney Terminal", "Jeepney Terminal: VGC",
                "Monumento", "Polo Public Market", "Naval St. Navotas", "Malabon City Square", "Manila: Quiapo",
                "San Juan Comelec", "Pasig Blvd. Ext.", "SM City Marikina", "EDSA Central Jeep Terminal",
                "Gateway Mall", "SM City Fairview"
            ));
        }

        if (webView != null) {
            webEngine = webView.getEngine();

            // Load local URL
            String localUrl = Paths.get("src/backend/map.html").toUri().toString();
            webEngine.load(localUrl);

            // Listener for when webEngine finishes loading
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    // Successful load
                    System.out.println("Loaded: " + webEngine.getLocation());
                    JSObject window = (JSObject) webEngine.executeScript("window");
                    window.setMember("javaApp", this);
                } else if (newState == Worker.State.FAILED) {
                    // Failed to load
                    System.out.println("Failed to load: " + webEngine.getLocation());
                    webEngine.getLoadWorker().getException().printStackTrace();
                }
            });
        } else {
            System.out.println("webView is null in initialize");
        }
    }
    
    public WebView getWebView() {
        return webView;
    }
    
    public void setInitialLocations(String source, String destination) {
        // Ensure currLocation and trgtLocation are initialized before setting values
        if (currLocation != null && trgtLocation != null) {
            currLocation.setValue(source);
            trgtLocation.setValue(destination);
        } else {
            System.out.println("currLocation or trgtLocation is null.");
        }
    }
        
    // Method to set the location of Current Location from combobox
    public void getCurrentLocation(ActionEvent event){
        srcLocation = currLocation.getValue();
        setLocations(srcLocation, destLocation);
    }
    
    // Method to set the location of Destination Location from combobox
    public void getTargetLocation(ActionEvent event){
        destLocation = trgtLocation.getValue();
        setLocations(srcLocation, destLocation);
    }
    
    // Method to set the nodeMap and edges from Main class
    public void setGraphData(Map<String, Node> nodeMap, List<Edge> edges) {
        this.nodeMap = Main.getNodeMap();
        this.edges = Main.getEdges();
    }

    // Method to set source and destination locations and draw path
    public void setLocations(String source, String destination) {
             
        this.srcLocation = source;
        this.destLocation = destination;
        System.out.println("Source Location: " + source);
        System.out.println("Destination Location: " + destination);

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

            // Log the path data
            System.out.println("Path data: " + pathArray.toString());

            // Execute the drawPath function in JavaScript
            if (webEngine != null) {
                webEngine.executeScript("drawPath('" + pathArray.toString() + "');");
            } else {
                System.out.println("WebEngine is not initialized.");
            }
        } else {
            System.out.println("Source or destination node not found.");
        }
    }

    public void switchToLandingPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
        root = loader.load();

        // Log for debugging
        System.out.println("Loaded LandingPage.fxml");

        Controller controller = loader.getController();
        controller.setGraphData(nodeMap, edges);

        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        srcLocation = currLocation.getValue();
        destLocation = trgtLocation.getValue();
        // Debugging
        System.out.println("Switched to LandingPage scene");
    }

    public void switchToMapPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MapPage.fxml"));
        root = loader.load();

        // Log for debugging
        System.out.println("Loaded MapPage.fxml");

        Controller controller = loader.getController();
        controller.setGraphData(nodeMap, edges);

        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Debugging
        System.out.println("Switched to MapPage scene");
    }
    
   public void callJavaScriptFunction(String source, String destination) {
        if (webEngine != null) {
            String script = String.format("setLocations('%s', '%s');", source, destination);
            webEngine.executeScript(script);
        } else {
            System.out.println("WebEngine is not initialized.");
            // Handle the case where webEngine is not initialized
        }
 }

public void switchGetRoutePage(ActionEvent event) throws IOException {
    // Store current selected values
    String currentSource = currLocation.getValue();
    String currentDestination = trgtLocation.getValue();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("GetRoute.fxml"));
    root = loader.load();

    // Log for debugging
    System.out.println("Loaded GetRoute.fxml");

    Controller controller = loader.getController();
    controller.setGraphData(nodeMap, edges);

    // Set stored values into GetRoute.fxml controller
    controller.setInitialLocations(currentSource, currentDestination);

    stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();

    // Add listener to WebView load state
    WebView webView = controller.getWebView(); // Ensure this method returns the WebView instance
    if (webView != null) {
        WebEngine webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // Successfully loaded
                System.out.println("WebView loaded: " + webEngine.getLocation());
                controller.setLocations(currentSource, currentDestination);
            } else if (newState == Worker.State.FAILED) {
                // Failed to load
                System.out.println("Failed to load WebView: " + webEngine.getLocation());
                webEngine.getLoadWorker().getException().printStackTrace();
            }
        });
    } else {
        System.out.println("WebView is null.");
    }

    // Debugging
    System.out.println("Switched to GetRoute scene");
}
}