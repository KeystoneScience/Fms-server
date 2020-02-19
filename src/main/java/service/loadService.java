package service;

import result.loadResult;
import request.loadRequest;

/**
 * serves the load request.
 */
public class loadService {
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private boolean success;


    /**
     * function for serving the load request
     * @param lr, a load request
     * @return the fail or success statement.
     */
    public loadResult load(loadRequest lr){
        loadResult lR = new loadResult();
        return lR;
    }
}
