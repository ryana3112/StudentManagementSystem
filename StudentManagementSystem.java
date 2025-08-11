import java.io.*;
import java.util.*;

public class StudentManagementSystem {
    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by Roll No");
            System.out.println("4. Delete Student by Roll No");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addStudent(sc);
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    searchStudent(sc);
                    break;
                case 4:
                    deleteStudent(sc);
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);

        sc.close();
    }

    private static void addStudent(Scanner sc) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            System.out.print("Enter Roll No: ");
            int rollNo = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Age: ");
            int age = sc.nextInt();

            Student student = new Student(rollNo, name, age);
            bw.write(student.toString());
            bw.newLine();
            System.out.println("✅ Student added successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error writing to file: " + e.getMessage());
        }
    }

    private static void viewStudents() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n--- Student List ---");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    private static void searchStudent(Scanner sc) {
        System.out.print("Enter Roll No to search: ");
        int searchRollNo = sc.nextInt();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Roll No: " + searchRollNo + ",")) {
                    System.out.println("✅ Student Found: " + line);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("❌ Student not found.");
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    private static void deleteStudent(Scanner sc) {
        System.out.print("Enter Roll No to delete: ");
        int deleteRollNo = sc.nextInt();
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        boolean deleted = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Roll No: " + deleteRollNo + ",")) {
                    deleted = true;
                    continue;
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error processing file: " + e.getMessage());
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }

        if (deleted) {
            System.out.println("✅ Student deleted successfully!");
        } else {
            System.out.println("❌ Student not found.");
        }
    }
}
