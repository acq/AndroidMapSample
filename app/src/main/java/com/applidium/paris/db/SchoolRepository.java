package com.applidium.paris.db;

import com.applidium.paris.App;
import com.applidium.paris.model.School;
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

public class SchoolRepository {

    public List<School> getSchools() {
        List<School> schools = new Select().from(School.class).queryList();
        if (schools.size() == 0) {
            try {
                List<JsonSchoolWrapper> wrappers = MapperUtil.sharedMapper().readValue(App.getContext().getAssets().open("data/liste_des_etablissements_scolaires2.json"), new TypeReference<List<JsonSchoolWrapper>>() {
                });
                schools = new ArrayList<>(wrappers.size());
                for (JsonSchoolWrapper wrapper : wrappers) {
                    schools.add(wrapper.toSchool());
                }
                TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(schools)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return schools;
    }

    static class JsonSchool {
        String   ville;
        int      code_postal;
        String   nom;
        String   adresse;
        String   n_etablissement;
        double[] adresse_complete;
        String   type;
    }

    @JsonIgnoreProperties({"geometry"})
    static class JsonSchoolWrapper {
        String     datasetid;
        String     recordid;
        JsonSchool fields;
        Date       record_timestamp;

        School toSchool() {
            return new School(
                    recordid,
                    fields.n_etablissement,
                    fields.nom,
                    fields.type,
                    fields.adresse,
                    fields.adresse_complete != null ? fields.adresse_complete[0] : 0.,
                    fields.adresse_complete != null ? fields.adresse_complete[1] : 0.,
                    fields.ville,
                    fields.code_postal,
                    datasetid,
                    record_timestamp);
        }
    }
}
