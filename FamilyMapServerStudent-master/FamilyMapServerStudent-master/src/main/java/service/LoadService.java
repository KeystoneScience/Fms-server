package service;

import request.LoadRequest;
import result.LoadResult;

/**
 * serves the load request.
 */
public class LoadService {
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
    public LoadResult load(LoadRequest lr){
        LoadResult lR = new LoadResult();
        return lR;
    }
}
