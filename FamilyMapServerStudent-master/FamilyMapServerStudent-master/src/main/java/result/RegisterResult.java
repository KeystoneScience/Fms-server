package result;


/**
 * The function of this class is to hold the data of the return from a register
 * request.
 */
public class RegisterResult extends ParentResult {

    /** authentication token for the User */
    private String AuthToken;
    /** Username associated with the request  */
    private String UserName;
    /** the identification of the Person object linked to the User*/
    private String PersonId;


    /**
     * Function to return the successful response.
     * (More to add here later)
     * @return type string, returns response
     */
    public String successfulResponse(){
        //here is where i will put the information found in the specs:
        //        "AuthToken": "cf7a368f", // Non-empty auth token string
        //                "UserName": "susan", // User name passed in with request
        //                "PersonID": "39f9fe46" // Non-empty string containing the Person ID of the
        //// User’s generated Person object
        //"success":"true" // Boolean identifier

        //I will construct this string with a string builder, and return it
        return null;
    }


    public RegisterResult(){
        super();

    }
    public RegisterResult(String message, String AuthToken, String UserName, String PersonId) {
        super(message);
        this.AuthToken = AuthToken;
        this.UserName = UserName;
        this.PersonId = PersonId;
    }

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String AuthToken) {
        this.AuthToken = AuthToken;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPersonId() {
        return PersonId;
    }

    public void setPersonId(String PersonId) {
        this.PersonId = PersonId;
    }

    public RegisterResult(String message) {
        super(message);
    }
}
