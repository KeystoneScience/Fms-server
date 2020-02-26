package request;

/**
 * Data structure for a fill request. contains the information for number of generations
 * to be filled along with the Username
 */
public class FillRequest {
    private String UserID;
    private int numGenerations;

    public FillRequest(String UserID) {
        this.UserID = UserID;
    }

    /**
     * data object constructor
     * @param UserID unique string specifying the User
     * @param numGenerations number of generations to generate
     */
    public FillRequest(String UserID, int numGenerations) {
        this.UserID = UserID;
        this.numGenerations = numGenerations;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public int getNumGenerations() {
        return numGenerations;
    }

    public void setNumGenerations(int numGenerations) {
        this.numGenerations = numGenerations;
    }
}
