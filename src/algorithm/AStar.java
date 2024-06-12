package algorithm;

import java.util.*;

public class AStar {

    public static List<Node> findPath(Node start, Node goal, List<Edge> edges) {
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Double> gScore = new HashMap<>();
        Map<Node, Double> fScore = new HashMap<>();

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        openSet.add(start);

        gScore.put(start, 0.0);
        fScore.put(start, heuristic(start, goal));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }

            for (Edge edge : edges) {
                Node neighbor;
                if (edge.getSource().equals(current)) {
                    neighbor = edge.getDestination();
                } else if (edge.getDestination().equals(current)) {
                    neighbor = edge.getSource();
                } else {
                    continue;
                }

                double tentativeGScore = gScore.getOrDefault(current, Double.MAX_VALUE) + edge.getDistance();
                if (tentativeGScore < gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + heuristic(neighbor, goal));
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return Collections.emptyList(); // No path found
    }

    private static double heuristic(Node node1, Node node2) {
        return calculateDistance(node1, node2);
    }

    private static List<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<Node> totalPath = new ArrayList<>();
        totalPath.add(current);
        
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            totalPath.add(current);
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