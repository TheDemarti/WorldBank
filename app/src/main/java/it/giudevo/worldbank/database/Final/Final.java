package it.giudevo.worldbank.database.Final;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Final implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "countryiso3code")
    public String countryiso3code;
    @ColumnInfo(name = "date")
    public int date;
    @ColumnInfo(name ="value")
    public float value;
    @ColumnInfo(name="unit")
    public String unit;
    @ColumnInfo(name="obs_status")
    public String obs_status;
    @ColumnInfo(name = "decimal")
    public String decimal;

    public Final(String countryiso3code, int date, float value, String unit, String obs_status, String decimal) {
        this.countryiso3code = countryiso3code;
        this.date = date;
        this.value = value;
        this.unit = unit;
        this.obs_status = obs_status;
        this.decimal = decimal;
    }

    protected Final(Parcel in) {
        id = in.readInt();
        countryiso3code = in.readString();
        date = in.readInt();
        value = in.readInt();
        unit = in.readString();
        obs_status = in.readString();
        decimal = in.readString();
    }

    public static final Creator<Final> CREATOR = new Creator<Final>() {
        @Override
        public Final createFromParcel(Parcel in) {
            return new Final(in);
        }

        @Override
        public Final[] newArray(int size) {
            return new Final[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(countryiso3code);
        dest.writeInt(date);
        dest.writeFloat(value);
        dest.writeString(unit);
        dest.writeString(obs_status);
        dest.writeString(decimal);
    }

    public String getCountryiso3code() {
        return countryiso3code;
    }

    public void setCountryiso3code(String countryiso3code) {
        this.countryiso3code = countryiso3code;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getObs_status() {
        return obs_status;
    }

    public void setObs_status(String obs_status) {
        this.obs_status = obs_status;
    }

    public String getDecimal() {
        return decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }
//    public Countries(int id, String iso2code, String name, String capitalCity, String longitude, String latitude) {
//        this.id = id;
//        this.iso2code = iso2code;
//        this.name = name;
//        this.capitalCity = capitalCity;
//        this.longitude = longitude;
//        this.latitude = latitude;
//    }
//
//    protected Countries(Parcel in) {
//        id = in.readInt();
//        iso2code = in.readString();
//        name = in.readString();
//        capitalCity = in.readString();
//        longitude = in.readString();
//        latitude = in.readString();
//    }
//
//    public static final Creator<it.giudevo.worldbank.database.Countries.Countries>
//            CREATOR = new Creator<it.giudevo.worldbank.database.Countries.Countries>() {
//        @Override
//        public it.giudevo.worldbank.database.Countries.Countries createFromParcel(Parcel in) {
//            return new it.giudevo.worldbank.database.Countries.Countries(in);
//        }
//
//        @Override
//        public it.giudevo.worldbank.database.Countries.Countries[] newArray(int size) {
//            return new it.giudevo.worldbank.database.Countries.Countries[size];
//        }
//    };
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getIso2code() {
//        return iso2code;
//    }
//
//    public void setIso2code(String iso2code) {
//        this.iso2code = iso2code;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getCapitalCity() {
//        return capitalCity;
//    }
//
//    public void setCapitalCity(String capitalCity) {
//        this.capitalCity = capitalCity;
//    }
//
//    public String getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(String longitude) {
//        this.longitude = longitude;
//    }
//
//    public String getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(String latitude) {
//        this.latitude = latitude;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeString(iso2code);
//        dest.writeString(name);
//        dest.writeString(capitalCity);
//        dest.writeString(longitude);
//        dest.writeString(latitude);
//    }
}