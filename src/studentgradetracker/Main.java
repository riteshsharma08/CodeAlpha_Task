/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentgradetracker;

import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args){
        int choice;
        
        do{
            System.out.println("\n=====================================");
            System.out.println("       STUDENT GRADE TRACKER    ");
            System.out.println("\n=====================================");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Calculate Statistics");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            choice= scanner.nextInt();
            scanner.nextLine();
            
            switch(choice){
                
                  case 1:
                    addStudents();
                    break;

                case 2:
                    displayStudents();
                    break;

                case 3:
                    calculateStatistics();
                    break;

                case 4:
                    System.out.println("Thank you for using Student Grade Tracker!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }  
        }while( choice != 4);
        scanner.close();
    }
    // Add Student
    public static void addStudents(){
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Marks: ");
        double marks = scanner.nextDouble();
        
        if(marks < 0 || marks > 100){
            System.out.print("Marks must be between 0 to 100.");
        }
        Student student = new Student(name, marks);
        students.add(student);
         System.out.println("Student added successfully!");
    }
    //Display Student
    public static void displayStudents(){
        if(students.isEmpty()){
            System.out.println("No students available.");
            return;
        }
        System.out.println("\n========== STUDENT REPORT ==========");
        System.out.printf("%-20s %s%n", "Student Name", "Marks");
        System.out.println("------------------------------------");
        
        for (Student student : students) {
            System.out.printf(
                    "%-20s %.2f%n",
                    student.getName(),
                    student.getMarks()
            );
        }
        
    }
    // Calculate average, highest and lowest
     public static void calculateStatistics(){
          if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        double total = 0;
        double highest = students.get(0).getMarks();
        double lowest = students.get(0).getMarks();
        
         for (Student student : students) {

            double marks = student.getMarks();

            total = total + marks;

            if (marks > highest) {
                highest = marks;
            }

            if (marks < lowest) {
                lowest = marks;
            }
        }
          double average = total / students.size();

        System.out.println("\n========== STATISTICS ==========");
        System.out.printf("Average Marks: %.2f%n", average);
        System.out.printf("Highest Marks: %.2f%n", highest);
        System.out.printf("Lowest Marks : %.2f%n", lowest);
     }
    
}
