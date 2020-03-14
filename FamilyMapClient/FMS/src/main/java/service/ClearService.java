package service;

import dao.DataAccessException;
import dao.Database;
import results.ClearResult;

/**
 * serves to clear the entire data base.
 */
public class ClearService {
    private boolean success;
    private Database db = new Database();

    /**
     * the clear service
     * @return the results of the clear
     */
    public ClearResult clear(){
        ClearResult cR = new ClearResult();
        try {
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);

            cR.setSuccess(true);
            cR.setMessage("clear succeeded.");

        } catch (DataAccessException e) {
            cR.setSuccess(false);

            cR.setMessage(e.getMessage());
            try{
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
        }
        return cR;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
