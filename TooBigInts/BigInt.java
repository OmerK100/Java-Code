import java.util.ArrayList;

/* Class BigInt represents integers using ArrayLists and stores arithmetic functions to use on these integers. */

public class BigInt implements Comparable<BigInt> {

    private ArrayList bigInt;
    /* ArrayList attribute to store the integer. We use an ArrayList because regular integer data types are very limited and ArrayList can store much larger ints. */
    private boolean isPositive; /* Attribute to know whether the BigInt represents a positive or a negative integer. */

    public BigInt(String num) { /* Builder that receives a string that represents an integer (with a sign) and puts it within the object's ArrayList. */
        this.bigInt = new ArrayList();
        if (this.checkCorrectInt(num) == false) { /* Checking whether the string represents a legal integer, else we throw an exception. */
            throw new IllegalArgumentException("String parameter doesn't represent an integer.");
        }
        if (num.charAt(0) == '+') { /* Checking the sign at the beginning of the integer and adding it to BigInt accordingly. */
            isPositive = true;
            this.bigInt.add('+');
        }
        else {
            isPositive = false;
            this.bigInt.add('-');
        }
        int i;
        for (i = 1 ; i < num.length() - 1 ; i++) {
            /* If there are extra unnecessary 0's in the beginning of the number we remove them, for example: +000357 should be +357 and +000 should be +0. */
            if (charToInt(num.charAt(i)) != 0) {
                break;
            }
            if (i == num.length() - 2 && charToInt(num.charAt(num.length() - 1)) == 0) {
                /* Making sure if there are only 0's not to accidentally delete the all of them and add a plus sign, for example: -00 should be +0 and not -. */
                isPositive = true;
                this.bigInt.set(0 , '+');
            }
        }
        for (int j = i ; j < num.length() ; j++) { /* If the rest of the string represents an int correctly we just add its digits to the BigInt. */
            this.bigInt.add((charToInt(num.charAt(j))));
        }
    }

    private BigInt(ArrayList arr) { /* Private copy builder that is used to create auxiliary BigInts for the arithmetic functions. */
        this.bigInt = new ArrayList();
        for (int i = 0 ; i < arr.size() ; i++) { /* Copying the ArrayList parameter to this ArrayList. */
            this.bigInt.add(arr.get(i));
        }
        if ((char)arr.get(0) == '+') { /* Checking the sign. */
            isPositive = true;
        }
        else {
            isPositive = false;
        }
    }

    public BigInt plus(BigInt other) { /* plus function returns the sum of two BigInt as another BigInt. */
        ArrayList sum = new ArrayList(); /* Will represent the reversed sum. */
        ArrayList sum2 = new ArrayList(); /* Will represent the final sum. */
        ArrayList rev1 = new ArrayList(); /* Reverse of this ArrayList. */
        ArrayList rev2 = new ArrayList(); /* Reverse of other ArrayList. */
        if (this.isPositive == true && other.isPositive == false) {
            /* A sum of this as positive and other as negative is the same as the subtraction of both this and other being positive. */
            other.bigInt.set(0 , '+');
            other.isPositive = true;

            return this.minus(other);
        }
        else if (this.isPositive == false && other.isPositive == true) {
            /* A sum of this as negative and other as positive is the same as the opposite subtraction of both this and other being positive. */
            this.bigInt.set(0 , '+');
            this.isPositive = true;

            return other.minus(this);
        }
        rev1 = this.reverse(rev1); /* In this case we have to create the sum manually, we begin with reversing the BigInts. */
        rev2 = other.reverse(rev2);
        if (rev1.size() < rev2.size()) { /* If sizes are different we take the smaller BigInt and add extra 0's to make the sizes the same. */
            int i = rev1.size() - 1;
            while (rev1.size() <= rev2.size() - 1) {
                rev1.add(i , 0);
            }
        }
        else if ((rev1.size() > rev2.size())) {
            int i = rev2.size() - 1;
            while (rev2.size() <= rev1.size() - 1) {
                rev2.add(i , 0);
            }
        }
        int temp = 0;
        if (this.isPositive == true && other.isPositive == true) { /* Sum of both positive BigInts. */
            for (int i = 0 ; i < rev1.size() - 1 ; i++) { /* Vertical sum of both BigInts. */
                sum.add(((int)rev1.get(i) + (int)rev2.get(i) + temp) % 10);
                /* Summing each digit from this and other from beginning to end and using a temp variable according to vertical sum rules. */
                temp = ((int)rev1.get(i) + (int)rev2.get(i) + temp) / 10;
                if (i == rev1.size() - 2) {
                    sum.add(temp);
                }
            }
            sum.add('+'); /* The sum is positive. */
        }
        else if (this.isPositive == false && other.isPositive == false) { /* Sum of both negative BigInts. */
            for (int i = 0 ; i < rev1.size() - 1 ; i++) { /* Vertical sum of both BigInts. */
                sum.add(((int)rev1.get(i) + (int)rev2.get(i) + temp) % 10);
                /* Summing each digit from this and other from beginning to end and using a temp variable according to vertical sum rules. */
                temp = ((int)rev1.get(i) + (int)rev2.get(i) + temp) / 10;
                if (i == rev1.size() - 2) {
                    sum.add(temp);
                }
            }
            sum.add('-'); /* The sum is negative. */
        }
        for (int i = sum.size() - 2 ; i >= 1 ; i--) { /* Removing extra unnecessary 0's if there are in the sum. */
            if ((int)sum.get(i) == 0) {
                sum.remove(i);
            }
            else {
                break;
            }
        }
        for (int i = sum.size() - 1 ; i >= 0 ; i--) { /* Reversing the sum to its correct form. */
            sum2.add(sum.get(i));
        }
        BigInt big = new BigInt(sum2); /* Creating the BigInt to return and checking whether its zero to add the '+' sign if needed. */
        if (big.isZero() == true) {
            big.bigInt.set(0 , '+');
        }

        return big; /* Returning the sum BigInt. */
    }

    public BigInt minus(BigInt other) { /* minus function returns the subtraction of two BigInt as another BigInt. */
        ArrayList diff = new ArrayList(); /* Will represent the reversed subtraction. */
        ArrayList diff2 = new ArrayList(); /* Will represent the final subtraction. */
        ArrayList rev1 = new ArrayList(); /* Reverse of this ArrayList. */
        ArrayList rev2 = new ArrayList(); /* Reverse of other ArrayList. */
        if (this.isPositive == true && other.isPositive == false) {
            /* A subtraction of this as positive and other as negative is the same as the sum of both this and other being positive. */
            other.bigInt.set(0 , '+');
            other.isPositive = true;
            return this.plus(other);
        }
        else if (this.isPositive == false && other.isPositive == true) {
            /* A sum of this as negative and other as positive is the same as the sum of both this and other being negative. */
            other.bigInt.set(0 , '-');
            other.isPositive = false;
            return other.plus(this);
        }
        rev1 = this.reverse(rev1); /* In this case we have to create the subtraction manually, we begin with reversing the BigInts. */
        rev2 = other.reverse(rev2);
        if (rev1.size() < rev2.size()) { /* If sizes are different we take the smaller BigInt and add extra 0's to make the sizes the same. */
            int i = rev1.size() - 1;
            while (rev1.size() <= rev2.size() - 1) {
                rev1.add(i , 0);
            }
        }
        else if ((rev1.size() > rev2.size())) {
            int i = rev2.size() - 1;
            while (rev2.size() <= rev1.size() - 1) {
                rev2.add(i , 0);
            }
        }
        if (this.isPositive == true && other.isPositive == true) { /* Subtraction of both positive BigInts. */
            if (this.otherBiggerABS(rev1 , rev2 , 1) == true) {
                /* If other BigInt is larger we return the minus of the opposite subtraction because we have an option to subtract a smaller number from a bigger number.  */
                ArrayList backRev = new ArrayList();
                /* Reversing back the ArrayLists to correct form, adding them to auxiliary Bigint objects, subtracting the opposite and adding a '-' sign. */
                for (int i = rev1.size() - 1 ; i >= 0 ; i--) {
                    backRev.add(rev1.get(i));
                }
                BigInt dummy1 = new BigInt(backRev);
                backRev.clear();
                for (int i = rev2.size() - 1 ; i >= 0 ; i--) {
                    backRev.add(rev2.get(i));
                }
                BigInt dummy2 = new BigInt(backRev);
                BigInt dummy3 = dummy2.minus(dummy1); /* Opposite subtraction. */
                dummy3.bigInt.set(0 , '-');
                dummy3.isPositive = false;

                return dummy3;
            }
            else { /* If this is larger than other we subtract them vertically. */
                for (int i = 0 ; i < rev1.size() - 1 ; i++) {
                    /* Subtracting each digit of other from this from beginning to end and if this's digit is smaller than other's, we have to increase it by lowering a
                    further digit of this according to vertical subtraction rules. */
                    if ((int)rev1.get(i) < (int)rev2.get(i)) {
                        rev1.set(i , (int)rev1.get(i) + 10); /* Increasing this's digit. */
                        int j = i + 1;
                        while ((int)rev1.get(j) == 0) { /* Looking for first non-zero digit after the one we reached and lowering it by 1. */
                            rev1.set(j , 9);
                            j++;
                        }
                        if ((int)rev1.get(j) == 1) {
                            rev1.set(j , 0);
                        }
                        else {
                            rev1.set(j , (int)rev1.get(j) - 1);
                        }
                    }
                    diff.add((int)rev1.get(i) - (int)rev2.get(i)); /* Adding the digit subtraction to the subtraction of the BigInts. */
                }
                for (int i = diff.size() - 1 ; i >= 1 ; i--) { /* Removing extra unnecessary 0's added to the subtraction. */
                    if ((int)diff.get(i) == 0) {
                        diff.remove(i);
                    }
                    else {
                        break;
                    }
                }
                diff.add('+'); /* Subtraction is positive. */
            }
        }
        else if (this.isPositive == false && other.isPositive == false) { /* Subtraction of both negative BigInts. */
            /* Subtracting a negative from a negative is making both this and other positive and subtracting this from other the way we did above. */
            rev1.set(rev1.size() - 1 , '+'); /* Making both BigInts positive. */
            rev2.set(rev2.size() - 1 , '+');
            ArrayList backRev = new ArrayList(); /* Reversing the BigInts to their correct form and adding them to auxiliary BigInt objects. */
            for (int i = rev1.size() - 1 ; i >= 0 ; i--) {
                backRev.add(rev1.get(i));
            }
            BigInt dummy1 = new BigInt(backRev);
            backRev.clear();
            for (int i = rev2.size() - 1 ; i >= 0 ; i--) {
                backRev.add(rev2.get(i));
            }
            BigInt dummy2 = new BigInt(backRev);
            BigInt dummy3 = dummy2.minus(dummy1); /* Creating and returning the subtraction of this from other (as positives). */

            return dummy3;
        }
        for (int i = diff.size() - 1 ; i >= 0 ; i--) { /* Reversing the subtraction to its correct form. */
            diff2.add(diff.get(i));
        }
        BigInt big = new BigInt(diff2); /* Creating the BigInt to return and checking whether its zero to add the '+' sign if needed. */
        if (big.isZero() == true) {
            big.bigInt.set(0 , '+');
        }

        return big; /* Returning the subtraction BigInt. */
    }

    public BigInt multiply(BigInt other) { /* multiply function returns the product of two BigInts as another BigInt. */
        ArrayList mult = new ArrayList(); /* Stores reversed products. */
        ArrayList mult2 = new ArrayList(); /* Stores non-reversed products. */
        ArrayList rev1 = new ArrayList(); /* Reverses of this and other. */
        ArrayList rev2 = new ArrayList();
        rev1 = this.reverse(rev1);
        rev2 = other.reverse(rev2);
        if (rev1.size() < rev2.size()) { /* If sizes are different we take the smaller BigInt and add extra 0's to make the sizes the same. */
            int i = rev1.size() - 1;
            while (rev1.size() <= rev2.size() - 1) {
                rev1.add(i , 0);
            }
        }
        else if ((rev1.size() > rev2.size())) {
            int i = rev2.size() - 1;
            while (rev2.size() <= rev1.size() - 1) {
                rev2.add(i , 0);
            }
        }
        BigInt sum = new BigInt("+0"); /* BigInt that stores the sum of all the multiplication according to vertical product rules. */
        int addZero = 0; /* Counts how many 0's we need to add in the beginning of a product we calculate, increases by 1 with each iteration. */
        for (int i = 0 ; i < rev2.size() - 1 ; i++) {
            /* We go over each digit from beginning to end of other, multiply and sum up other's digit with each one of this's digits. */
            int temp = 0;
            mult.clear(); /* mult and mult2 store the sum of multiplying this's digit with each digit of other, we clear them up for each one of other's digits. */
            mult2.clear();
            for (int z = 1 ; z <= addZero ; z++) {
                /* By the rules of vertical product with each new digit that belongs to other, we need to add another zero in the beginning of the current sum. */
                mult.add(0);
            }
            for (int j = 0 ; j < rev1.size() - 1 ; j++) {
                /* Running through this and multiplying each digit with the current other digit and also using a temp according to vertical product rules. */
                mult.add(((int)rev1.get(j) * (int)rev2.get(i) + temp) % 10);
                temp = ((int)rev1.get(j) * (int)rev2.get(i) + temp) / 10;
                if (j == rev1.size() - 2) {
                    mult.add(temp);
                }
            }
            mult.add('+'); /* Each product we add to the total sum is positive, we'll figure out if the final product is negative by the end. */
            for (int q = mult.size() - 2 ; q >= 1 ; q--) { /* Removing extra unnecessary 0's. */
                if ((int)mult.get(q) == 0) {
                    mult.remove(q);
                }
                else {
                    break;
                }
            }
            for (int l = mult.size() - 1 ; l >= 0 ; l--) { /* Reversing each product back to correct form. */
                mult2.add(mult.get(l));
            }
            BigInt dummy = new BigInt(mult2); /* Adding each product with each one of other's digits to the total sum and continuing to calculate and sum next products. */
            sum = sum.plus(dummy); /* dummy holds the product we calculated as a BigInt and we use the plus function to add it to sum. */
            addZero++;
        }
        if ((this.isPositive == true && other.isPositive == true) || (this.isPositive == false && other.isPositive == false) || sum.isZero() == true) {
            /* Deciding whether the final product is negative or positive according to product rules. */
            sum.bigInt.set(0 , '+');
            sum.isPositive = true;
        }
        else {
            sum.bigInt.set(0 , '-');
            sum.isPositive = false;
        }

        return sum; /* Returning the final product. */
    }

    public BigInt divide(BigInt other) { /* divide function returns the division of two BigInts as another BigInt. */
        if (other.isZero() == true) { /* If other represents a 0 it means we try divide by 0 and throw and exception. */
            throw new ArithmeticException("Attempt to divide by a BigInt that represents 0.");
        }
        BigInt sum = new BigInt("+0"); /* Will store the final division. */
        BigInt div = new BigInt("+1"); /* Stores each round of the division and summed up to sum. */
        BigInt otherTemp = new BigInt(other.bigInt); /* Copy of other object. */
        BigInt temp = new BigInt(this.bigInt); /* Copy of this. */
        otherTemp.bigInt.set(0 , '+'); /* Turning other's number into positive no matter its sign for easier calculation. */
        otherTemp.isPositive = true;
        temp.bigInt.set(0 , '+'); /* Turning this's number into positive no matter its sign for easier calculation. */
        temp.isPositive = true;
        while (this.otherBiggerABS(temp.bigInt , other.bigInt , 2) == false) {
		/* Our division calculation must return an integer, so what we do is we use a loop to check how many times we could fill up this's number
		(the divided) using other's number (the divider) and with each iteration we increase sum by 1 which means that the divider could fill this's number
		another time, then we subtract the divider from what's left of this's number until this's remaining is smaller than the divider. */
            sum = sum.plus(div);
            temp = temp.minus(otherTemp);
        }
        if ((this.isPositive == true && other.isPositive == true) || (this.isPositive == false && other.isPositive == false) || sum.isZero() == true) {
            /* Deciding whether the final division is negative or positive according to division rules. */
            sum.bigInt.set(0 , '+');
            sum.isPositive = true;
        }
        else {
            sum.bigInt.set(0 , '-');
            sum.isPositive = false;
        }

        return sum; /* Returning the final division. */
    }

    public String toString() { /* toString returns a string that represents the BigInt according to its ArrayList. */
        String str = new String("");
        for (int i = 0 ; i < this.bigInt.size() ; i++) { /* Adding each digit to the string. */
            str = str + this.bigInt.get(i);
        }

        return str;
    }

    public boolean equals(BigInt other) { /* equals receives two BigInts and returns whether they represent the same integer or not. */
        if (this.isZero() == true && other.isZero() == true) { /* Checking whether both are 0. */
            return true;
        }
        if (this.bigInt.size() != other.bigInt.size()) { /* Comparing sizes. */
            return false;
        }
        for (int i = 0 ; i < this.bigInt.size() ; i++) { /* If sizes are the same we compare them digit by digit. */
            if (this.bigInt.get(i) != other.bigInt.get(i)) {
                return false;
            }
        }

        return false;
    }

    public int compareTo(BigInt other) { /* compareTo is a function received from the Comparable interface and compares two BigInts. */
        if ((char)this.bigInt.get(0) == '+' && (char)other.bigInt.get(0) == '-') { /* Checking if this is larger by comparing signs. */
            return 1;
        }
        else if ((char)this.bigInt.get(0) == '-' && (char)other.bigInt.get(0) == '+') { /* Checking if this is smaller by comparing signs. */
            return -1;
        }
        if (this.equals(other) == true) { /* Using equals function to check whether both BigInts are the same. */
            return 0;
        }
        else if ((char)this.bigInt.get(0) == '+') { /* If both are positive we compare them as positive ints. */
            if (this.bigInt.size() > other.bigInt.size()) { /* Comparing sizes. */
                return 1;
            }
            else if (this.bigInt.size() < other.bigInt.size()) {
                return -1;
            }
            else {
                for (int i = 1 ; i < this.bigInt.size() ; i++) { /* If sizes are the same we compare them digit by digit. */
                    if ((int)this.bigInt.get(i) > (int)other.bigInt.get(i)) {
                        return 1;
                    }
                    else if ((int)this.bigInt.get(i) < (int)other.bigInt.get(i)) {
                        return -1;
                    }
                }
            }
        } /* In this last case both BigInts are negative, we compare them as negative ints. */
        if (this.bigInt.size() > other.bigInt.size()) { /* Comparing sizes. */
            return -1;
        }
        else if (this.bigInt.size() < other.bigInt.size()) {
            return 1;
        }
        for (int i = 1 ; i < this.bigInt.size() ; i++) { /* If sizes are the same we compare them digit by digit. */
            if ((int)this.bigInt.get(i) > (int)other.bigInt.get(i)) {
                return -1;
            }
            else if ((int)this.bigInt.get(i) < (int)other.bigInt.get(i)) {
                return 1;
            }
        }

        return 0;
    }

    private boolean isZero() { /* Utility function that checks whether the BigInt represents 0. */
        for (int i = 1 ; i < this.bigInt.size() ; i++) {
            if ((int)this.bigInt.get(i) != 0) {
                return false;
            }
        }

        return true;
    }

    private ArrayList reverse(ArrayList rev) { /* Utility function that receives a BigInt and reverses its ArrayList. */
        int j = 0;
        for (int i = this.bigInt.size() - 1 ; i >= 0 ; i--) {
            rev.add(this.bigInt.get(i));
        }

        return rev;
    }

    private int charToInt(char a) { /* Utility function that receives a char digit and returns its integer representation. */
        return a - '0'; /* Trick according to ascii values. */
    }

    private boolean otherBiggerABS(ArrayList arr1 , ArrayList arr2 , int which) { /* Utility function to compare ABS values of two BigInts. */
        if (arr1.size() > arr2.size()) { /* Comparing sizes. */
            return false;
        }
        else if (arr1.size() < arr2.size()) {
            return true;
        }
        else { /* If sizes are same, comparing each digit. */
            if (which == 1) { /* which == 1 case means that the BigInts received are reversed hence we compare them from the end. */
                for (int i = arr1.size() - 2 ; i >= 0 ; i--) {
                    if ((int)arr1.get(i) < (int)arr2.get(i)) {
                        return true;
                    }
                    else if ((int)arr1.get(i) > (int)arr2.get(i)) {
                        return false;
                    }
                }
            }
            else if (which == 2) { /* which == 2 case means that the BigInts received are not reversed hence we compare them from the beginning. */
                for (int i = 1 ; i < arr1.size() ; i++) {
                    if ((int)arr1.get(i) < (int)arr2.get(i)) {
                        return true;
                    }
                    else if ((int)arr1.get(i) > (int)arr2.get(i)) {
                        return false;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkCorrectInt(String num) { /* Utility method to check whether the string parameter for the builder represents a legal integer. */
        if (num.charAt(0) != '-' && num.charAt(0) != '+') { /* Looking for a sign in the beginning. */
            return false;
        }
        for (int i = 1 ; i < num.length() ; i++) { /* Making sure each character is a digit. */
            if (Character.isDigit(num.charAt(i)) != true) {
                return false;
            }
        }

        return true;
    }

}
// End of class BigInt.