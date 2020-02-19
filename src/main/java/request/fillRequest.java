package request;

/**
 * Data structure for a fill request. contains the information for number of generations
 * to be filled along with the username
 */
public class fillRequest {
    private String userID;
    private int numGenerations;

    public fillRequest(String userID) {
        this.userID = userID;
    }

    /**
     * data object constructor
     * @param userID unique string specifying the user
     * @param numGenerations number of generations to generate
     */
    public fillRequest(String userID, int numGenerations) {
        this.userID = userID;
        this.numGenerations = numGenerations;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getNumGenerations() {
        return numGenerations;
    }

    public void setNumGenerations(int numGenerations) {
        this.numGenerations = numGenerations;
    }
}
