package com.fm.alSoukBk.enums;

public enum RegionCode {
    TN11("Tunis"),
    TN12("Ariana"),
    TN13("Ben Arous"),
    TN14("Manouba"),
    TN21("Nabeul"),
    TN22("Zaghouan"),
    TN23("Bizerte"),
    TN31("Béja"),
    TN32("Jendouba"),
    TN33("Le Kef"),
    TN34("Siliana"),
    TN41("Kairouan"),
    TN42("Kasserine"),
    TN43("Sidi Bou Zid"),
    TN51("Sousse"),
    TN52("Monastir"),
    TN53("Mahdia"),
    TN61("Sfax"),
    TN71("Gafsa"),
    TN72("Tozeur"),
    TN73("Kebili"),
    TN81("Gabès"),
    TN82("Médenine"),
    TN83("Tataouine");

    private final String nom;

    RegionCode(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return this.name();
    }

    public String getNom() {
        return nom;
    }

    public static RegionCode fromCode(String code) {
        return RegionCode.valueOf(code);
    }

    public static RegionCode fromNom(String nom) {
        for (RegionCode region : RegionCode.values()) {
            if (region.nom.equals(nom)) {
                return region;
            }
        }
        throw new IllegalArgumentException("Nom de région invalide : " + nom);
    }

    @Override
    public String toString() {
        return nom;
    }
}