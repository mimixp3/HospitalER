import java.util.ArrayList;
import java.util.List;

public class RegQueue {
    private static class Node {
        Patient data;
        Node next; //regular singly linked list
        
        Node(Patient data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public RegQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    //O(1), to add patient in line for waiting
    public void enqueue(Patient p) {
        Node newNode = new Node(p);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    //O(1) dequeue
    public Patient dequeue() {
        if (head == null) return null;
        Patient data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return data;
    }    

    //O(1)
    public Patient peek(){
        if (head == null) return null;
        return head.data;
    }

    public List<Patient> getAllWaiting() {
        List<Patient> list = new ArrayList<>();
        Node current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    public boolean isEmpty(){
        return size == 0;
    }
    public int getSize(){
        return size;
    }
}
