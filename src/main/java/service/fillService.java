package service;

import request.fillRequest;
import result.FillResult;

/**
 * Serves a fill request made and returns the success or fail message
 */
public class fillService
{
    boolean success;

    public fillService(){};

    /**
     * This function serves the fill request
     * @param fr fill request
     * @return Fill Result
     */
    public FillResult fill (fillRequest fr){
        FillResult fR = new FillResult();

        return fR;
    };
}
