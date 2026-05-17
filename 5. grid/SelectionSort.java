import static java.lang.System.*;
import java.util.*;

class Student{
    int id;
    String name;
    float marks;

    Student(int id, String name, float marks){
        this.id=id;
        this.name=name;
        this.marks=marks;
    }

    Student(Student s){
        this.id = s.id;
        this.name = s.name;
        this.marks = s.marks;
    }
}

public class SelectionSort {
    public static List<Student> Students;

    public static void sort(List<Student> students, int order){
        int selectedIndex;

        for(int i = 0; i < students.size(); i++){
            selectedIndex = i;

            for(int j = i + 1; j < students.size(); j++){
                if(order == 0){ // ascending
                    if(students.get(j).marks < students.get(selectedIndex).marks){
                        selectedIndex = j;
                    }
                }
                else { // descending
                    if(students.get(j).marks > students.get(selectedIndex).marks){
                        selectedIndex = j;
                    }
                }
            }

            if(selectedIndex != i){
                Student temp = students.get(i);
                students.set(i, students.get(selectedIndex));
                students.set(selectedIndex, temp);
            }
        }
    }

    public static void display(List<Student> students){
        if(!students.isEmpty()){
            out.printf("%-5s %-15s %-10s\n", "Id", "Name", "Marks");
            for (Student s : students) {
                out.printf("%-5d %-15s %-10f\n", s.id, s.name, s.marks);
            }
        }
    }

    public static void main(String[] args){
        Students=new ArrayList<>();
        Scanner sc=new Scanner(in);

        out.println("How many students do you want to add in the System??");
        int n=sc.nextInt();
        out.println();

        for(int i=0; i<n; i++){
            out.print("Enter student id: ");
            int id=sc.nextInt();
            out.print("Enter student name: ");
            String name=sc.next();
            out.print("Enter student marks: ");
            float marks=sc.nextFloat();

            Students.add(new Student(id, name, marks));
            out.println();
        }

        display(Students);

        out.print("\n\nIn which order do you want to sort(0 for asc, 1 for desc)??: ");
        int order=sc.nextInt();

        out.println("\nSort by marks:-");
        sort(Students, order);
        display(Students);
    }
}
