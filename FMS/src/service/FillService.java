package service;

import request.FillRequest;
import result.FillResult;

/**
 * Serves a fill request made and returns the success or fail message
 */
public class FillService
{
    boolean success;

    public FillService(){};

    /**
     * This function serves the fill request
     * @param fr fill request
     * @return Fill Result
     */
    public FillResult fill (FillRequest fr){
        FillResult fR = new FillResult();

        return fR;
    };
}
