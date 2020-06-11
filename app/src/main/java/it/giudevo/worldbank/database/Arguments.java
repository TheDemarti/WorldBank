package it.giudevo.worldbank.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Arg")
public class Arguments implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "page")
    public int page;
    @ColumnInfo(name = "pages")
    public int pages;
    @ColumnInfo(name = "per_page")
    public String per_page;
    @ColumnInfo(name = "total")
    public int total;
    @Embedded
    private ArrayList<Template> templateList;

    public Arguments(){

    }

    public void setTemplateList(ArrayList<Template> templateList) {
        this.templateList = templateList;
    }

    public ArrayList<Template> getTemplateList() {
        return templateList;
    }

    protected Arguments(Parcel in) {
        this.page = (Integer) in.readInt();
        this.pages = in.readInt();
        this.per_page = in.readString();
        this.total = in.readInt();
        this.templateList = in.createTypedArrayList(Template.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(pages);
        dest.writeString(per_page);
        dest.writeInt(total);
        dest.writeTypedList(templateList);
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
}

@Entity(tableName = "Templates")
class Template implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "value")
    private String value;
    @ColumnInfo(name = "sourceNote")
    private String sourceNote;

    public Template(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    protected Template(Parcel in) {
        this.id = (Integer)in.readInt();
        this.value = in.readString();
        this.sourceNote = in.readString();
    }

    public static final Creator<Template> CREATOR = new Creator<Template>() {
        @Override
        public Template createFromParcel(Parcel in) {
            return new Template(in);
        }

        @Override
        public Template[] newArray(int size) {
            return new Template[size];
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
}

//@Entity
//public class Arguments implements Parcelable {
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name="page")
//    public int page;
//    @ColumnInfo(name="pages")
//    public int pages;
//    @ColumnInfo(name="per_page")
//    public String per_page;
//    @ColumnInfo(name="total")
//    public int total;
//    @Embedded
//    private Arg mArg;
//
//    protected Arguments(Parcel in) {
//        page = in.readInt();
//        pages = in.readInt();
//        per_page = in.readString();
//        total = in.readInt();
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
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(page);
//        dest.writeInt(pages);
//        dest.writeString(per_page);
//        dest.writeInt(total);
//    }
//
//    public static class Arg{
//        @ColumnInfo(name="id")
//        public int id;
//        @ColumnInfo(name="value")
//        public String value;
//        @ColumnInfo(name="sourceNote")
//        public String sourceNote;
//    }
//

//    @ColumnInfo(name="id")
//    public int id;
//    @ColumnInfo(name="value")
//    public String value;
//    @ColumnInfo(name="sourceNote")
//    public String sourceNote;



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
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(page);
//        dest.writeInt(pages);
//        dest.writeString(per_page);
//        dest.writeInt(total);
//        dest.writeInt(id);
//        dest.writeString(value);
//        dest.writeString(sourceNote);
//    }
//
//    protected Arguments(Parcel in) {
//        page = in.readInt();
//        pages = in.readInt();
//        per_page = in.readString();
//        total = in.readInt();
//        id = in.readInt();
//        value = in.readString();
//        sourceNote = in.readString();
//    }