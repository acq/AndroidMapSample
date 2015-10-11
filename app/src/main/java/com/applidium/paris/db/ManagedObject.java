package com.applidium.paris.db;

import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

public abstract class ManagedObject extends BaseModel implements Parcelable {
    @PrimaryKey
    @Column
    long  id;
    @Column
    Date createdAt;
    @Column
    Date updatedAt;

    {
        createdAt = new Date();
    }

    public void save() {
        updatedAt = new Date();
        super.save();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ManagedObject that = (ManagedObject) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
