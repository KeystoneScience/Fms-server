package result;


/**
 * The function of this class is to hold the data of the return from a register
 * request.
 */
public class RegisterResult extends parentResult {

    /** authentication token for the user */
    private String authToken;
    /** username associated with the request  */
    private String userName;
    /** the identification of the person object linked to the user*/
    private String personId;


    /**
     * Function to return the successful response.
     * (More to add here later)
     * @return type string, returns response
     */
    public String successfulResponse(){
        //here is where i will put the information found in the specs:
        //        "authToken": "cf7a368f", // Non-empty auth token string
        //                "userName": "susan", // User name passed in with request
        //                "personID": "39f9fe46" // Non-empty string containing the Person ID of the
        //// user’s generated Person object
        //"success":"true" // Boolean identifier

        //I will construct this string with a string builder, and return it
        return null;
    }


    public RegisterResult(){
        super();

    }
    public RegisterResult(String message, String authToken, String userName, String personId) {
        super(message);
        this.authToken = authToken;
        this.userName = userName;
        this.personId = personId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public RegisterResult(String message) {
        super(message);
    }
}
