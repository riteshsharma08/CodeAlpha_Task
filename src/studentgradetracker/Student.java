/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package studentgradetracker;

/**
 *
 * @author user
 */
public class Student {
    private String name;
    private double marks;
    
    public Student(String name, double marks){
        this.name = name;
        this.marks =marks;
    }
    public String getName(){
        return name;
    }
    public double getMarks(){
        return marks;
    }
}
