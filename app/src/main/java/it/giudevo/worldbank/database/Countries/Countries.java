package it.giudevo.worldbank.database.Countries;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Countries implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "iso2code")
    public String iso2code;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name ="capitalCity")
    public String capitalCity;
    @ColumnInfo(name="longitude")
    public String longitude;
    @ColumnInfo(name="latitude")
    public String latitude;

    public Countries(int id, String iso2code, String name, String capitalCity, String longitude, String latitude) {
        this.id = id;
        this.iso2code = iso2code;
        this.name = name;
        this.capitalCity = capitalCity;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    protected Countries(Parcel in) {
        id = in.readInt();
        iso2code = in.readString();
        name = in.readString();
        capitalCity = in.readString();
        longitude = in.readString();
        latitude = in.readString();
    }

    public static final Creator<it.giudevo.worldbank.database.Countries.Countries>
            CREATOR = new Creator<it.giudevo.worldbank.database.Countries.Countries>() {
        @Override
        public it.giudevo.worldbank.database.Countries.Countries createFromParcel(Parcel in) {
            return new it.giudevo.worldbank.database.Countries.Countries(in);
        }

        @Override
        public it.giudevo.worldbank.database.Countries.Countries[] newArray(int size) {
            return new it.giudevo.worldbank.database.Countries.Countries[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIso2code() {
        return iso2code;
    }

    public void setIso2code(String iso2code) {
        this.iso2code = iso2code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(iso2code);
        dest.writeString(name);
        dest.writeString(capitalCity);
        dest.writeString(longitude);
        dest.writeString(latitude);
    }
}