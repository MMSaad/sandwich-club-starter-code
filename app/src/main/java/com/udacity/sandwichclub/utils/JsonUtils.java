package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /***
     * FLAGS/CONSTANTS
     */
    private static final String JSON_NAME = "name";
    private static final String JSON_MAIN_NAME = "mainName";
    private static final String JSON_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String JSON_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_IMAGE = "image";
    private static final String JSON_INGREDIENTS = "ingredients";

    /***
     * Parse Sandwich object from Json
     * @param json Json string represent Sandwich object
     * @return Sandwich object, Null of exception
     */
    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject(JSON_NAME);
            String mainName = name.getString(JSON_MAIN_NAME);
            JSONArray alsoKnownAs = name.getJSONArray(JSON_ALSO_KNOWN_AS);
            List<String> knownNames = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                knownNames.add(alsoKnownAs.getString(i));
            }
            String placeOfOrigin = jsonObject.getString(JSON_PLACE_OF_ORIGIN);
            String description = jsonObject.getString(JSON_DESCRIPTION);
            String image = jsonObject.getString(JSON_IMAGE);
            JSONArray ingredientsArray = jsonObject.getJSONArray(JSON_INGREDIENTS);
            ArrayList<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }
            return new Sandwich(mainName, knownNames, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
