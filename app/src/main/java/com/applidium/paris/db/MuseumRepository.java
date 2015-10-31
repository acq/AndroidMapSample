package com.applidium.paris.db;

import com.applidium.paris.App;
import com.applidium.paris.model.Museum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MuseumRepository {
    public List<Museum> getMuseums() {
        List<Museum> museums = new Select().from(Museum.class).queryList();
        if (museums.size() == 0) {
            try {
                List<JsonMuseumWrapper> wrappers = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).readValue(App.getContext().getAssets().open("data/liste-musees-de-france-a-paris.json"), new TypeReference<List<JsonMuseumWrapper>>() {
                });
                museums = new ArrayList<>(wrappers.size());
                for (JsonMuseumWrapper wrapper : wrappers) {
                    museums.add(wrapper.toMuseum());
                }
                TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(museums)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return museums;
    }


    static class JsonMuseum {
        String   annreouv;
        String   periode_ouverture;
        String   nom_du_musee;
        String   adr;
        String   ville;
        String   nomreg;
        String   sitweb;
        String   fermeture_annuelle;
        double[] coordonnees_;
        String   jours_nocturnes;
        String   ferme;
        String   cp;
        String   nomdep;
        String   annexe;
    }

    @JsonIgnoreProperties({"geometry"})
    static class JsonMuseumWrapper {
        String     datasetid;
        String     recordid;
        JsonMuseum fields;
        Date       record_timestamp;

        Museum toMuseum() {
            return new Museum(
                    recordid,
                    fields.nom_du_musee,
                    fields.adr,
                    fields.periode_ouverture,
                    fields.ville,
                    fields.nomreg,
                    fields.sitweb,
                    fields.fermeture_annuelle,
                    fields.ferme,
                    fields.cp,
                    fields.nomdep,
                    fields.coordonnees_ != null ? fields.coordonnees_[0] : 0.,
                    fields.coordonnees_ != null ? fields.coordonnees_[1] : 0.,
                    fields.jours_nocturnes,
                    fields.annreouv,
                    fields.annexe,
                    record_timestamp,
                    datasetid);
        }
    }

}
