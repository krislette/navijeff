package frontend;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import algorithm.Node;
import algorithm.Edge;
import algorithm.AStar;

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
    
    @FXML
    private VBox vboxContent;
    
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

            String localUrl = Paths.get("src/backend/map.html").toUri().toString();
            webEngine.load(localUrl);

            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    System.out.println("Loaded: " + webEngine.getLocation());
                    JSObject window = (JSObject) webEngine.executeScript("window");
                    window.setMember("javaApp", this);
                } else if (newState == Worker.State.FAILED) {
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
    
    // Method to ensure currLocation and trgtLocation are initialized before setting values
    public void setInitialLocations(String source, String destination) {
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
            path.forEach(System.out::println);

            // Check if vboxContent is not null before accessing it
            if (vboxContent != null) {
                // Clear the existing content in the VBox
                vboxContent.getChildren().clear();

                // Prepare the path data for JavaScript
                JSONArray pathArray = new JSONArray();

                for (int i = 0; i < path.size(); i++) {
                    Node node = path.get(i);
                    Node pastNode = null;

                    JSONObject point = new JSONObject();
                    point.put("loc", node.getLocation());
                    point.put("lat", node.getLatitude());
                    point.put("lng", node.getLongitude());
                    pathArray.put(point);

                    // Create a new pane for each node
                    if (i > 0) {
                        VBox jeepStop = new VBox();
                        jeepStop.setSpacing(15);
                        jeepStop.setAlignment(Pos.CENTER);

                        Text sourceDestinationText = new Text();
                        Text jeepFareText = new Text();
                        Text edgeDistanceText = new Text();

                        pastNode = path.get(i - 1);
                        sourceDestinationText.getStyleClass().add("source-destination");
                        sourceDestinationText.setText(pastNode.getLocation() + " || " + node.getLocation());

                        double fare = getFare(pastNode, node);
                        int roundedFare = (int) fare;
                        jeepFareText.getStyleClass().add("jeep-fare");
                        jeepFareText.setText("₱" + roundedFare);

                        edgeDistanceText.getStyleClass().add("edge-distance");
                        edgeDistanceText.setText("Distance: " + getDistanceToNextNode(pastNode, node));

                        jeepStop.getStyleClass().add("jeep-stop");
                        jeepStop.getChildren().addAll(sourceDestinationText, jeepFareText, edgeDistanceText);
                        vboxContent.getChildren().add(jeepStop);
                    }
                }

                System.out.println("Path data: " + pathArray.toString());

                if (webEngine != null) {
                    webEngine.executeScript("drawPath('" + pathArray.toString() + "');");
                } else {
                    System.out.println("WebEngine is not initialized.");
                }
            } else {
                System.out.println("vboxContent is null.");
            }

        } else {
            System.out.println("Source or destination node not found.");
        }
    }

    private double getFare(Node sourceNode, Node destNode) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(sourceNode) && edge.getDestination().equals(destNode)) {
                double distance = edge.getDistance();
                double fare = ((distance - 4) * 1.8) + 13;
                return fare;
            }
        }
        return 0.0;
    }

    private double getDistanceToNextNode(Node sourceNode, Node destNode) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(sourceNode) && edge.getDestination().equals(destNode)) {
                return edge.getDistance();
            }
        }
        return 0;
    }

    public void switchToLandingPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
        root = loader.load();

        System.out.println("Loaded LandingPage.fxml");

        Controller controller = loader.getController();
        controller.setGraphData(nodeMap, edges);

        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Image appIcon = new Image("file:src/images/icon.jpeg");
        stage.getIcons().add(appIcon);
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        
        srcLocation = currLocation.getValue();
        destLocation = trgtLocation.getValue();
        
        System.out.println("Switched to LandingPage scene");
    }

    public void switchToMapPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MapPage.fxml"));
        root = loader.load();

        System.out.println("Loaded MapPage.fxml");

        Controller controller = loader.getController();
        controller.setGraphData(nodeMap, edges);

        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Image appIcon = new Image("file:src/images/icon.jpeg");
        stage.getIcons().add(appIcon);
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        System.out.println("Switched to MapPage scene");
    }

    public void callJavaScriptFunction(String source, String destination) {
        if (webEngine != null) {
            String script = String.format("setLocations('%s', '%s');", source, destination);
            webEngine.executeScript(script);
        } else {
            System.out.println("WebEngine is not initialized.");
        }
    }

    public void switchGetRoutePage(ActionEvent event) throws IOException {
        String currentSource = currLocation.getValue();
        String currentDestination = trgtLocation.getValue();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GetRoute.fxml"));
        root = loader.load();

        System.out.println("Loaded GetRoute.fxml");

        Controller controller = loader.getController();
        controller.setGraphData(nodeMap, edges);

        controller.setInitialLocations(currentSource, currentDestination);

        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Image appIcon = new Image("file:src/images/icon.jpeg");
        stage.getIcons().add(appIcon);
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/frontend/pane.css").toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        WebView webView = controller.getWebView();
        if (webView != null) {
            WebEngine webEngine = webView.getEngine();
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {

                    System.out.println("WebView loaded: " + webEngine.getLocation());
                    controller.setLocations(currentSource, currentDestination);
                } else if (newState == Worker.State.FAILED) {

                    System.out.println("Failed to load WebView: " + webEngine.getLocation());
                    webEngine.getLoadWorker().getException().printStackTrace();
                }
            });
        } else {
            System.out.println("WebView is null.");
        }

        System.out.println("Switched to GetRoute scene");
    }
    
}
