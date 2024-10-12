/*
 * Sean Zamora
 * ITSE 2317
 * 10/6/2024
 * Write a class named DriverExam that holds the correct answers to the exam in an array field. The class should also have an array field that holds the student’s answers.
 */

 import java.util.Scanner;

 public class DriverExam {
     // Array holding the correct answers
     private final char[] correctAnswers = {'B', 'D', 'A', 'A', 'C', 'A', 'B', 'A', 'C', 'D',
                                             'B', 'C', 'D', 'A', 'D', 'C', 'C', 'B', 'D', 'A'};
     // Array holding the student's answers
     private char[] studentAnswers = new char[20];
 
     // Method to check if the student passed the exam
     public boolean passed() {
         return totalCorrect() >= 15;
     }
 
     // Method to count total correct answers
     public int totalCorrect() {
         int correctCount = 0;
         for (int i = 0; i < correctAnswers.length; i++) {
             if (studentAnswers[i] == correctAnswers[i]) {
                 correctCount++;
             }
         }
         return correctCount;
     }
 
     // Method to count total incorrect answers
     public int totalIncorrect() {
         return studentAnswers.length - totalCorrect();
     }
 
     // Method to get an array of question numbers missed
     public int[] questionsMissed() {
         int incorrectCount = totalIncorrect();
         int[] missedQuestions = new int[incorrectCount];
         int index = 0;
 
         for (int i = 0; i < correctAnswers.length; i++) {
             if (studentAnswers[i] != correctAnswers[i]) {
                 missedQuestions[index] = i + 1;
                 index++;
             }
         }
         return missedQuestions;
     }
 
     // Method to set the student's answers
     public void setStudentAnswers(char[] answers) {
         this.studentAnswers = answers;
     }
 
     // Demonstrate the DriverExam class
     public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
         DriverExam exam = new DriverExam();
 
         System.out.println("Enter the student's answers (20 answers, A, B, C, or D):");
         char[] studentAnswers = new char[20];
         
         for (int i = 0; i < 20; i++) {
             char answer;
             while (true) {
                 System.out.print("Question " + (i + 1) + ": ");
                 answer = scanner.next().toUpperCase().charAt(0);
                 if (answer == 'A' || answer == 'B' || answer == 'C' || answer == 'D') {
                     studentAnswers[i] = answer;
                     break;
                 } else {
                     System.out.println("Invalid answer. Please enter A, B, C, or D.");
                 }
             }
         }
         
         exam.setStudentAnswers(studentAnswers);
         
         System.out.println("Total Correct: " + exam.totalCorrect());
         System.out.println("Total Incorrect: " + exam.totalIncorrect());
         
         if (exam.passed()) {
             System.out.println("The student passed the exam.");
         } else {
             System.out.println("The student failed the exam.");
             int[] missedQuestions = exam.questionsMissed();
             System.out.print("Questions missed: ");
             for (int question : missedQuestions) {
                 System.out.print(question + " ");
             }
             System.out.println();
         }
         
         scanner.close();
     }
 }