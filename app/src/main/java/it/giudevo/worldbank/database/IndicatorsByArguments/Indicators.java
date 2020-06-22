package it.giudevo.worldbank.database.IndicatorsByArguments;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Indicators implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "number")
    public int number;
    @ColumnInfo(name="id")
    public String id;
    @ColumnInfo(name="name")
    public String name;
    @ColumnInfo(name = "unit")
    public String unit;
    @ColumnInfo(name = "sourceNote")
    public String sourceNote;
    @ColumnInfo(name = "sourceOrganization")
    public String sourceOrganization;

    public Indicators(String id, String name, String unit, String sourceNote, String sourceOrganization) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.sourceNote = sourceNote;
        this.sourceOrganization = sourceOrganization;
    }

    protected Indicators(Parcel in) {
        id = in.readString();
        name = in.readString();
        unit = in.readString();
        sourceNote = in.readString();
        sourceOrganization = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(unit);
        dest.writeString(sourceNote);
        dest.writeString(sourceOrganization);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
