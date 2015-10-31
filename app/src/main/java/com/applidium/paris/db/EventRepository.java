package com.applidium.paris.db;

import com.applidium.paris.App;
import com.applidium.paris.model.Event;
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

public class EventRepository {
    public List<Event> getEvents() {
        List<Event> events = new Select().from(Event.class).queryList();
        if (events.size() == 0) {
            try {
                List<EventWrapper> wrappers = MapperUtil.sharedMapper().readValue(App.getContext().getAssets().open("data/evenements-a-paris.json"), new TypeReference<List<EventWrapper>>() {
                });
                events = new ArrayList<>(wrappers.size());
                for (EventWrapper wrapper : wrappers) {
                    events.add(wrapper.fields);
                }
                TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(events)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    @JsonIgnoreProperties({"geometry"})
    static class EventWrapper {
        String datasetid;
        String recordid;
        Event  fields;
        Date   record_timestamp;
    }

    @JsonIgnoreProperties({"parameters"})
    static class EventJson {
        String nhits;
        List<EventWrapper> records;
    }
}
