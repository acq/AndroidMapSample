package com.applidium.paris.model;

import com.applidium.paris.App;
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

import timber.log.Timber;

public class MuseumProvider {
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
        String annreouv;
        String periode_ouverture;
        String nom_du_musee;
        String adr;
        String ville;
        String nomreg;
        String sitweb;
        String fermeture_annuelle;
        double[] coordonnees_;
        String jours_nocturnes;
        String ferme;
        String cp;
        String nomdep;
        String annexe;
    }

    @JsonIgnoreProperties({"geometry"})
    static class JsonMuseumWrapper {
        String     datasetid;
        String     recordid;
        JsonMuseum fields;
        Date       record_timestamp;

        Museum toMuseum() {
            Museum museum = new Museum();
            museum.id = this.recordid;
            museum.name = this.fields.nom_du_musee;
            museum.address = this.fields.adr;
            museum.openingHours = this.fields.periode_ouverture;
            museum.city = this.fields.ville;
            museum.region = this.fields.nomreg;
            museum.website = this.fields.sitweb;
            museum.closedDays = this.fields.fermeture_annuelle;
            museum.closed = this.fields.ferme;
            museum.postalCode = this.fields.cp;
            museum.department = this.fields.nomdep;
            if (this.fields.coordonnees_ != null) {
                museum.latitude = this.fields.coordonnees_[0];
                museum.longitude = this.fields.coordonnees_[1];
            } else {
                Timber.e("Null coordinates for : " + museum.name + " (" + museum.id + ")");
            }
            museum.lateNightOpenings = this.fields.jours_nocturnes;
            museum.reopening = this.fields.annreouv;
            museum.annex = this.fields.annexe;
            museum.updatedAt = this.record_timestamp;
            museum.source = this.datasetid;
            return museum;
        }
    }

}
