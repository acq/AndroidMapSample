package com.applidium.map_sample.db;

import com.applidium.map_sample.App;
import com.applidium.map_sample.model.PublicToilet;
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

public class PublicToiletRepository {

    public List<PublicToilet> getPublicToilets() {
        List<PublicToilet> toilets = new Select().from(PublicToilet.class).queryList();
        if (toilets.size() == 0) {
            try {
                List<JsonPublicToiletWrapper> toiletWrappers = MapperUtil.sharedMapper().readValue(App.getContext().getAssets().open("data/sanisettesparis2011.json"), new TypeReference<List<JsonPublicToiletWrapper>>() {
                });
                toilets = new ArrayList<>(toiletWrappers.size());
                for (JsonPublicToiletWrapper wrapper : toiletWrappers) {
                    toilets.add(wrapper.toPublicToilet());
                }
                TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(toilets)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return toilets;
    }

    @JsonIgnoreProperties({"geom"})
    static class JsonPublicToilet {
        String   info;
        String   libelle;
        double[] geom_x_y;
    }

    @JsonIgnoreProperties({"geometry"})
    static class JsonPublicToiletWrapper {
        String           datasetid;
        String           recordid;
        JsonPublicToilet fields;
        Date             record_timestamp;

        PublicToilet toPublicToilet() {
            return new PublicToilet(
                    recordid,
                    fields.info,
                    fields.libelle,
                    fields.geom_x_y[0],
                    fields.geom_x_y[1],
                    record_timestamp,
                    datasetid);
        }
    }

}
