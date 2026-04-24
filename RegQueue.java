import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RegQueue {

    private LinkedList<Patient> queue;
    public RegQueue() {
        this.queue = new LinkedList<>();
    }

    // O(1), to add patient in line for waiting
    public void enqueue(Patient p) {
        queue.offer(p);
    }


    // O(1), to remove patient from WAITING and they start treatment
    public Patient dequeue() {
        if (queue.isEmpty()) return null;
        return queue.poll();
    }

    // O(1)
    public Patient peek() {
        return queue.peek();
    }

    public List<Patient> getAllWaiting() {
        return new ArrayList<>(queue);
    }

    public boolean isEmpty() { return queue.isEmpty(); }
    public int getSize() { return queue.size(); }
}