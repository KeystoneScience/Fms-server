package service;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.AuthToken;
import model.Person;
import result.PersonResult;

import java.util.List;

/**
 * This class serves the Person request.
 */
public class PersonService {
    private boolean success;
    private Database db = new Database();


    /**
     * object constructor
     */
    public PersonService(){};

    /**
     * returns Person from specified PersonID and UserID
     * @param PersonID Unique identifier for the Person
     * @param authToken unique string specifying the User
     * @return success or error results of finding the Person
     */
    public PersonResult findPerson(String PersonID, String authToken){
        PersonResult pr = new PersonResult();
        try {

            //FIXME check that auth token does exist, use a authDAO function.
            db.openConnection();


            AuthTokenDao atd = new AuthTokenDao(db.getConnection());
            PersonDao pd = new PersonDao(db.getConnection());


            model.AuthToken at = atd.find(authToken);
            if(at == null){
                throw new DataAccessException("Error: AuthToken does not exist.");

            }
            Person person = pd.find(PersonID,at.getUsername());
            if (person == null){
                throw new DataAccessException("Error: Person with ID given does not exist.");
            }
            pr.fillPerson(person);

            pr.setSuccess(true);
            db.closeConnection(true);

        } catch (DataAccessException e) {
            pr.setSuccess(false);
            pr.setMessage(e.getMessage());
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                pr.setMessage(e.getMessage());
            }
        }

        return pr;
    }

    /**
     * returns a list of people linked to a given User.
     * @param AuthToken used to verify
     * @return list of people
     */
    public PersonResult Persons(String AuthToken){
        PersonResult pr = new PersonResult();
        try {

            //FIXME check that auth token does exist, use a authDAO function.
            db.openConnection();


            AuthTokenDao atd = new AuthTokenDao(db.getConnection());

            PersonDao pd = new PersonDao(db.getConnection());


            model.AuthToken at = atd.find(AuthToken);
            if(at == null){
                throw new DataAccessException("Error: Auth Token Incorrect.");
            }
            List<Person> persons = pd.findPersons(at.getUsername());

            pr.setPeople(persons);

            pr.setSuccess(true);
            db.closeConnection(true);

        } catch (DataAccessException e) {
            pr.setSuccess(false);
            pr.setMessage(e.getMessage());
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                pr.setMessage(e.getMessage());
            }
        }

        return pr;
    }

}
