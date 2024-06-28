package algorithm;

import java.util.*;

public class AStar {

    public static List<Node> findPath(Node start, Node goal, List<Edge> edges) {
        Map<Node, Node> lastNode = new HashMap<>();
        Map<Node, Double> gScore = new HashMap<>();
        Map<Node, Double> fScore = new HashMap<>();

        PriorityQueue<Node> priorityNodes = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        priorityNodes.add(start);

        gScore.put(start, 0.0);
        fScore.put(start, heuristic(start, goal));

        while (!priorityNodes.isEmpty()) {
            Node currentNode = priorityNodes.poll();

            if (currentNode.equals(goal)) {
                return createPath(lastNode, currentNode);
            }

            for (Edge edge : edges) {
                Node neighbor;
                if (edge.getSource().equals(currentNode)) {
                    neighbor = edge.getDestination();
                } else if (edge.getDestination().equals(currentNode)) {
                    neighbor = edge.getSource();
                } else {
                    continue;
                }

                double estimatedGScore = gScore.getOrDefault(currentNode, Double.MAX_VALUE) + edge.getDistance();
                if (estimatedGScore < gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    lastNode.put(neighbor, currentNode);
                    gScore.put(neighbor, estimatedGScore);
                    fScore.put(neighbor, estimatedGScore + heuristic(neighbor, goal));
                    if (!priorityNodes.contains(neighbor)) {
                        priorityNodes.add(neighbor);
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private static double heuristic(Node node1, Node node2) {
        return calculateDistance(node1, node2);
    }

    private static List<Node> createPath(Map<Node, Node> lastNode, Node currentNode) {
        List<Node> totalPath = new ArrayList<>();
        totalPath.add(currentNode);
        
        while (lastNode.containsKey(currentNode)) {
            currentNode = lastNode.get(currentNode);
            totalPath.add(currentNode);
        }
        Collections.reverse(totalPath);
        
        return totalPath;
    }

    // Haversine formula to calculate distance between two points
    // Heuristic computation for AStar
    private static double calculateDistance(Node node1, Node node2) {
        double lat1 = node1.getLatitude();
        double lon1 = node1.getLongitude();
        double lat2 = node2.getLatitude();
        double lon2 = node2.getLongitude();
        
        final int R = 6371; // Radius of the Earth in kilometers
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c; // Distance in kilometers
    }
    
}