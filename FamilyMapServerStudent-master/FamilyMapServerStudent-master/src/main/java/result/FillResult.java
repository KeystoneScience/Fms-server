package result;

/**
 * Fail and success messages from the fillServer acting on the
 * fill request
 */
public class FillResult extends ParentResult{
    private int numPeople;
    private int EVENTS_PER_PERSON = 3;
    private int numEvents;
    private String userPerson;

    public String getUserPerson() {
        return userPerson;
    }

    public void setUserPerson(String userPerson) {
        this.userPerson = userPerson;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numGenerations) {
        int numPeople = (int)Math.pow(2,numGenerations+1)-1;
        numEvents = numPeople*EVENTS_PER_PERSON;
        this.numPeople = numPeople;
    }

    /** constructs the fill result object*/
    public FillResult(){};

    /**
     * fill was successful response string
     * @return success string message
     */
    public String successResponse(){
        return null;
    }
}
