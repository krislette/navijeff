package algorithm;

import java.util.ArrayList;
import java.util.List;

public class Node {
    
    private final String location;
    private final double latitude;
    private final double longitude;
    private final List<Edge> edges;

    public Node(String location, double latitude, double longitude) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.edges = new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Node{" +
                "location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

}