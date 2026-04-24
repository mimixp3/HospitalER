public class ERSystem {
    private Triage triage;
    private Registry registry;
    private RegQueue regQueue;
    private ResourceGraph resourceGraph;
    private Stats stats;

    public ERSystem() {
        triage = new Triage();
        registry = new Registry();
        regQueue = new RegQueue();
        resourceGraph = new ResourceGraph();
        stats = new Stats();
        initializeHospitalResources();
    }


    private void initializeHospitalResources() {
        for (int i = 1; i <= 20; i++) { //20 docs
            resourceGraph.addNode("Dr. Specialist " + i, "doctor");
        }
        for (int i = 1; i <= 30;i++) { //30 rooms
            resourceGraph.addNode("Room " + i, "room");
        }
    }

    //register patient into registry and registration queue
    public Patient registerPatient(String name, int age, String gender, String condition, int severity,String bloodType, String allergies, String contact, String insurance) {
        String id = registry.generateId();
        Patient p = new Patient(id, name, age, gender, condition, severity, bloodType, allergies, contact, insurance);
        registry.register(p);
        regQueue.enqueue(p);
        return p;
    }
    
    //move next patient from registration queue into triage heap
    public Patient processRegistration() {
        Patient p = regQueue.dequeue();
        if (p != null) {
            triage.admitPatient(p);
            assignNextPatient();
        }
        return p;
    }

    //assign doctor and room to highest priority patient using bfs
    public boolean assignNextPatient() {
        boolean assigned = false;
        while (true) {
            Patient p = triage.peekNextPatient();
            if (p == null) break;

            String doctor = resourceGraph.findAvailableResource("doctor");
            String room = resourceGraph.findAvailableResource("room");

            if (doctor == null || room == null) break;

            triage.getNextPatient();
            resourceGraph.addNode(p.getPatientId(), "patient");
            resourceGraph.assignResource(p.getPatientId(), doctor);
            resourceGraph.assignResource(p.getPatientId(), room);

            p.setDoctorId(doctor);
            p.setRoomId(room);
            p.setAssigned(true);
            p.setTreatmentStartTime(java.time.LocalTime.now());
            registry.update(p);

            assigned = true;
        }

        return assigned;
    }

    //discharge patient and release their resources
    public void dischargePatient(String patientId) {
        Patient p = registry.lookup(patientId);
        if (p != null) {
            //free up the doctor and room
            resourceGraph.releaseResource(patientId, p.getDoctorId());
            resourceGraph.releaseResource(patientId, p.getRoomId());
            
            //mark as discharged in the registry
            registry.discharge(patientId);
            //refresh stats using the updated registry
            stats.loadFromPatients(registry.getDischargedPatients());

            //try to pull the next person from triage
            assignNextPatient();
        }
    }

    public Triage getTriage() {
        return triage;
    }
    public Registry getRegistry(){
        return registry;
    }
    public RegQueue getRegQueue(){
        return regQueue;
    }
    public ResourceGraph getResourceGraph(){
        return resourceGraph;
    }
    public Stats getStats() {
        return stats;

    }
}