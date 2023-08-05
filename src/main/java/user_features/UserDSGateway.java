package user_features;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;

import java.io.*;
import java.util.HashMap;


public class UserDSGateway {
    /**
     * A gateway class that is used to access and edit the data in the data file "user.json".
     *
     */

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
    @SuppressWarnings("unchecked")
    public void addUser(User user) {

        // Creating json object for user
        JSONObject userDetails = new JSONObject();
        userDetails.put("Name", user.name);
        userDetails.put("Email", user.email);
        userDetails.put("Password", user.password);
        userDetails.put("Projects", new JSONArray());

        // using the user email as the key since it is unique to each user
        this.dataDocument.put(user.email, userDetails);

    }

    public String getName(String email){
        JSONObject userDetails = (JSONObject) this.dataDocument.get(email);
        return (String) userDetails.get("Name");
    }

    public boolean checkUserExists(User user){

        return(this.dataDocument.containsKey(user.email));

    }

    public boolean checkPassword(String email, String password){

        JSONObject userDetails = (JSONObject) this.dataDocument.get(email);
        return(password.equals(userDetails.get("Password")));
    }

    @SuppressWarnings("unchecked")
    public void addProject(User user, HashMap<String, Object> project){

        JSONObject userDetails = (JSONObject) this.dataDocument.get(user.email);
        JSONArray projectsArray = (JSONArray) userDetails.get("Projects");
        projectsArray.add(project);

    }

    public boolean deleteProject(User user, HashMap<String, Object> project){

        JSONObject userDetails = (JSONObject) this.dataDocument.get(user.email);
        JSONArray projectsArray = (JSONArray) userDetails.get("Projects");
        return(projectsArray.remove(project));

    }

    public void saveChanges(){
        try (FileWriter fw = new FileWriter(this.file)){
            fw.write(this.dataDocument.toJSONString());
            fw.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}




