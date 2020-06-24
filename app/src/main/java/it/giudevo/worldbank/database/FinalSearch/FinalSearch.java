package it.giudevo.worldbank.database.FinalSearch;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FinalSearch implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "countryiso3code")
    public String countryiso3code;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name ="value")
    public String value;
    @ColumnInfo(name="unit")
    public String unit;
    @ColumnInfo(name="obs_status")
    public String obs_status;
    @ColumnInfo(name = "decimal")
    public String decimal;

    public FinalSearch(String countryiso3code, String date, String value, String unit, String obs_status, String decimal) {
        this.countryiso3code = countryiso3code;
        this.date = date;
        this.value = value;
        this.unit = unit;
        this.obs_status = obs_status;
        this.decimal = decimal;
    }

    protected FinalSearch(Parcel in) {
        id = in.readInt();
        countryiso3code = in.readString();
        date = in.readString();
        value = in.readString();
        unit = in.readString();
        obs_status = in.readString();
        decimal = in.readString();
    }

    public static final Creator<FinalSearch> CREATOR = new Creator<FinalSearch>() {
        @Override
        public FinalSearch createFromParcel(Parcel in) {
            return new FinalSearch(in);
        }

        @Override
        public FinalSearch[] newArray(int size) {
            return new FinalSearch[size];
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
        dest.writeString(date);
        dest.writeString(value);
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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