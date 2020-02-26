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
    private Database db = new Database();
    boolean success;

    public FillService(){
    };

    /**
     * This function serves the fill request
     * @param fr fill request
     * @return Fill Result
     */
    public FillResult fill (FillRequest fr){
        FillResult fR = new FillResult();
        try {
            db.openConnection();

            PersonDao pdao = new PersonDao(db.getConnection());
            UserDao udao = new UserDao(db.getConnection());
            EventDao edao = new EventDao(db.getConnection());

            User user = udao.find(fr.getUserID());

            if (user == null){ //Checks if user exists
                throw new DataAccessException("Username is not in the database.");
            }

            udao.removeUser(user);
            user.setPerson_id(UUID.randomUUID().toString()); //sets person ID to a random made UUID Possibly wrong order
            udao.insertUser(user);
            Person userPerson = new Person(user.getPerson_id(),user.getId(),user.getFirst_name(),user.getLast_name(),user.getGender(),"","",""); // Construct person object based on user
            pdao.insertPerson(userPerson);

            edao.generateBirth(userPerson,1999);











        }catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }

        return fR;
    }

    private void GenerateGenerations(Person child, int numGenerationsToGenerate) throws DataAccessException {
        Person base = new Person();
        base.setAssociated_Username(child.getAssociated_Username());

        Person dad = base;
        Person mom = base;

        dad.setGender("m");
        mom.setGender("f");

        mom.setPerson_id(UUID.randomUUID().toString());
        dad.setPerson_id(UUID.randomUUID().toString());

        mom.setSpouse_id(dad.getPerson_id());
        dad.setSpouse_id(mom.getPerson_id());


        //set first and last names from the last and first name database FIXME


        child.setFather_id(dad.getPerson_id());
        child.setMother_id(mom.getPerson_id());

        Random r = new Random();

        EventDao edao = new EventDao(db.openConnection());


        Event childBirth = edao.findBirth(child.getPerson_id());



        //Generates reasonable birth and death based off of child birth.
        int birthYearMom = childBirth.getYear() - 23 - r.nextInt(10);
        int deathYearMom = childBirth.getYear() + 10 + r.nextInt(30);
        int birthYearDad = childBirth.getYear() - 23 - r.nextInt(10);
        int deathYearDad = childBirth.getYear() + 10 + r.nextInt(30);

        db.openConnection();

        PersonDao pdao = new PersonDao(db.getConnection());

        try {
            pdao.updatePerson(child, child.getPerson_id());
            pdao.insertPerson(mom);
            pdao.insertPerson(dad);

            edao.generateBirth(mom,birthYearMom);
            edao.generateBirth(dad,birthYearDad);

            edao.generateDeath(mom,deathYearMom);
            edao.generateDeath(dad,deathYearDad)


        } catch (SQLException e) {
            e.printStackTrace();
        }


        numGenerationsToGenerate = numGenerationsToGenerate-1;
        if(numGenerationsToGenerate > 0) {
            GenerateGenerations(mom, numGenerationsToGenerate);
            GenerateGenerations(dad, numGenerationsToGenerate);
        }


    }



    private void GenerateCouple(){}

    private void MarriageOfTwo(String spouse1, String spouse2, String childID) throws DataAccessException, SQLException {
        Random r = new Random();
        EventDao edao = new EventDao(db.openConnection());


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

    private void GiveBornAndDead(String personID, String childID) throws DataAccessException, SQLException {
        Random r = new Random();

        PersonDao pdao = new PersonDao(db.openConnection());
        EventDao edao = new EventDao(db.openConnection());


        Event childBirth = edao.findBirth(childID);



        Person person = pdao.find(personID);
        Person child = pdao.find(childID);

        //Generates reasonable birth and death based off of child birth.
        int birthYear = childBirth.getYear() - 23 - r.nextInt(10);
        int deathYear = childBirth.getYear() + 10 + r.nextInt(30);

        float birthLatitude = 0.0f;
        float birthLongitude = 0.0f;
        String birthID = "";
        String birthCountry ="";
        String birthCity = "";

        float deathLatitude = 0.0f;
        float deathLongitude = 0.0f;
        String deathID = "";
        String deathCountry ="";
        String deathCity = "";


        Event birth = new Event(birthID, child.getAssociated_Username(),personID,birthLatitude,birthLongitude,birthCountry, birthCity, "birth", birthYear);
        Event death = new Event(deathID, child.getAssociated_Username(),personID,deathLatitude,deathLongitude,deathCountry, deathCity, "death", deathYear);

        edao.insertEvent(birth);
        edao.insertEvent(death);
    }
}
