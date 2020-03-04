package service;

import dao.*;
import model.Event;
import model.Person;
import model.User;
import requests.LoadRequest;
import results.LoadResult;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * serves the load request.
 */
public class LoadService {
    private Database db = new Database();
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

        try{
            db.openConnection();

            // clear all data from tables
            db.clearTables();

            UserDao ud = new UserDao(db.getConnection());
            EventDao ed = new EventDao(db.getConnection());
            PersonDao pd = new PersonDao(db.getConnection());

            //Get information from the login request
            ArrayList<User> users = lr.getUsers();
            ArrayList<Event> events = lr.getEvents();
            ArrayList<Person> persons = lr.getPeople();



            //Itterate through each of the datasets and add them to the database
            for (User user : users) {
                ud.insertUser(user);
            }
            for (Event event : events) {
                ed.insertEvent(event);
            }
            for (Person person : persons) {
                pd.insertPerson(person);
            }


            db.closeConnection(true);

            lR.setSuccess(true);
            String message =  "Successfully added "+users.size()
                    + " users, "+persons.size()
                    +" persons, and "+events.size()
                    +" events to the database.";
            lR.setMessage(message);




        } catch (DataAccessException | SQLException e) {
            lR.setSuccess(false);
            lR.setMessage(e.getMessage());
           try{
               db.closeConnection(false);
           } catch (DataAccessException ex) {
               lR.setMessage(ex.getMessage());
           }
        }
        return lR;
    }
}
