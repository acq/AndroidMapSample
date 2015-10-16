package com.applidium.paris.model;

import com.applidium.paris.App;
import com.applidium.paris.util.MapperUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TheaterProvider {

    public List<Theater> getTheaters() {
        List<Theater> theaters = new Select().from(Theater.class).queryList();
        if (theaters.size() == 0) {
            try {
                List<JsonTheaterWrapper> wrappers = MapperUtil.sharedMapper().readValue(App.getContext().getAssets().open("data/cinemas-a-paris.json"), new TypeReference<List<JsonTheaterWrapper>>() {
                });
                theaters = new ArrayList<>(wrappers.size());
                for (JsonTheaterWrapper wrapper : wrappers) {
                    theaters.add(wrapper.toTheater());
                }
                TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(theaters)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return theaters;
    }

    static class JsonTheater {
        int    ecrans;
        String fauteuils;
        int    ndegauto;
        int    arrondissement;
        String art_et_essai;
        String adresse;
        String nom_etablissement;
        double[] coordonnees;
    }

    @JsonIgnoreProperties({"geometry"})
    static class JsonTheaterWrapper {
        String datasetid;
        String recordid;
        JsonTheater fields;
        Date record_timestamp;

        Theater toTheater() {
            Theater theater = new Theater();
            theater.recordId = recordid;
            theater.autoId = fields.ndegauto;
            theater.name = fields.nom_etablissement;
            theater.screens = fields.ecrans;
            theater.seats = fields.fauteuils;
            theater.arrondissement = fields.arrondissement;
            theater.artHouse = "A".equals(fields.art_et_essai);
            theater.address = fields.adresse;
            if (fields.coordonnees != null) {
                theater.latitude = fields.coordonnees[0];
                theater.longitude = fields.coordonnees[1];
            }
            theater.source = datasetid;
            theater.updatedAt = record_timestamp;
            return theater;
        }
    }
}
