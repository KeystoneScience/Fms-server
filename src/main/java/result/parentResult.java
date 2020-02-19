package result;


/**
 * The common inherited class for all result classes
 */
public class parentResult {
    /** contains the error message */
    protected String message;

    /** the marker of a succsessful result*/
    protected boolean success;

    public parentResult(){};

    /**
     * Function to return the error response.
     * (More to add here later)
     * @return type string, returns response
     */
    public String errorResponse (){

        return null;
    }

    public parentResult(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
