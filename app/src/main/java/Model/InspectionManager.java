package Model;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Data model: Store a collection of inspectionmanager.
 */



public class InspectionManager implements Iterable <Inspection> {
    private List<Inspection> inspections = new ArrayList<>();
    private static InspectionManager instance;

    private InspectionManager() {
        // Nothing: ensure this is a singleton.
    }

    public static InspectionManager getInstance() {
        if (instance == null) {
            instance = new InspectionManager();
        }
        return instance;
    }



    public List getList(){
        return inspections;
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


    @NonNull
    @Override
    public Iterator<Inspection> iterator() {
        return inspections.iterator();
    }
}
