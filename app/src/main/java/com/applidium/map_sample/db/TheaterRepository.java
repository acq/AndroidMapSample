package com.applidium.map_sample.db;

import com.applidium.map_sample.App;
import com.applidium.map_sample.model.Theater;
import com.applidium.map_sample.util.MapperUtil;
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

public class TheaterRepository {

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
        int      ecrans;
        String   fauteuils;
        int      ndegauto;
        int      arrondissement;
        String   art_et_essai;
        String   adresse;
        String   nom_etablissement;
        double[] coordonnees;
    }

    @JsonIgnoreProperties({"geometry"})
    static class JsonTheaterWrapper {
        String      datasetid;
        String      recordid;
        JsonTheater fields;
        Date        record_timestamp;

        Theater toTheater() {
            return new Theater(
                    recordid,
                    fields.ndegauto,
                    fields.nom_etablissement,
                    fields.ecrans,
                    fields.fauteuils,
                    fields.arrondissement,
                    "A".equals(fields.art_et_essai),
                    fields.adresse,
                    fields.coordonnees != null ? fields.coordonnees[0] : 0.,
                    fields.coordonnees != null ? fields.coordonnees[1] : 0,
                    datasetid,
                    record_timestamp);
        }
    }
}
