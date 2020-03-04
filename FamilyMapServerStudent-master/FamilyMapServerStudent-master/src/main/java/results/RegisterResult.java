package results;


/**
 * The function of this class is to hold the data of the return from a register
 * request.
 */
public class RegisterResult extends ParentResult {

    /** authentication token for the User */
    private String authToken;
    /** Username associated with the request  */
    private String userName;
    /** the identification of the Person object linked to the User*/
    private String personID;



    /**
     * Function to return the successful response.
     * (More to add here later)
     * @return type string, returns response
     */
    public String successfulResponse(){
        //here is where i will put the information found in the specs:
        //        "authToken": "cf7a368f", // Non-empty auth token string
        //                "userName": "susan", // User name passed in with request
        //                "PersonID": "39f9fe46" // Non-empty string containing the Person ID of the
        //// User’s generated Person object
        //"success":"true" // Boolean identifier

        //I will construct this string with a string builder, and return it
        return null;
    }


    public RegisterResult(){
        super();

    }
    public RegisterResult(String message, String authToken, String userName, String personID) {
        super(message);
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public String getauthToken() {
        return authToken;
    }

    public void setauthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getpersonID() {
        return personID;
    }

    public void setpersonID(String personID) {
        this.personID = personID;
    }

    public RegisterResult(String message) {
        super(message);
    }
}
