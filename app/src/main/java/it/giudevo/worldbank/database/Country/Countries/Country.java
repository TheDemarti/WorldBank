package it.giudevo.worldbank.database.Country.Countries;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Country implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "number")
    public int number;
    @ColumnInfo(name = "id")
    public String id;
    @ColumnInfo(name = "iso2Code")
    public String iso2Code;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name ="capitalCity")
    public String capitalCity;
    @ColumnInfo(name="longitude")
    public String longitude;
    @ColumnInfo(name="latitude")
    public String latitude;

    public Country(String id, String iso2Code, String name, String capitalCity, String longitude, String latitude) {
        this.id = id;
        this.iso2Code = iso2Code;
        this.name = name;
        this.capitalCity = capitalCity;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    protected Country(Parcel in) {
        number = in.readInt();
        id = in.readString();
        iso2Code = in.readString();
        name = in.readString();
        capitalCity = in.readString();
        longitude = in.readString();
        latitude = in.readString();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(id);
        dest.writeString(iso2Code);
        dest.writeString(name);
        dest.writeString(capitalCity);
        dest.writeString(longitude);
        dest.writeString(latitude);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso2Code() {
        return iso2Code;
    }

    public void setIso2Code(String iso2Code) {
        this.iso2Code = iso2Code;
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
}
