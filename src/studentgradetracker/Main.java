package studentgradetracker;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new StudentGradeGUI().setVisible(true);
        });
    }
}