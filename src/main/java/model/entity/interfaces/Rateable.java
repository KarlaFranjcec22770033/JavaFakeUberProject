package model.entity.interfaces;

/**
 * omogucuje ocjenjivanje i racunanje prosjescne ocjene vozaca
 */
public sealed interface Rateable permits Driver {
    /**
     * dodaje novu ocjenu
     * @param rating nova ocjena
     */
    void getRating(int rating);

    /**
     * racuna prosjecnu ocjenu
     * @return prosjecna ocjena(izracun avgRating dijeljno sa ratingCount)
     */
    double getAvgRating();
}
