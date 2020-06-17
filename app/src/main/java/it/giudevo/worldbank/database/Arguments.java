package it.giudevo.worldbank.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Arguments")
public
class  Arguments implements Parcelable {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        public Integer id;
        @ColumnInfo(name = "value")
        public String value;
        @ColumnInfo(name = "sourceNote")
        public String sourceNote;

    public Arguments(Parcel in) {
        id = in.readInt();
        value = in.readString();
        sourceNote = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(value);
        dest.writeString(sourceNote);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Arguments> CREATOR = new Creator<Arguments>() {
        @Override
        public Arguments createFromParcel(Parcel in) {
            return new Arguments(in);
        }

        @Override
        public Arguments[] newArray(int size) {
            return new Arguments[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSourceNote() {
        return sourceNote;
    }

    public void setSourceNote(String sourceNote) {
        this.sourceNote = sourceNote;
    }
}