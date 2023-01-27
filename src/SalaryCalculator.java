import java.util.Scanner;

public class SalaryCalculator {
    static double basicSalary = 0;
    static double finalSalary = 0;
    static float allowances = 0;
    static float epfDeduction = 0;

    public static void main(String[] args) {
        // Main thread
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter payment per day: ");
        double perDayPayment = scanner.nextDouble();
        System.out.print("Enter number of days: ");
        int noOfDays = scanner.nextInt();
        scanner.close();
        basicSalary = perDayPayment * noOfDays;

        // Thread 2 to calculate allowances
        Thread allowanceCalThread = new Thread(new Runnable() {
            @Override
            public void run() {
                allowances = (float) (basicSalary * 0.05);
                System.out.println("Allowances: " + allowances);
            }
        });

        // Thread 3 to calculate EPF deductions and Employer's contribution
        Thread epfCalThread = new Thread(new Runnable() {
            @Override
            public void run() {
                epfDeduction = (float) ((basicSalary + allowances) * 0.08);
                float employerContribution = (float) ((basicSalary + allowances) * 0.12);
                System.out.println("EPF Deduction: " + epfDeduction);
                System.out.println("Employer Contribution: " + employerContribution);
                System.out.println("Total EPF: " + (epfDeduction + employerContribution));
            }
        });

        try {
            allowanceCalThread.start();
            allowanceCalThread.join();
            epfCalThread.start();
            epfCalThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finalSalary = basicSalary + allowances - epfDeduction;
        System.out.println("Final Salary: " + finalSalary);
    }
}