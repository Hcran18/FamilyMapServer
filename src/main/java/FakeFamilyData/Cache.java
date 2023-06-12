package FakeFamilyData;

/**
 * The Cache class represents a cache for storing location data, male first names data,
 * female first names data, and surnames data.
 */
public class Cache {
    /**
     * The cached location data.
     */
    private static LocationData locations;

    /**
     * The cached male first names data.
     */
    private static MNamesData maleNames;

    /**
     * The cached female first names data.
     */
    private static FNamesData femaleNames;

    /**
     * The cached surnames data.
     */
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
