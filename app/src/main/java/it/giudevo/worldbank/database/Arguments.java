package it.giudevo.worldbank.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Arguments implements Parcelable {
    @PrimaryKey(autoGenerate = true)
        private int uid;
        @ColumnInfo(name = "id")
        public String id;
        @ColumnInfo(name = "value")
        public String value;
        @ColumnInfo(name = "sourceNote")
        public String sourceNote;

    public Arguments(String id, String value, String sourceNote) {
        this.id = id;
        this.value = value;
        this.sourceNote = sourceNote;
    }

//    protected Arguments(Parcel in) {
//        uid = in.readInt();
//        id = in.readString();
//        value = in.readString();
//        sourceNote = in.readString();
//    }

//    public final Creator<Arguments> CREATOR = new Creator<Arguments>() {
////        @Override
////        public Arguments createFromParcel(String id, String value, String sourceNote) {
////            return new Arguments(id, value, sourceNote);
////        }
//
//        @Override
//        public Arguments createFromParcel(Parcel source) {
//            return null;
//        }
//
//        @Override
//        public Arguments[] newArray(int size) {
//            return new Arguments[size];
//        }
//    };

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(id);
        dest.writeString(value);
        dest.writeString(sourceNote);
    }
//    public Arguments(int uid, String id, String value, String sourceNote) {
//
//    }
//
//    public Arguments(Parcel in) {
//    }
//
////    public Arguments(Parcel in) {
////        this.id = in.readString();
////        this.value = in.readString();
////        this.sourceNote = in.readString();
////    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(id);
//        dest.writeString(value);
//        dest.writeString(sourceNote);
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Creator<Arguments> CREATOR = new Creator<Arguments>() {
//        @Override
//        public Arguments createFromParcel(Parcel in) {
//            return new Arguments(in);
//        }
//
//        @Override
//        public Arguments[] newArray(int size) {
//            return new Arguments[size];
//        }
//    };
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//
//    public String getSourceNote() {
//        return sourceNote;
//    }
//
//    public void setSourceNote(String sourceNote) {
//        this.sourceNote = sourceNote;
//    }
}