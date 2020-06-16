package it.giudevo.worldbank.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
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
    public ArrayList<Template> templateList;

    public ArrayList<Template> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(ArrayList<Template> templateList) {
        this.templateList = templateList;
    }

    public Arguments(){

    }

    protected Arguments(Parcel in) {
        this.page = (Integer) in.readInt();
        this.pages = in.readInt();
        this.per_page = in.readString();
        this.total = in.readInt();
        this.templateList = in.readArrayList((ClassLoader) Template.CREATOR);
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
    @Entity(tableName = "Template")
    class Template implements Parcelable {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        private int id;
        @ColumnInfo(name = "value")
        private String value;
        @ColumnInfo(name = "sourceNote")
        private String sourceNote;

        protected Template(Parcel in) {
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
    }