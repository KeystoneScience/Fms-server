package requests;

/**
 * Data structure for a fill request. contains the information for number of generations
 * to be filled along with the Username
 */
public class FillRequest {
    private String userName;
    private int numGenerations;

    public FillRequest(String userName) {
        this.userName = userName;
    }

    /**
     * data object constructor
     * @param userName unique string specifying the User
     * @param numGenerations number of generations to generate
     */
    public FillRequest(String userName, int numGenerations) {
        this.userName = userName;
        this.numGenerations = numGenerations;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public int getNumGenerations() {
        return numGenerations;
    }

    public void setNumGenerations(int numGenerations) {
        this.numGenerations = numGenerations;
    }
}
