/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help;

import model.UserModel;
import javax.json.Json;
import javax.json.JsonObject;
import java.io.StringReader;
import javax.json.JsonReader;

/**
 *
 * @author remon
 */
public class JsonUtil {

    public static void main(String[] args) {

    }

    public static String convertFromJsonEmail(JsonObject obj) {
        return obj.getString(JsonConst.EMAIL);
    }

    public static JsonObject convertToJsonEmailResponse(int id) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, JsonConst.TYPE_EMAIL_SIGNIN_RESPONSE)
                .add("id", id)
                .build();
        return obj;
    }

    public static String convertFromJsonPasswordd(JsonObject obj) {
        return obj.getString(JsonConst.PASSWORD);
    }

    public static JsonObject convertToJsonPasswordResponse(boolean b) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, JsonConst.TYPE_EMAIL_SIGNIN_RESPONSE) //NotUse
                .add(JsonConst.TYPE_PASSWORD_SIGNIN_RESPONSE, b)
                .build();
        return obj;
    }

    public static JsonObject convertFromString(String string) {
        JsonReader jsonReader = Json.createReader(new StringReader(string));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        return jsonObject;
    }

    public static int convertFromJsonId(JsonObject obj) {
        return obj.getInt(JsonConst.ID);
    }

    //Don't Have Online_Status
    public static UserModel converetFromJsonUserModel(JsonObject obj) {
        UserModel user = new UserModel();
        user.setId(obj.getInt("id"));
        user.setName(obj.getString("name"));
        user.setEmail(obj.getString("email"));
        user.setPassword(obj.getString("password"));
        return user;
    }
    
}
