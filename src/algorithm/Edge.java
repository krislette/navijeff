package algorithm;

public class Edge {
    
    private final Node source;
    private final Node destination;
    private final double distance;

    /**
     * Constructs an edge between two nodes with a given distance.
     * 
     * @param source The source node of the edge.
     * @param destination The destination node of the edge.
     * @param distance The distance between the source and destination nodes.
     */
    public Edge(Node source, Node destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    // Returns a string representation of the edge
    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source.getLocation() +
                ", destination=" + destination.getLocation() +
                ", distance=" + distance +
                '}';
    }
    
    // Getter for the source node of the edge
    public Node getSource() {
        return source;
    }

    // Getter for the destination node of the edge
    public Node getDestination() {
        return destination;
    }

    // Getter for the distance of the edge between the source and dest nodes
    public double getDistance() {
        return distance;
    }

}