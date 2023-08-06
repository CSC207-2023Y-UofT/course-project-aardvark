package user_features;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


/**
 * A gateway class that is used to access and edit the data in the data file "DataModel.json" and interact with
 * instances of user.
 *
 * Instance variables: dataDocument a JSONObject for editing, representing the JSON file created from parsing the document.
 *                     file a File object for the JSON file.
 */
public class UserDSGateway {

    // instance variable
    JSONObject dataDocument;
    File file;

    @SuppressWarnings("unchecked")
    public UserDSGateway(){

        // Creating file object to check if file exists and create it if it does not
        this.file = new File("src/main/java/user_features/DataModel.json");
        if (!this.file.exists()) {
            try (FileWriter fw = new FileWriter(this.file)) {
                this.dataDocument = new JSONObject();
                JSONObject userDetails = new JSONObject();
                userDetails.put("Name", "Sample User");
                userDetails.put("Email", "sample.user@gmail.com");
                userDetails.put("Password", "12345");
                userDetails.put("Projects", new JSONArray());
                this.dataDocument.put("sample.user@gmail.com", userDetails);
                fw.write(this.dataDocument.toJSONString());
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // Handles case after file has been created
            JSONParser jsonParser = new JSONParser();
            this.dataDocument = (JSONObject) jsonParser.parse(new FileReader(file));
        }
        catch (ParseException | IOException ex){
            ex.printStackTrace();
        }

    }

    /**
     * Keeps track of logged-in user.
     *
     * @param email the inputted email by the user
     * @param password the inputted email by
     * @return a user object that corresponds to the inputted information
     */

    public User userLogin(String email, String password){

        return (new User("Name_holder", email, password));


    }

    /**
     * Keeps track of new user registering.
     *
     * @param name the inputted name by the user.
     * @param email the inputted email by the user.
     * @param password the inputted password by the user.
     * @return a user object that corresponds to the inputted information
     */

    public User userRegister(String name, String email, String password){

        return (new User(name, email, password));

    }

    /**
     * Adds a new user to the JSON file.
     *
     * @param user the user to be added to JSON file.
     */
    @SuppressWarnings("unchecked")
    public void addUser(User user) {

        // Creating json object for user
        JSONObject userDetails = new JSONObject();
        userDetails.put("Name", user.getName());
        userDetails.put("Email", user.getEmail());
        userDetails.put("Password", user.getPassword());
        userDetails.put("Projects", new JSONArray());

        // using the user email as the key since it is unique to each user
        this.dataDocument.put(user.getEmail(), userDetails);

    }

    /**
     * Returns the name of a user from the JSON file based on given email.
     *
     * @param email the email of the user of which you want to find the name.
     * @return the name of the user corresponding to that email in JSON file.
     */
    public String getName(String email){
        JSONObject userDetails = (JSONObject) this.dataDocument.get(email);
        return (String) userDetails.get("Name");
    }

    /**
     * Checks if a given user exists in the JSON file.
     *
     * @param user user object of the user which you want to check exists.
     * @return True iff the user exists in the JSON file.
     */
    public boolean checkUserExists(User user){

        return(this.dataDocument.containsKey(user.getEmail()));

    }

    /**
     * Checks if the user inputted password matches the password saved in the JSON file.
     *
     * @param email the user inputted email from log in page.
     * @param password the user inputted password from log in page.
     * @return True iff the inputted password matches the saved password in the JSON file.
     */
    public boolean checkPassword(String email, String password){

        JSONObject userDetails = (JSONObject) this.dataDocument.get(email);
        return(password.equals(userDetails.get("Password")));
    }

    /**
     * Adds a given project HashMap to the given user in the JSON file.
     *
     * @param user the User object representing the user which the project is to be added.
     * @param project the project HashMap.
     */
    @SuppressWarnings("unchecked")
    public void addProject(User user, HashMap<String, Object> project){

        JSONObject userDetails = (JSONObject) this.dataDocument.get(user.getEmail());
        JSONArray projectsArray = (JSONArray) userDetails.get("Projects");
        projectsArray.add(project);

    }

    /**
     * Deletes a given project HashMap to the given user in the JSON file.
     *
     * @param user the User object representing the user which the project is to be deleted.
     * @param project the project HashMap.
     */
    public boolean deleteProject(User user, HashMap<String, Object> project){

        JSONObject userDetails = (JSONObject) this.dataDocument.get(user.getEmail());
        JSONArray projectsArray = (JSONArray) userDetails.get("Projects");
        return(projectsArray.remove(project));

    }

    /**
     * Saves the changes made to the JSON file. Must be called after everytime the JSON file is done being edited.
     */
    public void saveChanges() {
        try (FileWriter fw = new FileWriter(this.file)){
            fw.write(this.dataDocument.toJSONString());
            fw.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
