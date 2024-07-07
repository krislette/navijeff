package algorithm;

import java.util.*;

public class AStar {

    /**
     * This method finds the shortest path from the 
     * start node to the goal node using the A* algorithm.
     * 
     * @param start The starting node.
     * @param goal The goal node.
     * @param edges A list of edges representing the graph.
     * @return A list of nodes representing the path from start to goal.
     */
    public static List<Node> findPath(Node start, Node goal, List<Edge> edges) {
        Map<Node, Node> lastNode = new HashMap<>();
        Map<Node, Double> gScore = new HashMap<>();
        Map<Node, Double> fScore = new HashMap<>();

        // Priority queue to hold nodes to be evaluated, sorted by their fScore
        PriorityQueue<Node> priorityNodes = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        priorityNodes.add(start);
        
        System.out.println("-".repeat(30));
        System.out.println("Starting A* Algorithm");
        System.out.println("Start Node: " + start);
        System.out.println("Goal Node: " + goal);

        gScore.put(start, 0.0);
        fScore.put(start, heuristic(start, goal));

        // Main loop for the A* algorithm
        while (!priorityNodes.isEmpty()) {
            Node currentNode = priorityNodes.poll();
            System.out.println("Current Node: " + currentNode);

            // Check if the goal node has been reached
            if (currentNode.equals(goal)) {
                System.out.println("Goal node reached");
                System.out.println("-".repeat(30));
                return createPath(lastNode, currentNode);
            }

            // Evaluate each neighbor of the current node
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
                System.out.println("Evaluating neighbor: " + neighbor + " with gScore: " + estimatedGScore);
                
                // If a shorter path to the neighbor is found
                if (estimatedGScore < gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    lastNode.put(neighbor, currentNode);
                    gScore.put(neighbor, estimatedGScore);
                    fScore.put(neighbor, estimatedGScore + heuristic(neighbor, goal));
                    
                    // Add the neighbor to the priority queue if not already present
                    if (!priorityNodes.contains(neighbor)) {
                        priorityNodes.add(neighbor);
                        System.out.println("Added neighbor to priority queue: " + neighbor);
                    }
                }
            }
        }
        System.out.println("No path found to the goal node.");

        return Collections.emptyList();
    }

    /**
     * Heuristic function to estimate the distance between two nodes.
     * 
     * @param node1 The first node.
     * @param node2 The second node.
     * @return The estimated distance between the two nodes.
     */
    private static double heuristic(Node node1, Node node2) {
        return calculateDistance(node1, node2);
    }

    /**
     * Reconstructs the path from the start node to the goal node.
     * 
     * @param lastNode A map of nodes and their predecessors.
     * @param currentNode The current node being evaluated.
     * @return A list of nodes representing the path from start to goal.
     */
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

    /**
     * Calculates the distance between two nodes using the 
     * Haversine formula, a heuristic computation for A*..
     * 
     * @param node1 The first node.
     * @param node2 The second node.
     * @return The distance between the two nodes in kilometers.
     */
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