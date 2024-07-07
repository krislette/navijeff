package algorithm;

import java.util.ArrayList;
import java.util.List;

public class Node {
    
    private final String location;
    private final double latitude;
    private final double longitude;
    private final List<Edge> edges;

    /**
     * Constructs a node with a given location name, latitude, and longitude.
     * 
     * @param location The name or identifier of the node's location.
     * @param latitude The latitude coordinate of the node.
     * @param longitude The longitude coordinate of the node.
     */
    public Node(String location, double latitude, double longitude) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.edges = new ArrayList<>();
    }
    
    // Returns a string representation of the node
    @Override
    public String toString() {
        return "Node{" +
                "location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    // Getter for the location name of the node
    public String getLocation() {
        return location;
    }

    // Getter for the latitude of the node
    public double getLatitude() {
        return latitude;
    }

    // Getter for the longitude of the node
    public double getLongitude() {
        return longitude;
    }

    // Getter for the list of edges connected to a node
    public List<Edge> getEdges() {
        return edges;
    }

    // Adds an edge to the list of edges connected to a node
    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

}