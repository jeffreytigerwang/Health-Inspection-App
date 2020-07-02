package Model;


import java.util.ArrayList;
import java.util.List;

/**
 * Data model: Store a collection of inspectionmanager.
 */



public class InspectionManager {
    private List<Inspection> inspections = new ArrayList<>();
    private static InspectionManager instance;


    public static InspectionManager getInstance() {
        if (instance == null) {
            instance = new InspectionManager();
        }
        return instance;
    }

    private InspectionManager() {
        // Nothing: ensure this is a singleton.
    }

    public void add(Inspection inspection) {
        inspections.add(inspection);
    }

    public void remove(Inspection inspection) {
        inspections.remove(inspection);
    }
    public Inspection get(int i) {
        return inspections.get(i);
    }

    public int getNumInspection() {
        return inspections.size();
    }













}
