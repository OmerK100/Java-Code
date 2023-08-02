/* Student class represents a student by using identifying attributes such as name and ID. */
public class Student implements Comparable<Student> {

    private String name; /* Attributes. */
    private String ID;
    private int grade;
    private final int BIGGER = 1; /* Finals. */
    private final int SMALLER = -1;
    private final int EQUAL = 0;

    public Student(String name , String ID , int grade) { /* Constructor with parameters. */
        this.grade = grade;
        this.ID = ID;
        this.name = name;
    }

    public int compareTo(Student other) { /* Implementing the compareTo function that compares students through their grade attribute. */
        if (this.grade > other.grade) { /* Larger grade - larger student, and vice versa. */
            return BIGGER;
        }
        else if (this.grade < other.grade) {
            return SMALLER;
        }

        return EQUAL;
    }

    public String toString() { /* String representation of the Student object with its attributes. */
        return "Student's details: Name - " + this.name + ", ID - " + this.ID + ", Grade - " + this.grade;
    }

    public boolean equals(Student other) { /* equals function to check whether two students have the same grade. */
        if (this.grade == other.grade) {
            return true;
        }

        return false;
    }

}
// End of class Student.