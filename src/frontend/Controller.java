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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import algorithm.Node;
import algorithm.Edge;
import algorithm.AStar;

import javafx.geometry.Pos;
import javafx.application.Platform;

import backend.Location;
import javafx.scene.image.ImageView;

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
    private ComboBox<Location> currLocation;
    
    @FXML
    private ComboBox<Location> trgtLocation;
    
    @FXML
    private VBox vboxContent;
    
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private Label locationAddress;
    
    @FXML
    private Label locationName;
    
    @FXML
    private ImageView stationImage;
    
    @FXML
    private ImageView stationLogo;
    
    @FXML
    private ImageView addressLogo;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (currLocation != null && trgtLocation != null) {
            currLocation.setOnAction(event -> getCurrentLocation(event));
            trgtLocation.setOnAction(event -> getTargetLocation(event));

            // Define the stations with their corresponding city
            List<Location> locations = List.of(
                new Location("Phase 1 Church", "North Caloocan"),
                new Location("Monumento", "South Caloocan"),
                new Location("VGC Terminal", "Valenzuela"),
                new Location("Polo Market", "Valenzuela"),
                new Location("Novaliches", "Quezon City"),
                new Location("Gateway Mall", "Quezon City"),
                new Location("SM Fairview", "Quezon City"),
                new Location("Naval St. Navotas", "Navotas"),
                new Location("Fisher Malabon", "Malabon"),
                new Location("Quiapo", "Manila"),
                new Location("San Juan Comelec", "San Juan"),
                new Location("Pasig Blvd. Ext.", "Pasig"),
                new Location("SM Marikina", "Marikina"),
                new Location("EDSA Terminal", "Mandaluyong")
            );

            currLocation.setItems(FXCollections.observableArrayList(locations));
            trgtLocation.setItems(FXCollections.observableArrayList(locations));

            // Set custom cell factory to display location names with guides
            Callback<ListView<Location>, ListCell<Location>> cellFactory = (ListView<Location> param) -> new ListCell<>() {
                @Override
                protected void updateItem(Location item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getStation());
                        
                        Label lblCity = new Label(" " + item.getCity());
                        lblCity.getStyleClass().add("city");
                        setGraphic(lblCity);
                    }
                }
            };
            currLocation.setCellFactory(cellFactory);
            currLocation.setButtonCell(cellFactory.call(null));
            trgtLocation.setCellFactory(cellFactory);
            trgtLocation.setButtonCell(cellFactory.call(null));
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
                    
                    // Load geopositions JSON and pass to JavaScript
                    try {
                        String jsonContent = new String(Files.readAllBytes(Paths.get("src/backend/geopositions.json")));
                        webEngine.executeScript("window.setStationPins(" + jsonContent + ");");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (newState == Worker.State.FAILED) {
                    System.out.println("Failed to load: " + webEngine.getLocation());
                    webEngine.getLoadWorker().getException().printStackTrace();
                }
            });
        } else {
            System.out.println("webView is null in initialize");
        }
        
        if (scrollPane != null) {
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        }
    }
    
    public WebView getWebView() {
        return webView;
    }
    
    public void setStationImage(String stationName) {
        switch (stationName) {
            case "Phase 1 Church":
                stationImage.setImage(new Image("file:src/places/phase-1.jpg")); 
                break;
            case "Novaliches": 
                stationImage.setImage(new Image("file:src/places/novaliches.jpg")); 
                break;
            case "VGC Terminal": 
                stationImage.setImage(new Image("file:src/places/vgc.jpg")); 
                break;
            case "Monumento": 
                stationImage.setImage(new Image("file:src/places/monumento.jpg")); 
                break;
            case "Polo Market": 
                stationImage.setImage(new Image("file:src/places/polo.jpg")); 
                break;
            case "Naval St. Navotas": 
                stationImage.setImage(new Image("file:src/places/navotas.jpg")); 
                break;
            case "Fisher Malabon": 
                stationImage.setImage(new Image("file:src/places/malabon.jpg")); 
                break;
            case "Quiapo": 
                stationImage.setImage(new Image("file:src/places/quiapo.jpg")); 
                break;
            case "San Juan Comelec": 
                stationImage.setImage(new Image("file:src/places/san-juan.jpg")); 
                break;
            case "Pasig Blvd. Ext.": 
                stationImage.setImage(new Image("file:src/places/pasig.jpg")); 
                break;
            case "SM Marikina": 
                stationImage.setImage(new Image("file:src/places/marikina.jpg")); 
                break;
            case "EDSA Terminal": 
                stationImage.setImage(new Image("file:src/places/mandaluyong.jpg")); 
                break;
            case "Gateway Mall": 
                stationImage.setImage(new Image("file:src/places/gateway.jpg")); 
                break;
            case "SM Fairview": 
                stationImage.setImage(new Image("file:src/places/fairview.jpg")); 
                break;
            default:
                stationImage.setImage(null); 
                break;
        }
    }
    
    // Method called from JavaScript to update location info
    public void displayLocationInfo(String stationName, String address) {
        Platform.runLater(() -> {
            locationName.setText(stationName);
            locationName.setWrapText(true);
            locationAddress.setText(address);
            locationAddress.setWrapText(true);
            
            stationLogo.setImage(new Image("file:src/images/station.png"));
            addressLogo.setImage(new Image("file:src/images/address.png"));
            
            setStationImage(stationName);
        });
    }
    
    // Method to ensure currLocation and trgtLocation are initialized before setting values
    public void setInitialLocations(String source, String destination) {
        if (currLocation != null && trgtLocation != null) {
            Location sourceLocation = findLocationByName(source);
            Location destinationLocation = findLocationByName(destination);
            currLocation.setValue(sourceLocation);
            trgtLocation.setValue(destinationLocation);
        } else {
            System.out.println("currLocation or trgtLocation is null.");
        }
    }
    
    // Helper method to find a Location by name
    private Location findLocationByName(String name) {
        return currLocation.getItems().stream()
                .filter(location -> location.getStation().equals(name))
                .findFirst().orElse(null);
    }
        
    // Method to set the location of Current Location from combobox
    public void getCurrentLocation(ActionEvent event) {
        Location selectedLocation = currLocation.getValue();
        if (selectedLocation != null) {
            srcLocation = selectedLocation.getStation();
            setLocations(srcLocation, destLocation);
        }
    }
    
    // Method to set the location of Destination Location from combobox
    public void getTargetLocation(ActionEvent event) {
        Location selectedLocation = trgtLocation.getValue();
        if (selectedLocation != null) {
            destLocation = selectedLocation.getStation();
            setLocations(srcLocation, destLocation);
        }
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
                        jeepStop.setSpacing(10);
                        jeepStop.setAlignment(Pos.CENTER);

                        Label sourceDestinationText = new Label();
                        Label jeepFareText = new Label();
                        Label edgeDistanceText = new Label();

                        pastNode = path.get(i - 1);
                        sourceDestinationText.getStyleClass().add("source-destination");
                        sourceDestinationText.setText(pastNode.getLocation() + " || " + node.getLocation());

                        double fare = getFare(pastNode, node);
                        int roundedFare = (int) fare;
                        jeepFareText.getStyleClass().add("jeep-fare");
                        jeepFareText.setText("₱" + roundedFare);

                        edgeDistanceText.getStyleClass().add("edge-distance");
                        edgeDistanceText.setText("Distance: " + getDistanceToNextNode(pastNode, node) + " km");

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
                if (edge.getDistance() > 4) {
                    double fare = ((distance - 4) * 1.8) + 13;
                    return fare;
                } else{
                    double fare = 13;
                    return fare;
                }
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
        Location currentSource = currLocation.getValue();
        Location currentDestination = trgtLocation.getValue();
        
        String currentSourceName = (currentSource != null) ? currentSource.getStation() : "DefaultSource";
        String currentDestinationName = (currentDestination != null) ? currentDestination.getStation() : "DefaultDestination";
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GetRoute.fxml"));
        root = loader.load();

        System.out.println("Loaded GetRoute.fxml");

        Controller controller = loader.getController();
        controller.setGraphData(nodeMap, edges);

        controller.setInitialLocations(currentSourceName, currentDestinationName);

        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Image appIcon = new Image("file:src/images/icon.jpeg");
        stage.getIcons().add(appIcon);
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/frontend/style.css").toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        WebView webView = controller.getWebView();
        if (webView != null) {
            WebEngine webEngine = webView.getEngine();
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {

                    System.out.println("WebView loaded: " + webEngine.getLocation());
                    controller.setLocations(currentSourceName, currentDestinationName);
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