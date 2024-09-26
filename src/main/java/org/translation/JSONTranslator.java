package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {
    private Map<String, Map<String, String>> CountryMap;
    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String Aplha2 = jsonObject.getString("alpha2");
                Map<String, String> countryMap = new HashMap<>();
                for (String key : jsonObject.keySet()) {
                    if (!key.equals("alpha2") && !key.equals("alpha3") && !key.equals("id")) {
                        countryMap.put(key, jsonObject.getString(key));
                    }
                }
                CountryMap.put(Aplha2, countryMap);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        Map<String, String> countryMap = CountryMap.get(country);
        if (countryMap == null) {
            return new ArrayList<>(countryMap.keySet());
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getCountries() {
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>();
    }

    @Override
    public String translate(String country, String language) {
        Map<String, String> countryMap = CountryMap.get(country);
        if (countryMap == null) {
            return null;
        }
        return null;
    }
}
