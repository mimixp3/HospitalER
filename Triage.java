import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Triage {
    private PriorityQueue<Patient> triageHeap;

    public Triage() {
        //comparator: higher severity goes first (max heap)
        this.triageHeap = new PriorityQueue<>((p1,p2)-> p2.getSeverityScore()-p1.getSeverityScore());
    }

    
    //O(log n)
    public void admitPatient(Patient p){
        triageHeap.offer(p);
    }

    //O(log n)
    public Patient getNextPatient(){
        if (triageHeap.isEmpty()){
            return null;
        }
        return triageHeap.poll();
    }

    //O(1)
    public Patient peekNextPatient() {
        return triageHeap.peek();
    }

    //returns all patients sorted by severity for ui display
    public List<Patient> getAllPatientsSorted(){
        List<Patient> sorted = new ArrayList<>(triageHeap);
        sorted.sort((p1, p2)->p2.getSeverityScore()-p1.getSeverityScore());
        return sorted;
    }


    public boolean isEmpty(){
        return triageHeap.isEmpty();
    }
    public int getSize(){
        return triageHeap.size();
    }

    /*We use here .stream() to turn the priority queue
    into a stream which is like a pipeline that
    lets you process each element one by one
    
    then the .filter() goes through each patient p and only
    keep the ones whose severity is between a certain value*/
    public int countCritical() {
        return (int) triageHeap.stream().filter(p -> p.getSeverityScore() >= 9).count();
    }
    public int countHigh() {
        return (int) triageHeap.stream().filter(p -> p.getSeverityScore() >= 7 && p.getSeverityScore() < 9).count();
    }
    public int countModerate() {
        return (int) triageHeap.stream().filter(p -> p.getSeverityScore() >= 4 && p.getSeverityScore() < 7).count();
    }
    public int countLow() {
        return (int) triageHeap.stream().filter(p -> p.getSeverityScore() < 4).count();
    }
}