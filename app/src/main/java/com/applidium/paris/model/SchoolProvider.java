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

public class SchoolProvider {

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
            School school = new School();
            school.recordId = recordid;
            school.id = fields.n_etablissement;
            school.name = fields.nom;
            school.type = fields.type;
            school.address = fields.adresse;
            if (fields.adresse_complete != null) {
                school.latitude = fields.adresse_complete[0];
                school.longitude = fields.adresse_complete[1];
            }
            school.city = fields.ville;
            school.postalCode = fields.code_postal;
            school.source = datasetid;
            school.updatedAt = record_timestamp;
            return school;
        }
    }
}
