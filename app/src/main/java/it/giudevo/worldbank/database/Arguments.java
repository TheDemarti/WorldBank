package it.giudevo.worldbank.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Arguments implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="value")
    public String value;
    @ColumnInfo(name="sourceNote")
    public String sourceNote;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(value);
        dest.writeString(sourceNote);
    }

    protected Arguments(Parcel in) {
        id = in.readInt();
        value = in.readString();
        sourceNote = in.readString();
    }
}
