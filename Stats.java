import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stats {
    private List<Long> waitTimes;
    public Stats() {
        this.waitTimes = new ArrayList<>();
    }

    
    //clear the internal list and pull fresh data from your p object
    public void loadFromPatients(List<Patient> patients) {
        waitTimes.clear();
        for (Patient p : patients) {
            long wt = p.getWaitTimeMinutes();
            //only count patients who actually have a wait time
            if (p.getTreatmentStartTime() != null) {
                waitTimes.add(wt);
            }

        }
    }


    //calculation methods

    //calculates average time
    public double getMean() {
        if (waitTimes.isEmpty()) return 0;
        long sum = 0;

        for (long t : waitTimes){
            sum += t;
        }
        return (double) sum / waitTimes.size();
    }


    //standard deviation, it basically calculates how much variance/how much u moved from the avg time
    public double getStdDeviation(){
        if (waitTimes.isEmpty()) return 0;
        double mean = getMean();
        double variance = 0;
        for (long t : waitTimes){
            variance += Math.pow(t - mean, 2);
        }
        return Math.sqrt(variance / waitTimes.size());
    }

    public long getMin() {
        if (waitTimes.isEmpty()) return 0;
        return Collections.min(waitTimes);
    }

    public long getMax() {
        if (waitTimes.isEmpty()) return 0;
        return Collections.max(waitTimes);
    }

    public int getTotalPatients() { return waitTimes.size(); }

    //severity logic for table
    public double getAvgBySeverity(List<Patient> allPatients, String severityLabel) {
        long sum = 0; //to get avg sum
        int count = 0; //to get number of items for avg
        for (Patient p : allPatients) {
            if (p.getSeverityLabel().equalsIgnoreCase(severityLabel)) {
                sum += p.getWaitTimeMinutes();
                count++;
            }
        }
        return count == 0 ? 0 : (double) sum / count;
    }


    
    public long getMaxBySeverity(List<Patient> allPatients, String severityLabel) {
        long max = 0;
        for (Patient p : allPatients) {
            if (p.getSeverityLabel().equalsIgnoreCase(severityLabel)) {
                max = Math.max(max, p.getWaitTimeMinutes());
            }
        }
        return max;
    }
}