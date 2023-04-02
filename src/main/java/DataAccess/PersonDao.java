package DataAccess;


import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all database communication associated with a Person object
 */
public class PersonDao {

    /**
     * The database connection
     */
    private final Connection conn;

    /**
     * Constructor for the PersonDao
     * @param conn
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * inserts a new person into the database
     * @param person
     * @throws DataAccessException
     */
    public void Insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, fatherID, motherID, spouseID, gender) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getFatherID());
            stmt.setString(6, person.getMotherID());
            stmt.setString(7, person.getSpouseID());
            stmt.setString(8, person.getGender());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * Returns a person from the database by personID
     * @param personID
     * @return
     * @throws DataAccessException
     */
    public Person Find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"), rs.getString("gender"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * Deletes a person from the database by personID
     * @param personID
     * @throws DataAccessException
     */
    public void delete (String personID) throws DataAccessException {
        String sql = "DELETE FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting a person from the Person table");
        }
    }

    /**
     * Returns a list of all the persons in the database
     * @return List of persons
     */
    public List<Person> GetAllPersons() throws DataAccessException {
        List<Person> allPersons = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Person;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            while (rs.next()) {
                Person personToAdd = new Person(rs.getString("personID"),
                        rs.getString("associatedUsername"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("fatherID"),
                        rs.getString("motherID"),
                        rs.getString("spouseID"),
                        rs.getString("gender"));
                allPersons.add(personToAdd);
            }
            return allPersons;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while getting all the Persons from the Person table");
        }
    }

    /**
     * Retrieves all the people from the database associated with the provided username
     * @param username
     * @return
     * @throws DataAccessException
     */
    public List<Person> GetAllPersons(String username) throws DataAccessException {
        List<Person> allPersons = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Person personToAdd = new Person(rs.getString("personID"),
                        rs.getString("associatedUsername"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("fatherID"),
                        rs.getString("motherID"),
                        rs.getString("spouseID"),
                        rs.getString("gender"));
                allPersons.add(personToAdd);
            }
            return allPersons;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while getting all the Persons from the Person table");
        }
    }

    /**
     * Clears all people from the database
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Person";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }
}
