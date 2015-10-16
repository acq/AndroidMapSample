package com.applidium.paris.model;

import com.applidium.paris.App;
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

public class ArrondissementProvider {

    public List<Arrondissement> getArrondissements() {
        List<Arrondissement> arrondissements = new Select().from(Arrondissement.class).queryList();
        if (arrondissements.size() == 0) {
            try {
                List<JsonArrondissementWrapper> wrappers = MapperUtil.sharedMapper().readValue(App.getContext().getAssets().open("data/arrondissements.json"), new TypeReference<List<JsonArrondissementWrapper>>() {
                });
                arrondissements = new ArrayList<>(wrappers.size());
                for (JsonArrondissementWrapper wrapper : wrappers) {
                    arrondissements.add(wrapper.toArrondissement());
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
            Arrondissement arrondissement = new Arrondissement();
            arrondissement.recordId = recordid;
            arrondissement.sequentialId = fields.n_sq_ar;
            arrondissement.number = fields.c_ar;
            arrondissement.inseeNumber = fields.c_arinsee;
            arrondissement.name = fields.l_ar;
            arrondissement.officialName = fields.l_aroff;
            arrondissement.source = datasetid;
            arrondissement.updatedAt = record_timestamp;
            if (fields.geom_x_y != null) {
                arrondissement.latitude = fields.geom_x_y[0];
                arrondissement.longitude = fields.geom_x_y[1];
            }
            arrondissement.setGeoJson(fields.geom);
            arrondissement.length = fields.longueur;
            arrondissement.surface = fields.surface;
            arrondissement.perimeter = fields.perimetre;
            return arrondissement;
        }
    }
}
