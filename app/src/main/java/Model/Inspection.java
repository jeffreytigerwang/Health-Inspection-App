package Model;

public class Inspection {
    private String TrackingNum;
    private int[] InspectionDate;
    private String InspType;
    private int NumCritical;
    private int NumNonCritical;
    private String[] CViolLump;            //Critical ViolLUmp
    //private String[] NonViolLump;          //Non Critical ViolLump
    private String HazardRating;
    private String colour;

    public Inspection(String trackingNum) {
        this.TrackingNum=trackingNum;
        this.InspectionDate=new int[3];
        this.InspType="";
        this.NumCritical=0;
        this.NumNonCritical=0;
        this.HazardRating="";

    }


    public Inspection(String trackingNum, int fulldate, String inspType, int numCritical, int numNonCritical, String hazardRating, String wholecviolLump /*,String[] nonviolLump*/) {
        TrackingNum = trackingNum;
        InspectionDate = parseDate(fulldate);
        InspType = inspType;
        NumCritical = numCritical;
        NumNonCritical = numNonCritical;
        HazardRating = hazardRating;
        CViolLump = paseviolLump(wholecviolLump);
        //  NonViolLump = nonviolLump;
        setColour();

    }

    private int[] parseDate(int fulldate) {
        String year = String.valueOf(fulldate).substring(0,4);
        String month = String.valueOf(fulldate).substring(4,6);
        String day = String.valueOf(fulldate).substring(6,8);
        int[] parsedDate = {Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)};

        return parsedDate;
    }

    private String[] paseviolLump(String wholecviolLump) {

        return  wholecviolLump.replace(",",", ").split("\\|");
    }


    public String getTrackingNum() {
        return TrackingNum;
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

    public String[] getCViolLump() {
        return CViolLump;
    }

    public void setCViolLump(String[] violLump) {
        CViolLump = violLump;
    }

  /*  public String[] getNonViolLump() {
        return NonViolLump;
    }

    public void setNonViolLump(String[] violLump) {
        NonViolLump = violLump;
    }
*/

    public void setColour(){
        if(this.HazardRating.equals("Low"))
        {
            this.colour="blue";
        }
        else if(this.HazardRating.equals("Moderate"))
        {
            this.colour="yellow";
        }
        else if(this.HazardRating.equals("High"))
        {
            this.colour="red";
        }
    }
    public String getColour(){
        return colour;
    }
}
