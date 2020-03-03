package service;

import dao.*;
import model.Event;
import model.Person;
import model.User;
import request.FillRequest;
import result.FillResult;

import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

/**
 * Serves a fill request made and returns the success or fail message
 */
public class FillService
{
    //private boolean[] changeLastName = [true];
    private Database db = new Database();
    private PersonDao pdao;// = new PersonDao();
    private EventDao edao;// = new EventDao();
    private UserDao udao;// = new UserDao();

    boolean success;

    public FillService(){
    };

    private void openObjects(){
        try {
             pdao = new PersonDao(db.getConnection());
             udao = new UserDao(db.getConnection());
             edao = new EventDao(db.getConnection());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * This function serves the fill request
     * @param fr fill request
     * @return Fill Result
     */
    public FillResult fill (FillRequest fr){
        FillResult fR = new FillResult();
        try {
            db.openConnection();

            openObjects();

            User user = udao.find(fr.getuserName());

            if (user == null){ //Checks if user exists
                throw new DataAccessException("Username is not in the database.");
            }

            pdao.removePerson(user);
            edao.removeEvent(user);

            Person userPerson = new Person(user.getPerson_id(),user.getId(),user.getFirst_name(),user.getLast_name(),user.getGender(),"","",""); // Construct person object based on user
            pdao.insertPerson(userPerson);
            fR.setUserPerson(userPerson.getPerson_id());
            edao.generateBirth(userPerson,1999);



            GenerateGenerations(userPerson, fr.getNumGenerations());
            fR.setSuccess(true);
            fR.setNumPeople(fr.getNumGenerations());
            db.closeConnection(true);


        }catch (DataAccessException | SQLException e) {
            fR.setSuccess(false);
            fR.setMessage(e.getMessage());
            try{
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                fR.setMessage(ex.getMessage());
            }
        }

        return fR;
    }

    /**
     * Recursive Algorithm to populate generations
     * @param child
     * @param numGenerationsToGenerate
     * @throws DataAccessException
     */
    private void GenerateGenerations(Person child, int numGenerationsToGenerate) throws DataAccessException {
        Person mom = new Person();
        Person dad = new Person();
        mom.setAssociated_Username(child.getAssociated_Username());
        dad.setAssociated_Username(child.getAssociated_Username());


        dad.setGender("m");
        mom.setGender("f");

        mom.setPerson_id(UUID.randomUUID().toString());
        dad.setPerson_id(UUID.randomUUID().toString());

        mom.setSpouse_id(dad.getPerson_id());
        dad.setSpouse_id(mom.getPerson_id());


        child.setFather_id(dad.getPerson_id());
        child.setMother_id(mom.getPerson_id());

        Random r = new Random();




        Event childBirth = edao.findBirth(child.getPerson_id());



        //Generates reasonable birth and death based off of child birth.
        int birthYearMom = childBirth.getYear() - 23 - r.nextInt(10);
        int deathYearMom = childBirth.getYear() + 10 + r.nextInt(30);
        int birthYearDad = childBirth.getYear() - 23 - r.nextInt(10);
        int deathYearDad = childBirth.getYear() + 10 + r.nextInt(30);



        try {
            dad.setLast_name(child.getLast_name());
            mom.setLast_name(pdao.randomLastName());

            dad.setFirst_name(pdao.randomMaleName());
            mom.setFirst_name(pdao.randomFemaleName());

            pdao.updatePerson(child, child.getPerson_id());
            pdao.insertPerson(mom);
            pdao.insertPerson(dad);

            edao.generateBirth(mom,birthYearMom);
            edao.generateBirth(dad,birthYearDad);

            edao.generateDeath(mom,deathYearMom);
            edao.generateDeath(dad,deathYearDad);

            int marriageYear = childBirth.getYear() - 1 - r.nextInt(5);

            MarriageOfTwo(mom.getPerson_id(),dad.getPerson_id(),child.getPerson_id());

        } catch (SQLException e) {
            e.printStackTrace();
        }


        numGenerationsToGenerate = numGenerationsToGenerate-1;
        if(numGenerationsToGenerate > 0) {
            GenerateGenerations(mom, numGenerationsToGenerate);
            GenerateGenerations(dad, numGenerationsToGenerate);
        }


    }

    private void MarriageOfTwo(String spouse1, String spouse2, String childID) throws DataAccessException, SQLException {
        Random r = new Random();
        Event childBirth = edao.findBirth(childID);

        //Generates reasonable birth and death based off of child birth.
        int marriageYear = childBirth.getYear() - 1 - r.nextInt(5);

        float latitude = 0.0f;
        float longitude = 0.0f;
        String id1 = UUID.randomUUID().toString();
        String id2 = UUID.randomUUID().toString();

        String country =""; //FIXME
        String city = "";


        Event spouse1Event = new Event(id1, childBirth.getAssociated_Username(),spouse1,latitude,longitude,country, city, "marriage", marriageYear);
        Event spouse2Event = new Event(id2, childBirth.getAssociated_Username(),spouse2,latitude,longitude,country, city, "marriage", marriageYear);

        edao.insertEvent(spouse1Event);
        edao.insertEvent(spouse2Event);

    }
}
