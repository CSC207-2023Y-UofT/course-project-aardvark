package user_features;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class UserDSGateway {


    public void createDataModel

    public void addUser(User user){

        // Creating json object for user
        JSONObject userDetails = new JSONObject();
        userDetails.put("Name", user.name);
        userDetails.put("Email", user.email);
        userDetails.put("Password", user.password);

        JSONObject userObject = new JSONObject();
        userObject.put("user", userDetails);

        // Add user to list
        JSONArray userList = new JSONArray();
        userList.add(userObject);

        //Write JSON file
        try (FileWriter file = new FileWriter("users.json")){
            file.write(userList.toJSONString());
            file.flush();
        } catch (IOException e){
            e.printStackTrace();
        }






    }

}
