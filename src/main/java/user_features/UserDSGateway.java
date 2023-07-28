package user_features;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class UserDSGateway {
    /**
     * A gateway class that is used to access and edit the data in the data file "user.json".
     *
     */

    // instance variable
    JSONObject userDocument;
    JSONArray userList;

    public UserDSGateway() throws IOException, ParseException {
        if (!new File("users.json").exists()){
            new FileWriter("user.json");
        }
        JSONParser jsonParser = new JSONParser();
        this.userDocument = (JSONObject) jsonParser.parse(new FileReader("users.json"));

        if (this.userDocument.isEmpty()){
            this.userList = new JSONArray();
        }
        this.userList = (JSONArray) this.userDocument.get("users");
    }

    public void addUser(User user) {

        // Creating json object for user
        JSONObject userDetails = new JSONObject();
        userDetails.put("Name", user.name);
        userDetails.put("Email", user.email);
        userDetails.put("Password", user.password);

        JSONObject userObject = new JSONObject();
        userObject.put("user", userDetails);

        // add user to list
        this.userList.add(userObject);

    }



}

