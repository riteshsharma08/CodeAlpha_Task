package sgt;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class StudentGradeTracker extends JFrame implements ActionListener {

    // ================= Data Model =================

    static class Student {
        String name;
        double score;

        Student(String name, double score) {
            this.name = name;
            this.score = score;
        }
    }

    ArrayList<Student> studentList = new ArrayList<>();

    // ================= UI Components =================

    JLabel l1, l2, l3, l4, l5, l6, l7;
    JTextField t1, t2;

    JButton bAdd, bRemove, bSummary, bClear;

    JTable table;
    JScrollPane sp;
    DefaultTableModel model;

    JLabel lblTotal, lblAverage, lblHighest, lblLowest;


    // ================= Constructor =================

    StudentGradeTracker() {

        setTitle("Student Grade Tracker");
        setSize(800, 600);
        setLocation(0, 0);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        // ---------------- Input Fields ----------------

        l1 = new JLabel("Student Name:");
        l1.setBounds(20, 20, 120, 25);
        add(l1);

        t1 = new JTextField();
        t1.setBounds(150, 20, 200, 25);
        add(t1);

        l2 = new JLabel("Score:");
        l2.setBounds(370, 20, 60, 25);
        add(l2);

        t2 = new JTextField();
        t2.setBounds(430, 20, 100, 25);
        add(t2);


        // ---------------- Buttons ----------------

        bAdd = new JButton("Add Student");
        bAdd.setBounds(20, 60, 140, 30);
        add(bAdd);

        bRemove = new JButton("Remove Selected");
        bRemove.setBounds(170, 60, 160, 30);
        add(bRemove);

        bSummary = new JButton("Show Summary");
        bSummary.setBounds(340, 60, 150, 30);
        add(bSummary);

        bClear = new JButton("Clear All");
        bClear.setBounds(500, 60, 120, 30);
        add(bClear);


        // ---------------- Table ----------------

        model = new DefaultTableModel(
                new Object[]{"Sr No", "Name", "Score"}, 0
        );

        table = new JTable(model);
        sp = new JScrollPane(table);
        sp.setBounds(20, 110, 740, 300);
        add(sp);


        // ---------------- Summary Labels ----------------

        l4 = new JLabel("Summary Report");
        l4.setFont(new Font("Arial", Font.BOLD, 16));
        l4.setBounds(20, 420, 200, 25);
        add(l4);

        l5 = new JLabel("Total Students:");
        l5.setBounds(20, 455, 130, 25);
        add(l5);

        lblTotal = new JLabel("0");
        lblTotal.setBounds(160, 455, 100, 25);
        add(lblTotal);

        l6 = new JLabel("Average Score:");
        l6.setBounds(20, 485, 130, 25);
        add(l6);

        lblAverage = new JLabel("0.0");
        lblAverage.setBounds(160, 485, 100, 25);
        add(lblAverage);

        l3 = new JLabel("Highest Score:");
        l3.setBounds(320, 455, 130, 25);
        add(l3);

        lblHighest = new JLabel("0.0");
        lblHighest.setBounds(460, 455, 200, 25);
        add(lblHighest);

        l7 = new JLabel("Lowest Score:");
        l7.setBounds(320, 485, 130, 25);
        add(l7);

        lblLowest = new JLabel("0.0");
        lblLowest.setBounds(460, 485, 200, 25);
        add(lblLowest);


        // ---------------- Action Listeners ----------------

        bAdd.addActionListener(this);
        bRemove.addActionListener(this);
        bSummary.addActionListener(this);
        bClear.addActionListener(this);
    }


    // =========================================================
    //                    ACTION PERFORMED
    // =========================================================

    @Override
    public void actionPerformed(ActionEvent e) {

        // ================= Add Student =================

        if (e.getSource() == bAdd) {

            String name = t1.getText().trim();
            String scoreText = t2.getText().trim();

            if (name.isEmpty() || scoreText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both name and score");
                return;
            }

            try {
                double score = Double.parseDouble(scoreText);

                if (score < 0 || score > 100) {
                    JOptionPane.showMessageDialog(this, "Score should be between 0 and 100");
                    return;
                }

                Student s = new Student(name, score);
                studentList.add(s);

                model.addRow(new Object[]{studentList.size(), name, score});

                t1.setText("");
                t2.setText("");
                t1.requestFocus();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Score must be a valid number");
            }
        }


        // ================= Remove Selected =================

        else if (e.getSource() == bRemove) {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student to remove");
                return;
            }

            studentList.remove(row);
            model.removeRow(row);

            refreshSrNo();
        }


        // ================= Show Summary =================

        else if (e.getSource() == bSummary) {

            showSummary();
        }


        // ================= Clear All =================

        else if (e.getSource() == bClear) {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to clear all records?",
                    "Clear All",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                studentList.clear();
                model.setRowCount(0);

                lblTotal.setText("0");
                lblAverage.setText("0.0");
                lblHighest.setText("0.0");
                lblLowest.setText("0.0");
            }
        }
    }


    // Sr No column ko delete ke baad dobara sahi sequence me set karta hai
    void refreshSrNo() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(i + 1, i, 0);
        }
    }


    // Average, Highest, Lowest calculate karke summary dikhata hai
    void showSummary() {

        if (studentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No student records available");
            return;
        }

        double total = 0;
        double highest = studentList.get(0).score;
        double lowest = studentList.get(0).score;
        String topStudent = studentList.get(0).name;
        String bottomStudent = studentList.get(0).name;

        for (Student s : studentList) {

            total += s.score;

            if (s.score > highest) {
                highest = s.score;
                topStudent = s.name;
            }

            if (s.score < lowest) {
                lowest = s.score;
                bottomStudent = s.name;
            }
        }

        double average = total / studentList.size();

        lblTotal.setText(String.valueOf(studentList.size()));
        lblAverage.setText(String.format("%.2f", average));
        lblHighest.setText(String.format("%.2f (%s)", highest, topStudent));
        lblLowest.setText(String.format("%.2f (%s)", lowest, bottomStudent));

        JOptionPane.showMessageDialog(
                this,
                "Summary Report\n\n" +
                        "Total Students : " + studentList.size() + "\n" +
                        "Average Score  : " + String.format("%.2f", average) + "\n" +
                        "Highest Score  : " + highest + " (" + topStudent + ")\n" +
                        "Lowest Score   : " + lowest + " (" + bottomStudent + ")",
                "Summary",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================================================
    //                       MAIN METHOD
    // =========================================================

    public static void main(String[] args) {
        new StudentGradeTracker().setVisible(true);
    }
}

