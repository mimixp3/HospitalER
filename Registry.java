import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Registry {
    private HashMap<String, Patient> records;
    private ArrayList<Patient> dischargedPatients = new ArrayList<>();
    private int idCounter;

    public Registry() {
        this.records = new HashMap<>();
        this.idCounter = 1;
    }

    //generates unique id like P001, P002
    //P is just the literal letter P that appears at the start of every ID
    //%03d part is formatting it means give me an integer that is always 3 digits long
    public String generateId() {
        return String.format("P%03d", idCounter++);
    }

    //O(1) average
    public void register(Patient p) {
        records.put(p.getPatientId(), p);
    }

    //O(1) average
    public Patient lookup(String patientId) {
        return records.get(patientId);
    }

    //O(1) average assigns record updated
    public void update(Patient p) {
        if (records.containsKey(p.getPatientId())) {
            records.put(p.getPatientId(), p);
        }
    }

    //O(1) average, it discharges the patient by removing using id
    public void discharge(String patientId) {
        Patient p = records.get(patientId);
        if (p != null) {
            dischargedPatients.add(p); //saving the records before removing the patient
        }
        records.remove(patientId);
    }

    //O(1) average lets say patients id exists it returns it along with the record, otherwise empty
    public boolean contains(String patientId) {
        return records.containsKey(patientId);
    }

    //takes values from hash map and puts in array list
    public List<Patient> getAllPatients(){
        return new ArrayList<>(records.values());
    }

    //will help us for getting discharged patients stats in ERSystem with stats.loadFromPatients(registry.getDischargedPatients());
    public List<Patient> getDischargedPatients() {
        return dischargedPatients;
    }

    public int getSize() {
        return records.size();
    }
    public boolean isEmpty() {
        return records.isEmpty();
    }
}