package com.example.abboud_tikinas;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageTagsInfo {
    private List<String> tags;
    private List<String> filtreFlou;
    private List<Transformation> transformations;

    public ImageTagsInfo() {
        this.tags = new ArrayList<>();
        this.transformations = new ArrayList<>();
        this.filtreFlou = new ArrayList<>();
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void addFiltreFlou(String filtre) {
        filtreFlou.add(filtre);
    }

    public void addTransformation(Transformation transformation) {
        transformations.add(transformation);
    }

    public void saveToJson(String filename) throws IOException {
        File file = new File(filename.concat(".json"));
        JSONObject json = new JSONObject();
        JSONArray tagsArray = new JSONArray();
        JSONArray filtreArrayflou = new JSONArray();
        for (String tag : tags) {
            tagsArray.add(tag);
        }
        json.put("tags", tagsArray);

        for (String filr : filtreFlou) {
            filtreArrayflou.add(filr);
        }
        json.put("FiltreSpecial", filtreArrayflou);

        JSONArray transformationsArray = new JSONArray();
        for (Transformation transformation : transformations) {
            JSONObject transformationObject = new JSONObject();
            transformationObject.put("type", transformation.getType());
            transformationsArray.add(transformationObject);
        }
        json.put("transformations", transformationsArray);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json.toJSONString());
        }
    }

    public static class Transformation {
        private String type;

        public Transformation(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
