package it.giudevo.worldbank.database.Indicators;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Indicators implements Parcelable {
    @PrimaryKey(autoGenerate = true)
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
        source = in.readString();
        sourceNote = in.readString();
        sourceOrganization = in.readString();
        topics = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(unit);
        dest.writeString(source);
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
}
