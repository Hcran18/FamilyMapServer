package FakeFamilyData;

public class Cache {
    private static LocationData locations;
    private static MNamesData maleNames;
    private static FNamesData femaleNames;
    private static SNamesData surnames;

    public static LocationData getLocations() {
        return locations;
    }

    public static void setLocations(LocationData locations) {
        Cache.locations = locations;
    }

    public static MNamesData getMaleNames() {
        return maleNames;
    }

    public static void setMaleNames(MNamesData maleNames) {
        Cache.maleNames = maleNames;
    }

    public static FNamesData getFemaleNames() {
        return femaleNames;
    }

    public static void setFemaleNames(FNamesData femaleNames) {
        Cache.femaleNames = femaleNames;
    }

    public static SNamesData getSurnames() {
        return surnames;
    }

    public static void setSurnames(SNamesData surnames) {
        Cache.surnames = surnames;
    }
}
