import java.time.Duration;
import java.time.LocalTime;

public class Patient {

    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String condition;
    private int severityScore;
    private String insurance;
    private String bloodType;
    private String allergies;
    private String contact;
    private LocalTime arrivalTime;
    private LocalTime treatmentStartTime;
    private String doctorId;
    private String roomId;
    private boolean isAssigned;

    public Patient(String patientId, String name, int age, String gender, String condition, int severityScore, String bloodType, String allergies, String contact, String insurance){
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.condition = condition;
        this.severityScore = severityScore;
        this.insurance = insurance;
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.contact = contact;
        this.arrivalTime = LocalTime.now();
        this.doctorId = "Unassigned";
        this.roomId = "Waiting";
        this.isAssigned = false;
    }

    public String getPatientId(){
        return patientId;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }
    public String getCondition(){
        return condition;
    }
    public int getSeverityScore(){
        return severityScore;
    }
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }
    public LocalTime getTreatmentStartTime(){
        return treatmentStartTime;
    }
    public String getDoctorId(){
        return doctorId;
    }
    public String getRoomId() {
        return roomId;
    }
    public boolean isAssigned() {
        return isAssigned;
    }

    public void setSeverityScore(int s){
        this.severityScore = s;
    }
    public void setDoctorId(String d){
        this.doctorId = d;
    }
    public void setRoomId(String r){
        this.roomId = r;
    }
    public void setTreatmentStartTime(LocalTime t){
        this.treatmentStartTime = t;
    }
    public void setAssigned(boolean b){
        this.isAssigned = b;
    }
    public void setAllergies(String a){
        this.allergies = a;
    }


    public String getSeverityLabel() {
        if (severityScore >= 9) return "C";
        else if (severityScore >= 7) return "H";
        else if (severityScore >= 4) return "M";
        else return "L";
    }

    public String getSeverityColor() {
        if (severityScore >= 9) return "#E74C3C";
        else if (severityScore >= 7) return "#E67E22";
        else if (severityScore >= 4) return "#F1C40F";
        else return "#87d450";
    }

    public long getWaitTimeMinutes() {
        if (treatmentStartTime == null) return 0;
        return Duration.between(arrivalTime, treatmentStartTime).toMinutes();
    }


    public String getInsurance(){
        return insurance;
    }
    public String getBloodType(){
        return bloodType;
    }
    public String getAllergies(){
        return allergies;
    }
    public String getContact(){
        return contact;
    }

    @Override
    public String toString() {
        return "[" + patientId + "] " + name + ", " + age + gender+ " , Severity: " + severityScore + "/10 (" + getSeverityLabel() + ")"
            + " , " + condition+ " , " + (isAssigned ? doctorId + " / " + roomId : "Unassigned");
    }
}