package Model;

public class Inspection {
    private String TrackingNum;
    private int[]InspectionDate;
    private String InspType;
    private int NumCritical;
    private int NumNonCritical;
    private String HazardRating;
    private String ViolLump;
    private String colour;

    public Inspection(String trackingNum) {
        this.TrackingNum=trackingNum;
        this.InspectionDate=new int[8];
        this.InspType="";
        this.NumCritical=0;
        this.NumNonCritical=0;
        this.HazardRating="";
        this.ViolLump="";
    }

    public Inspection(String trackingNum, int[] inspectionDate, String inspType, int numCritical, int numNonCritical, String hazardRating, String violLump) {
        TrackingNum = trackingNum;
        InspectionDate = inspectionDate;
        InspType = inspType;
        NumCritical = numCritical;
        NumNonCritical = numNonCritical;
        HazardRating = hazardRating;
        ViolLump = violLump;
    }

    public int[] getInspectionDate() {
        return InspectionDate;
    }

    public void setInspectionDate(int[] inspectionDate) {
        InspectionDate = inspectionDate;
    }

    public String getInspType() {
        return InspType;
    }

    public void setInspType(String inspType) {
        InspType = inspType;
    }

    public int getNumCritical() {
        return NumCritical;
    }

    public void setNumCritical(int numCritical) {
        NumCritical = numCritical;
    }

    public int getNumNonCritical() {
        return NumNonCritical;
    }

    public void setNumNonCritical(int numNonCritical) {
        NumNonCritical = numNonCritical;
    }

    public String getHazardRating() {
        return HazardRating;
    }

    public void setHazardRating(String hazardRating) {
        HazardRating = hazardRating;
    }

    public String getViolLump() {
        return ViolLump;
    }

    public void setViolLump(String violLump) {
        ViolLump = violLump;
    }
    public void setColour(){
        if(this.HazardRating=="low")
        {
            this.colour="blue";
        }
        else if(this.HazardRating=="Medium")
        {
            this.colour="yellow";
        }
        else if(this.HazardRating=="high")
        {
            this.colour="red";
        }
    }
}
