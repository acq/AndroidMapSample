package com.applidium.paris.db;

import com.applidium.paris.App;
import com.applidium.paris.model.Arrondissement;
import com.applidium.paris.util.MapperUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArrondissementRepository {

    public List<Arrondissement> getArrondissements() {
        List<Arrondissement> arrondissements = new Select().from(Arrondissement.class).queryList();
        if (arrondissements.size() == 0) {
            try {
                List<JsonArrondissementWrapper> wrappers = MapperUtil.sharedMapper().readValue(App.getContext().getAssets().open("data/arrondissements.json"), new TypeReference<List<JsonArrondissementWrapper>>() {
                });
                arrondissements = new ArrayList<>(wrappers.size());
                for (JsonArrondissementWrapper wrapper : wrappers) {
                    Arrondissement arrondissement = wrapper.toArrondissement();
                    arrondissement.setWikipediaUrl(arrondissement.getNumber() > 1 ? String.format("https://fr.wikipedia.org/wiki/%de_arrondissement_de_Paris", arrondissement.getNumber()) : "https://fr.wikipedia.org/wiki/1er_arrondissement_de_Paris");
                    arrondissements.add(arrondissement);
                }
                TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(arrondissements)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return arrondissements;
    }

    static class JsonArrondissement {
        long     n_sq_co;
        long     n_sq_ar;
        int      objectid;
        String   l_ar;
        double   longueur;
        double   surface;
        double[] geom_x_y;
        String   geom;
        double   perimetre;
        String   l_aroff;
        int      c_arinsee;
        int      c_ar;

        public void setGeom(JsonNode geom) {
            this.geom = geom.toString();
        }
    }

    @JsonIgnoreProperties({"geometry"})
    static class JsonArrondissementWrapper {
        String             datasetid;
        String             recordid;
        JsonArrondissement fields;
        Date               record_timestamp;

        Arrondissement toArrondissement() {
            return new Arrondissement(
                    recordid,
                    fields.n_sq_ar,
                    fields.c_ar,
                    fields.c_arinsee,
                    fields.l_ar,
                    fields.l_aroff,
                    datasetid,
                    record_timestamp,
                    fields.geom_x_y[0],
                    fields.geom_x_y[1],
                    fields.geom,
                    fields.longueur,
                    fields.surface,
                    fields.perimetre);
        }
    }
}
