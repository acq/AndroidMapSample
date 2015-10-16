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

public class SectorProvider {

    public List<Sector> getSectors() {
        List<Sector> sectors = new Select().from(Sector.class).queryList();
        if (sectors.size() == 0) {
            try {
                List<JsonSectorWrapper> wrappers = MapperUtil.sharedMapper().readValue(App.getContext().getAssets().open("data/quartier_paris.json"), new TypeReference<List<JsonSectorWrapper>>() {
                });
                sectors = new ArrayList<>(wrappers.size());
                for (JsonSectorWrapper wrapper : wrappers) {
                    sectors.add(wrapper.toSector());
                }
                TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(sectors)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sectors;
    }

    static class JsonSector {
        long     n_sq_qu;
        double   perimetre;
        long     objectid;
        double   longueur;
        int      c_qu;
        double   surface;
        double[] geom_x_y;
        String   geom;
        long     n_sq_ar;
        long     c_quinsee;
        String   l_qu;
        int      c_ar;

        public void setGeom(JsonNode geom) {
            this.geom = geom.toString();
        }
    }

    @JsonIgnoreProperties({"geometry"})
    static class JsonSectorWrapper {
        String     datasetid;
        String     recordid;
        JsonSector fields;
        Date       record_timestamp;

        Sector toSector() {
            Sector sector = new Sector();
            sector.recordId = recordid;
            sector.sequentialId = fields.n_sq_qu;
            sector.number = fields.c_qu;
            sector.inseeNumber = fields.c_quinsee;
            sector.name = fields.l_qu;
            sector.arrondissement = fields.c_ar;
            sector.arrondissementId = fields.n_sq_ar;
            sector.perimeter = fields.perimetre;
            sector.length = fields.longueur;
            sector.surface = fields.surface;
            sector.centerLatitude = fields.geom_x_y[0];
            sector.centerLongitude = fields.geom_x_y[1];
            sector.setGeoJson(fields.geom);
            sector.updatedAt = record_timestamp;
            sector.source = datasetid;
            return sector;
        }
    }
}
