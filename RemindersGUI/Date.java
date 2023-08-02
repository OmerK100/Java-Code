/* Date class represents a calendar date using a day, month and a year. */
public class Date {

    private int day; /* Attributes. */
    private int month;
    private int year;
    private final int HASH_MULTIPLIER = 31; /* Final. */

    public Date(int day , int month , int year) { /* Constructor with parameters. */
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String toString() { /* toString returns a string representation of the date. */
        return this.day + "." + this.month + "." + this.year;
    }

    public boolean equals(Object obj) { /* equals checks whether two date objects are the same. */
        if (!(obj instanceof Date)) /* Checking whether the parameter is a kind of Date object. */
            return false;
        if (obj == this)
            return true;
        Date other = (Date)obj; /* Comparing attributes. */
        if (this.day == other.day && this.month == other.month && this.year == other.year) {
            return true;
        }

        return false;
    }

    public int hashCode() {
        /* Re-writing the hashCode for each Date object in order to be able to hash different Date object with same attributes to same table slots. */
        return HASH_MULTIPLIER * (this.day + this.month + HASH_MULTIPLIER * this.year);
    }

}
// End of class Date.