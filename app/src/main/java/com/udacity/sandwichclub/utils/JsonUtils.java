package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich;
        if (json == null) {
            sandwich = null;
        } else {
            JSONObject jsonObj = new JSONObject(json);
            JSONObject nameObj = jsonObj.getJSONObject("name");
            String name = nameObj.getString("mainName");
            String description = jsonObj.getString("description");
            String placeOfOrigin = jsonObj.getString("placeOfOrigin");
            String image = jsonObj.getString("image");

            JSONArray alsoKnownArray = nameObj.getJSONArray("alsoKnownAs");
            List<String> alsoKnownArrayList = new ArrayList<>();
            for (int i = 0; i < alsoKnownArray.length(); i++) {
                alsoKnownArrayList.add(alsoKnownArray.getString(i));
            }

            JSONArray ingredientsArray = jsonObj.getJSONArray("ingredients");
            List<String> ingredientsOfStrings = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredientsOfStrings.add(ingredientsArray.getString(i));
            }

            sandwich = new Sandwich(name, alsoKnownArrayList, placeOfOrigin, description, image, ingredientsOfStrings);
        }
        return sandwich;

    }
}
