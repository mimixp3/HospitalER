import java.util.ArrayList;
import java.util.HashMap; //For Availability, nodetype and adjacency list
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List; 
import java.util.Set;


public class ResourceGraph {
    private HashMap<String, List<String>> adjacencyList;
    private HashMap<String, Boolean> availability;
    private HashMap<String, String> nodeType;

    public ResourceGraph() {
        this.adjacencyList = new HashMap<>();
        this.availability = new HashMap<>();
        this.nodeType = new HashMap<>();
    }

    // O(1)
    public void addNode(String id, String type) {
        adjacencyList.put(id, new ArrayList<>());
        availability.put(id, true);
        nodeType.put(id, type);
    }

    // O(1) adding edges to connect
    public void addEdge(String a, String b) {
        adjacencyList.get(a).add(b);
        adjacencyList.get(b).add(a);
    }

    // O(degree) removing the edge between two 
    public void removeEdge(String a, String b) {
        if (adjacencyList.containsKey(a)) adjacencyList.get(a).remove(b);
        if (adjacencyList.containsKey(b)) adjacencyList.get(b).remove(a);
    }

    //bfs to find nearest available resource of given type - O(V+E)
    public String findAvailableResource(String type) {
        LinkedList<String> bfsQueue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        for (String node : adjacencyList.keySet()) {
            bfsQueue.offer(node);
        }

        while (!bfsQueue.isEmpty()) {
            String current = bfsQueue.poll();
            if (visited.contains(current)) continue;
            visited.add(current);

            if (nodeType.getOrDefault(current, "").equals(type) && availability.getOrDefault(current, false)) {
                return current;
            }

            for (String neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    bfsQueue.offer(neighbor);
                }
            }
        }
        return null;
    }

    //assign the patient with resources
    public void assignResource(String patientId, String resourceId) {
        addEdge(patientId, resourceId);
        availability.put(resourceId, false);
    }

    //release upon discharge
    public void releaseResource(String patientId, String resourceId) {
        removeEdge(patientId, resourceId);
        availability.put(resourceId, true);
    }

    //availability setting
    public void setAvailable(String id, boolean val) {
        availability.put(id, val);
    }

    //checks if available
    public boolean isAvailable(String id) {
        return availability.getOrDefault(id, false);
    }

    //type return for node
    public String getType(String id) {
        return nodeType.getOrDefault(id, "unknown");
    }
    //get neighbours adjacent
    public List<String> getNeighbors(String id) {
        return adjacencyList.getOrDefault(id, new ArrayList<>());
    }

    //get all nodes
    public Set<String> getAllNodes() {
        return adjacencyList.keySet();
    }

    //count available doctors this helps for the BFS
    public int countAvailableDoctors(){
        return (int) availability.entrySet().stream()
            .filter(e -> e.getValue() && nodeType.getOrDefault(e.getKey(), "").equals("doctor"))
            .count();
    }

    //count available rooms this helps for the BFS
    public int countAvailableRooms(){
        return (int) availability.entrySet().stream()
            .filter(e -> e.getValue() && nodeType.getOrDefault(e.getKey(), "").equals("room"))
            .count();
    }
}
