package service;

import result.ClearResult;

/**
 * serves to clear the entire data base.
 */
public class clearService {
    private boolean success;

    /**
     * the clear service
     * @return the results of the clear
     */
    public ClearResult clear(){
        ClearResult cR = new ClearResult();

        return cR;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
