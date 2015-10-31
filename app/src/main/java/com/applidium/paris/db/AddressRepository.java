package com.applidium.paris.db;

import com.applidium.paris.App;
import com.applidium.paris.model.Address;
import com.applidium.paris.model.Address$Table;
import com.applidium.paris.util.MapperUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddressRepository {
    public AddressRepository() {
        if (new Select().count().from(Address.class).count() == 0) {
            try {
                List<JsonAddressWrapper> wrappers = MapperUtil.sharedMapper().readValue(App.getContext().getAssets().open("data/adresse_paris.json"), new TypeReference<List<JsonAddressWrapper>>() {
                });
                List<Address> addresses = new ArrayList<>(wrappers.size());
                for (JsonAddressWrapper wrapper : wrappers) {
                    addresses.add(wrapper.toAddress());
                }
                TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(addresses)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Address> search(String search) {
        return new Select().from(Address.class).where(Condition.column(Address$Table.ADDRESS).like("%" + search + "%")).queryList();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class JsonAddress {
        double[] geom_x_y;
        String l_adr;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class JsonAddressWrapper {
        JsonAddress fields;
        String recordid;


        public Address toAddress() {
            return new Address(recordid, fields.l_adr, fields.geom_x_y[0], fields.geom_x_y[1]);
        }
    }
}
