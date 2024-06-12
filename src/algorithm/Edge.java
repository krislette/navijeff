package algorithm;

public class Edge {
    
    private Node source;
    private Node destination;
    private double distance;

    public Edge(Node source, Node destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source.getLocation() +
                ", destination=" + destination.getLocation() +
                ", distance=" + distance +
                '}';
    }
    
    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public double getDistance() {
        return distance;
    }

}