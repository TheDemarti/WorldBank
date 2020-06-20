package it.giudevo.worldbank.database.Indicators;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Delete;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Indicators implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "number")
    //public int number;
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="name")
    public String name;
    @ColumnInfo(name = "unit")
    public String unit;
    @ColumnInfo(name = "source")
    public String source;
    @ColumnInfo(name = "sourceNote")
    public String sourceNote;
    @ColumnInfo(name = "sourceOrganization")
    public String sourceOrganization;
    @ColumnInfo(name = "topics")
    public String topics;

    public static class Source {
        @PrimaryKey(autoGenerate = true)
        //@ColumnInfo(name="id")
        public int id;
        @ColumnInfo(name="name")
        public String name;
    }

    public Indicators(int id, String name, String unit, String source, String sourceNote, String sourceOrganization, String topics) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.source = source;
        this.sourceNote = sourceNote;
        this.sourceOrganization = sourceOrganization;
        this.topics = topics;
    }

    protected Indicators(Parcel in) {
        id = in.readInt();
        name = in.readString();
        unit = in.readString();
        //source = in.readList(source);
        sourceNote = in.readString();
        sourceOrganization = in.readString();
        topics = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(unit);
        //dest.writeTypedObject(source);
        dest.writeString(sourceNote);
        dest.writeString(sourceOrganization);
        dest.writeString(topics);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Indicators> CREATOR = new Creator<Indicators>() {
        @Override
        public Indicators createFromParcel(Parcel in) {
            return new Indicators(in);
        }

        @Override
        public Indicators[] newArray(int size) {
            return new Indicators[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceNote() {
        return sourceNote;
    }

    public void setSourceNote(String sourceNote) {
        this.sourceNote = sourceNote;
    }

    public String getSourceOrganization() {
        return sourceOrganization;
    }

    public void setSourceOrganization(String sourceOrganization) {
        this.sourceOrganization = sourceOrganization;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }


}
