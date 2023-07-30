package user_features;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class UserDSGateway {
    /**
     * A gateway class that is used to access and edit the data in the data file "user.json".
     *
     */

    // instance variable
    JSONObject dataDocument;
    File file;

    @SuppressWarnings("unchecked")
    public UserDSGateway() throws IOException {

        // Creating file object to check if file exists and create it if it does not
        this.file = new File("src/main/java/user_features/DataModel.json");
        if (!this.file.exists()) {
            try (FileWriter fw = new FileWriter(this.file)) {
                this.dataDocument = new JSONObject();
                JSONObject userDetails = new JSONObject();
                userDetails.put("Name", "Sample User");
                userDetails.put("Email", "sample.user@gmail.com");
                userDetails.put("Password", "12345");
                userDetails.put("Projects", new ArrayList<>());
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
        catch (ParseException ex){
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
        userDetails.put("Projects", new ArrayList<>());

        // using the user email as the key since it is unique to each user
        this.dataDocument.put(user.email, userDetails);

    }


    // addProject

    // deleteProject
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




