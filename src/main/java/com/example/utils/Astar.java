package com.example.utils;

import com.example.model.Carte;
import com.example.model.Chemin;
import com.example.model.Intersection;
import com.example.model.Segment;
import javafx.util.Pair;

import java.util.*;

/**
 * Implémente l'algorithme A* pour le calcul du plus court chemin dans un graphe.
 */
public class Astar {

    static class Node
    {
        public Node(long id, double cost, double estimatedCost, Node parent) {
            this.id = id;
            this.cost = cost;
            this.estimatedCost = estimatedCost;
            this.parent = parent;
        }

        long id;
        double cost;
        double estimatedCost;
        Node parent;
    }

    /**
     * Calcule la distance du plus court chemin entre deux intersections sur une carte.
     *
     * @param carte la carte sur laquelle calculer la distance
     * @param debut l'intersection de départ
     * @param fin l'intersection d'arrivée
     * @return la distance du plus court chemin entre debut et fin en mètres
     */
    public static double calculDistance(Carte carte, Intersection debut, Intersection fin) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(node -> node.estimatedCost));
        HashSet<Long> openSetIds = new HashSet<>();
        HashMap<Long, Node> closedSet = new HashMap<>();

        // Create the start node
        Node startNode = new Node(debut.getId(), 0, heuristic(debut, fin), null);
        openSet.add(startNode);
        openSetIds.add(startNode.id);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            openSetIds.remove(current.id);

            // Goal reached, return the cost
            if (current.id == fin.getId()) {
                return current.cost;
            }

            // Explore neighbors
            ArrayList<Pair<Long, Double>> neighbors = carte.getNeighbors(current.id);
            if (neighbors != null) {
                for (Pair<Long, Double> neighbor : neighbors) {
                    Intersection neighborIntersection = carte.getListeIntersections().get(neighbor.getKey());
                    double tentativeG = current.cost + neighbor.getValue();

                    if (closedSet.containsKey(neighbor.getKey()) && tentativeG >= closedSet.get(neighbor.getKey()).cost) {
                        continue;
                    }

                    if (openSetIds.contains(neighbor.getKey())){
                        Node existing = getNodeFromOpenSet(neighbor.getKey(), openSet);
                        assert existing != null;
                        if (tentativeG < existing.cost) {
                            openSet.remove(existing);
                            openSetIds.remove(existing.id);
                        } else {
                            continue;
                        }
                    }
                    Node neighborNode = new Node(neighbor.getKey(), tentativeG, tentativeG + heuristic(neighborIntersection, fin), current);
                    openSet.add(neighborNode);
                    openSetIds.add(neighborNode.id);
                }
            }
            closedSet.put(current.id, current);
        }

        // No path found
        return -1;
    }

    /**
     * Calcule le plus court chemin entre deux intersections sur une carte.
     *
     * @param carte la carte sur laquelle calculer le chemin
     * @param debut l'intersection de départ
     * @param fin l'intersection d'arrivée
     * @return le plus court chemin entre debut et fin
     */
    public static Chemin calculChemin(Carte carte, Intersection debut, Intersection fin) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(node -> node.estimatedCost));
        HashSet<Long> openSetIds = new HashSet<>();
        HashMap<Long, Node> closedSet = new HashMap<>();

        // Create the start node
        Node startNode = new Node(debut.getId(), 0, heuristic(debut, fin), null);
        openSet.add(startNode);
        openSetIds.add(startNode.id);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            openSetIds.remove(current.id);

            // Goal reached, return the path
            if (current.id == fin.getId()) {
               return reconstructPath(carte, current);
            }

            // Explore neighbors
            ArrayList<Pair<Long, Double>> neighbors = carte.getNeighbors(current.id);
            if (neighbors != null) {
                for (Pair<Long, Double> neighbor : neighbors) {
                    Intersection neighborIntersection = carte.getListeIntersections().get(neighbor.getKey());
                    double tentativeG = current.cost + neighbor.getValue();

                    if (closedSet.containsKey(neighbor.getKey()) && tentativeG >= closedSet.get(neighbor.getKey()).cost) {
                        continue;
                    }

                    if (openSetIds.contains(neighbor.getKey())) {
                        Node existing = getNodeFromOpenSet(neighbor.getKey(), openSet);
                        assert existing != null;
                        if (tentativeG < existing.cost) {
                            openSet.remove(existing);
                            openSetIds.remove(existing.id);
                        } else {
                            continue;
                        }
                    }
                    Node neighborNode = new Node(neighbor.getKey(), tentativeG, tentativeG + heuristic(neighborIntersection, fin), current);
                    openSet.add(neighborNode);
                    openSetIds.add(neighborNode.id);
                }
            }
            closedSet.put(current.id, current);
        }
        return null;
    }

    /**
     * Reconstruit le chemin à partir des nœuds parents jusqu'à l'origine.
     *
     * @param carte  La carte contenant les intersections et les segments.
     * @param node   Le nœud final.
     * @return le chemin reconstruit à partir du nœud final jusqu'à l'origine.
     */
    public static Chemin reconstructPath(Carte carte, Node node) {
        if(node == null) {
            return null;
        }
        Chemin path = new Chemin();
        Node current = node;
        while (current.parent != null) {
            Segment segment = carte.getListeSegments().get(new Pair<>(current.parent.id, current.id));
            path.addSegmentInFirstPosition(segment);
            current = current.parent;
        }
        return path;
    }

    /**
     * Calcule la distance heuristique entre deux intersections.
     *
     * @param origin      L'intersection de départ.
     * @param destination L'intersection d'arrivée.
     * @return la distance heuristique entre origin et destination.
     */
    private static double heuristic(Intersection origin, Intersection destination) {
        double x1 = origin.getLatitude();
        double y1 = origin.getLongitude();

        double x2 = destination.getLatitude();
        double y2 = destination.getLongitude();

        return Distance.haversine(x1, y1, x2, y2);
    }

    /**
     * Obtient un nœud spécifique à partir de l'ensemble ouvert (openSet) par son identifiant.
     *
     * @param nodeId   L'identifiant du nœud.
     * @param openSet  L'ensemble de nœuds ouvert.
     * @return le nœud correspondant à nodeId s'il est présent dans l'ensemble ouvert, sinon null.
     */
    private static Node getNodeFromOpenSet(long nodeId, PriorityQueue<Node> openSet) {
        for (Node node : openSet) {
            if (node.id == nodeId) {
                return node;
            }
        }
        return null;
    }
}
