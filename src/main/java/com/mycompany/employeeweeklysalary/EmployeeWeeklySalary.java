package com.mycompany.employeeweeklysalary;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeWeeklySalary {
    // Global variables
    private static String[] employeeDetails;
    private static double[] salaryDetails;
    private static ArrayList<String[]> timeLogs;
    private static Scanner scanner;
    private static DecimalFormat df;
    private static DateTimeFormatter timeFmt;
    
    public static void main(String[] args) {
        // Initialize variables
        initializeData();
        
        // Display employee and salary details
        displayEmployeeDetails();
        displaySalaryDetails();
        
        // Main program loop
        runMainLoop();
    }
    
    /**
     * Initialize all data and variables
     */
    private static void initializeData() {
        // Employee Details
        employeeDetails = new String[] {
            "10014", "Martinez", "Leila", "07/11/1970",
            "37/46 Kulas Roads, Maragondon 0962 Quirino", "877-110-749",
            "27-2090996-4", "515741057496", "275-792-513-000", "101205445886",
            "Regular", "Payroll Rank and File", "Salcedo, Anthony"
        };
        
        // Salary Details
        salaryDetails = new double[] {24000, 1500, 500, 500, 12000};
        
        // Formatter for decimal numbers
        df = new DecimalFormat("##,##0.00");
        
        // Time Formatter
        timeFmt = DateTimeFormatter.ofPattern("HH:mm");
        
        // Initialize time logs
        timeLogs = new ArrayList<>();
        timeLogs.add(new String[]{"2024-06-03", "09:23", "18:09"});
        timeLogs.add(new String[]{"2024-06-04", "09:11", "17:10"});
        timeLogs.add(new String[]{"2024-06-05", "08:50", "18:10"});
        timeLogs.add(new String[]{"2024-06-06", "09:08", "17:30"});
        timeLogs.add(new String[]{"2024-06-07", "08:30", "17:31"});
        
        // Scanner for user input
        scanner = new Scanner(System.in);
        
    }
    
    /**
     * Display employee details
     */
    private static void displayEmployeeDetails() {
        System.out.println("Employee Details:");
        String[] labels = {"Employee Number:", "Last Name:", "First Name:", "Birthday:",
                           "Address:", "Phone Number:", "SSS Number:", "PhilHealth Number:",
                           "TIN Number:", "PagIBIG Number:", "Status:", "Position:", "Immediate Supervisor:"};

        for (int i = 0; i < employeeDetails.length; i++) {
            System.out.println(labels[i] + " " + employeeDetails[i]);
        }
    }
    
    /**
     * Display salary details
     */
    private static void displaySalaryDetails() {
        String[] salaryLabels = {"Basic Salary:", "Rice Subsidy:", "Phone Allowance:",
                                 "Clothing Allowance:", "Gross Semi-Monthly Rate:"};
        for (int i = 0; i < salaryDetails.length; i++) {
            System.out.printf(salaryLabels[i] + " %.2f\n", (salaryDetails[i]));
        }
    }
    
    /**
     * Display time logs
     */
    private static void displayTimeLogs() {
        System.out.println("\nCurrent Time Logs:");
        // Print header
        System.out.printf("%-4s %-12s %-8s %-8s\n", "No.", "Date", "Time-in", "Time-out");

        for (int i = 0; i < timeLogs.size(); i++) {
            String[] log = timeLogs.get(i);
            System.out.printf("%-4d %-12s %-8s %-8s\n", i + 1, log[0], log[1], log[2]);
        }
    }
    
    /**
     * Display menu options
     */
    private static void displayMenu() {
        System.out.println("\nOptions:");
        System.out.println("1. Confirm or Edit an Entry");
        System.out.println("2. Add a New Entry");
        System.out.println("3. Remove an Entry");
        System.out.println("4. Calculate Payroll");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }
    
    /**
     * Main program loop
     */
    private static void runMainLoop() {
        while (true) {
            displayTimeLogs();
            displayMenu();
            
            String choiceStr = scanner.nextLine();

            // Add option to cancel or go back
            if (choiceStr.trim().equalsIgnoreCase("C")) {
                System.out.println("Action cancelled. Returning to main menu.");
                continue; // Go back to the beginning of the loop (main menu)
            }

            try {
                int choice = Integer.parseInt(choiceStr);

                switch (choice) {
                    case 1:
                        confirmOrEditEntry();
                        break;
                    case 2:
                        addNewEntry();
                        break;
                    case 3:
                        removeEntry();
                        break;
                    case 4:
                        calculatePayroll();
                        break;
                    case 5:
                        System.out.println("Exiting program.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please choose again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'C' to cancel.");
            }
        }
    }
    
    /**
     * Confirm or edit a time log entry
     */
    private static void confirmOrEditEntry() {
        System.out.print("Enter the entry number to confirm/edit (or 'C' to cancel): ");
        String entryInput = scanner.nextLine();

        if (entryInput.trim().equalsIgnoreCase("C")) {
            System.out.println("Action cancelled. Returning to main menu.");
            return;
        }

        try {
            int entryIndex = Integer.parseInt(entryInput) - 1;

            if (entryIndex >= 0 && entryIndex < timeLogs.size()) {
                String[] log = timeLogs.get(entryIndex);
                System.out.printf("%-4s %-12s %-8s %-8s\n", "No.", "Date", "Time-in", "Time-out");
                System.out.printf("%-4d %-12s %-8s %-8s\n", entryIndex + 1, log[0], log[1], log[2]);
                System.out.print("Confirm these details? (yes/no): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (!confirmation.equals("yes")) {
                    editTimeLogEntry(entryIndex, log);
                }
            } else {
                System.out.println("Invalid entry number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'C' to cancel.");
        }
    }
    
    /**
     * Edit a time log entry
     */
    private static void editTimeLogEntry(int entryIndex, String[] log) {
        System.out.print("Type key words to change the following: (D) = date, (TI) = Time-in, (TO) = time-out (or 'C' to cancel)? ");
        String editChoice = scanner.nextLine().trim().toUpperCase();

        if (editChoice.trim().equalsIgnoreCase("C")) {
            System.out.println("Action cancelled. Returning to main menu.");
            return;
        }

        switch (editChoice) {
            case "D":
                editDate(log);
                break;
            case "TI":
                editTimeIn(log);
                break;
            case "TO":
                editTimeOut(log);
                break;
            default:
                System.out.println("Invalid choice. No changes made.");
        }
    }
    
    /**
     * Edit the date of a time log entry
     */
    private static void editDate(String[] log) {
        while (true) {
            System.out.print("Enter the new date (YYYY-MM-DD) (or 'C' to cancel): ");
            String newDateStr = scanner.nextLine();

            if (newDateStr.trim().equalsIgnoreCase("C")) {
                System.out.println("Action cancelled. Returning to main menu.");
                break;
            }

            try {
                LocalDate.parse(newDateStr);
                log[0] = newDateStr;
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }
    
    /**
     * Edit the time-in of a time log entry
     */
    private static void editTimeIn(String[] log) {
        while (true) {
            System.out.print("Enter the new time-in (HH:mm) (or 'C' to cancel): ");
            String newTimeIn = scanner.nextLine();
            
            if (newTimeIn.trim().equalsIgnoreCase("C")) {
                System.out.println("Action cancelled. Returning to main menu.");
                break;
            }
            
            try {
                LocalTime.parse(newTimeIn, timeFmt);
                log[1] = newTimeIn;
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:mm.");
            }
        }
    }
    
    /**
     * Edit the time-out of a time log entry
     */
    private static void editTimeOut(String[] log) {
        while (true) {
            System.out.print("Enter the new time-out (HH:mm) (or 'C' to cancel): ");
            String newTimeOut = scanner.nextLine();
            
            if (newTimeOut.trim().equalsIgnoreCase("C")) {
                System.out.println("Action cancelled. Returning to main menu.");
                break;
            }
            
            try {
                LocalTime.parse(newTimeOut, timeFmt);
                log[2] = newTimeOut;
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:mm.");
            }
        }
    }
    
    /**
     * Add a new time log entry
     */
    private static void addNewEntry() {
        String[] newLog = new String[3];

        System.out.print("Enter the date (YYYY-MM-DD) (or 'C' to cancel): ");
        String dateStr = scanner.nextLine();

        if (dateStr.trim().equalsIgnoreCase("C")) {
            System.out.println("Action cancelled. Returning to main menu.");
            return;
        }

        try {
            LocalDate.parse(dateStr);
            newLog[0] = dateStr;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        System.out.print("Enter the time-in (HH:mm) (or 'C' to cancel): ");
        String timeInStr = scanner.nextLine();

        if (timeInStr.trim().equalsIgnoreCase("C")) {
            System.out.println("Action cancelled. Returning to main menu.");
            return;
        }

        try {
            LocalTime.parse(timeInStr, timeFmt);
            newLog[1] = timeInStr;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please use HH:mm.");
            return;
        }

        System.out.print("Enter the time-out (HH:mm) (or 'C' to cancel): ");
        String timeOutStr = scanner.nextLine();

        if (timeOutStr.trim().equalsIgnoreCase("C")) {
            System.out.println("Action cancelled. Returning to main menu.");
            return;
        }

        try {
            LocalTime.parse(timeOutStr, timeFmt);
            newLog[2] = timeOutStr;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please use HH:mm.");
            return;
        }

        timeLogs.add(newLog);
        System.out.println("New entry added successfully!");
    }
    
    /**
     * Remove a time log entry
     */
    private static void removeEntry() {
        System.out.print("Enter the entry number to remove (or 'C' to cancel): ");
        String removeInput = scanner.nextLine();

        if (removeInput.trim().equalsIgnoreCase("C")) {
            System.out.println("Action cancelled. Returning to main menu.");
            return;
        }

        try {
            int removeIndex = Integer.parseInt(removeInput) - 1;

            if (removeIndex >= 0 && removeIndex < timeLogs.size()) {
                timeLogs.remove(removeIndex);
                System.out.println("Entry removed successfully!");
            } else {
                System.out.println("Invalid entry number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'C' to cancel.");
        }
    }
    
    /**
     * Calculate payroll
     */
    private static void calculatePayroll() {
        double hourlyRate = 142.86;
        double minuteRate = hourlyRate / 60;
        int lunchBreak = 1;
        double weeklyGrossSalary = 0;
        double grossSemiMonthlyRate = salaryDetails[4];
        
        double basicSalary = salaryDetails[0];
        double riceSubsidy = salaryDetails[1];
        double phoneAllowance = salaryDetails[2];
        double clothingAllowance = salaryDetails[3];
        double allowance = riceSubsidy + phoneAllowance + clothingAllowance;
        
        int numDaysWorked = timeLogs.size();

        System.out.println("\nPayroll Calculation for " + employeeDetails[2] + " " + employeeDetails[1]);

        // Calculate daily salary for each day worked
        for (int i = 0; i < numDaysWorked; i++) {
            calculateDailySalary(i, hourlyRate, minuteRate, lunchBreak);
            weeklyGrossSalary += calculateDailySalaryAmount(i, hourlyRate, minuteRate, lunchBreak);
        }
        
        System.out.println("\nWeekly Gross Salary = " + df.format(weeklyGrossSalary));
        
        System.out.println("\nAllowance Breakdown:");
        System.out.println("Rice Subsidy + Phone Allowance + Clothing Allowance = " + 
                          df.format(riceSubsidy) + " + " + df.format(phoneAllowance) + " + " + 
                          df.format(clothingAllowance) + " = " + df.format(allowance));
        
        // Calculate contributions and deductions
        double pagIBIGMonthly = calculatePagIBIG(basicSalary);
        double pagIBIGWeekly = pagIBIGMonthly / 4;
        
        double philHealthMonthly = calculatePhilHealth(basicSalary);
        double philHealthWeekly = philHealthMonthly / 4;
        
        double monthlySSS = calculateSSS(basicSalary);
        double weeklySSS = monthlySSS / 4;
        
        double taxableIncomeMonthly = basicSalary - monthlySSS - philHealthMonthly - pagIBIGMonthly;
        double witholdingTax = calculateWithholdingTax(taxableIncomeMonthly);
        double witholdingTaxWeekly = witholdingTax / 4;
        
        double deductions = monthlySSS + philHealthMonthly + pagIBIGMonthly + witholdingTax;
        double deductionsWeekly = weeklySSS + philHealthWeekly + pagIBIGWeekly + witholdingTaxWeekly;
        double totalWeeklyNet = weeklyGrossSalary - deductionsWeekly;
        
        // Display contributions and deductions
        displayContributions(pagIBIGMonthly, philHealthMonthly, monthlySSS, witholdingTax, deductions);
        displayWeeklyContributions(pagIBIGWeekly, philHealthWeekly, weeklySSS, witholdingTaxWeekly, deductionsWeekly);
        
        // Display weekly net salary
        displayWeeklyNetSalary(weeklyGrossSalary, allowance, deductionsWeekly, totalWeeklyNet);
    }
    
    /**
     * Calculate and display daily salary for a specific day
     */
    private static void calculateDailySalary(int index, double hourlyRate, double minuteRate, int lunchBreak) {
        String date = timeLogs.get(index)[0];
        LocalTime timeIn = LocalTime.parse(timeLogs.get(index)[1], timeFmt);
        LocalTime timeOut = LocalTime.parse(timeLogs.get(index)[2], timeFmt);

        int hoursWorked = (timeOut.getHour() - timeIn.getHour()) - lunchBreak;
        int minWorked = timeOut.getMinute() - timeIn.getMinute();

        if (minWorked < 0) {
            hoursWorked -= 1;
            minWorked += 60;
        }

        double dailySalary = (hourlyRate * hoursWorked) + (minuteRate * minWorked);

        // Output Log
        System.out.println("\nDate: " + date);
        System.out.println("Time-in: " + timeIn);
        System.out.println("Time-out: " + timeOut);
        System.out.println("Total Time: " + hoursWorked + " hours and " + minWorked + " minutes");
        System.out.println("Rate per Minute: " + df.format(minuteRate) + " per minute");
        System.out.println("Hourly Rate: " + df.format(hourlyRate) + " per hour");
        System.out.println("Daily Salary: " + df.format(dailySalary) + "\n");
    }
    
    /**
     * Calculate daily salary amount for a specific day
     */
    private static double calculateDailySalaryAmount(int index, double hourlyRate, double minuteRate, int lunchBreak) {
        LocalTime timeIn = LocalTime.parse(timeLogs.get(index)[1], timeFmt);
        LocalTime timeOut = LocalTime.parse(timeLogs.get(index)[2], timeFmt);

        int hoursWorked = (timeOut.getHour() - timeIn.getHour()) - lunchBreak;
        int minWorked = timeOut.getMinute() - timeIn.getMinute();

        if (minWorked < 0) {
            hoursWorked -= 1;
            minWorked += 60;
        }

        return (hourlyRate * hoursWorked) + (minuteRate * minWorked);
    }
    
    /**
     * Calculate Pag-IBIG contribution
     * CR = Contribution Rate
     * Cont = Contribution
     */
    private static double calculatePagIBIG(double basicSalary) {
        double employeeCR = 0;
        double employerCR = 0;

        if (basicSalary <= 999.99) {
            employeeCR = 0.00 * 100;
            employerCR = 0.00 * 100;
        } else if (basicSalary >= 1000.00 && basicSalary <= 1500.00) {
            employeeCR = 0.01 * 100;
            employerCR = 0.02 * 100;
        } else if (basicSalary >= 1501.00) {
            employeeCR = 0.02 * 100;
            employerCR = 0.02 * 100;
        }

        double employeeCont = basicSalary * (employeeCR / 100);
        double employerCont = basicSalary * (employerCR / 100);

        double totalCR = (employeeCR + employerCR);
        double totalCont = basicSalary * (employeeCR + employerCR) / 100;

        // NOTE: The maximum contribution amount is 100.
        double totalContCapped = 0;
        if (totalCont >= 100) {
            totalContCapped = 100;
        } else if (totalCont <= 100) {
            totalContCapped = employeeCont;
        }

        return totalContCapped;
    }
    
    /**
     * Calculate PhilHealth contribution
     * pR = Premium Rate
     */
    private static double calculatePhilHealth(double basicSalary) {
        double pR = 0.00 * 100;
        if (basicSalary == 10000) {
            pR = 0.03 * 100;
        } else if (basicSalary >= 10000.01 && basicSalary <= 59999.99) {
            pR = 0.03 * 100;
        } else if (basicSalary == 60000) {
            pR = 0.03 * 100;
        }

        double monthlyPremium = basicSalary * (pR / 100);
        double employeeShare = 0.50;
        return monthlyPremium * employeeShare;
    }
    
    /**
     * Calculate SSS contribution
     */
    private static double calculateSSS(double compensation) {
        double contribution = 0;
        if (compensation < 3250) {
            contribution = 135;
        } else if (compensation >= 3250 && compensation <= 3570) {
            contribution = 157.50;
        } else if (compensation >= 3750 && compensation <= 4250) {
            contribution = 180.00;
        } else if (compensation >= 4250 && compensation <= 4750) {
            contribution = 202.50;
        } else if (compensation >= 4750 && compensation <= 5250) {
            contribution = 225.00;
        } else if (compensation >= 5250 && compensation <= 5750) {
            contribution = 247.50;
        } else if (compensation >= 5750 && compensation <= 6250) {
            contribution = 270.00;
        } else if (compensation >= 6250 && compensation <= 6750) {
            contribution = 292.50;
        } else if (compensation >= 6750 && compensation <= 7250) {
            contribution = 315.00;
        } else if (compensation >= 7250 && compensation <= 7750) {
            contribution = 337.50;
        } else if (compensation >= 7750 && compensation <= 8250) {
            contribution = 360;
        } else if (compensation >= 8250 && compensation <= 8750) {
            contribution = 382.50;
        } else if (compensation >= 8750 && compensation <= 9250) {
            contribution = 405.00;
        } else if (compensation >= 9250 && compensation <= 9750) {
            contribution = 427.50;
        } else if (compensation >= 9750 && compensation <= 10250) {
            contribution = 450.00;
        } else if (compensation >= 10250 && compensation <= 10750) {
            contribution = 472.50;
        } else if (compensation >= 10750 && compensation <= 11250) {
            contribution = 495.00;
        } else if (compensation >= 11250 && compensation <= 11750) {
            contribution = 517.50;
        } else if (compensation >= 11750 && compensation <= 12250) {
            contribution = 540.00;
        } else if (compensation >= 12250 && compensation <= 12750) {
            contribution = 562.50;
        } else if (compensation >= 12750 && compensation <= 13250) {
            contribution = 585.00;
        } else if (compensation >= 13250 && compensation <= 13750) {
            contribution = 607.50;
        } else if (compensation >= 13750 && compensation <= 14250) {
            contribution = 630.00;
        } else if (compensation >= 14250 && compensation <= 14750) {
            contribution = 652.50;
        } else if (compensation >= 14750 && compensation <= 15250) {
            contribution = 675.00;
        } else if (compensation >= 15250 && compensation <= 15750) {
            contribution = 697.50;
        } else if (compensation >= 15750 && compensation <= 16250) {
            contribution = 720.00;
        } else if (compensation >= 16250 && compensation <= 16750) {
            contribution = 742.50;
        } else if (compensation >= 16750 && compensation <= 17250) {
            contribution = 765.00;
        } else if (compensation >= 17250 && compensation <= 17750) {
            contribution = 787.50;
        } else if (compensation >= 17750 && compensation <= 18250) {
            contribution = 810.00;
        } else if (compensation >= 18250 && compensation <= 18750) {
            contribution = 832.50;
        } else if (compensation >= 18750 && compensation <= 19250) {
            contribution = 855.00;
        } else if (compensation >= 19250 && compensation <= 19750) {
            contribution = 877.50;
        } else if (compensation >= 19750 && compensation <= 20250) {
            contribution = 900.00;
        } else if (compensation >= 20250 && compensation <= 20750) {
            contribution = 922.50;
        } else if (compensation >= 20750 && compensation <= 21250) {
            contribution = 945.00;
        } else if (compensation >= 21250 && compensation <= 21750) {
            contribution = 967.50;
        } else if (compensation >= 21750 && compensation <= 22250) {
            contribution = 990.00;
        } else if (compensation >= 22250 && compensation <= 22750) {
            contribution = 1012.50;
        } else if (compensation >= 22750 && compensation <= 23250) {
            contribution = 1035.00;
        } else if (compensation >= 23250 && compensation <= 23750) {
            contribution = 1057.50;
        } else if (compensation >= 23750 && compensation <= 24250) {
            contribution = 1080.00;
        } else if (compensation >= 24250 && compensation <= 24750) {
            contribution = 1102.50;
        } else if (compensation >= 24750) {
            contribution = 1125.00;
        }
        
        return contribution;
    }
    
    /**
     * Calculate withholding tax
     * mR = Monthly Rate
     * tR = Tax Rate
     */
    private static double calculateWithholdingTax(double taxableIncomeMonthly) {
        double tR = 0;
        if (taxableIncomeMonthly <= 20832) {
            tR = 0.00;
        } else if (taxableIncomeMonthly >= 20833 && taxableIncomeMonthly <= 33333) {
            tR = (taxableIncomeMonthly - 20833) * 0.20;
        } else if (taxableIncomeMonthly >= 33333 && taxableIncomeMonthly <= 66667) {
            tR = 2500 + ((taxableIncomeMonthly - 33333) * 0.25);
        } else if (taxableIncomeMonthly >= 66667 && taxableIncomeMonthly <= 166667) {
            tR = 10833 + ((taxableIncomeMonthly - 33333) * 0.30);
        } else if (taxableIncomeMonthly >= 166667 && taxableIncomeMonthly <= 666667) {
            tR = 40833.33 + ((taxableIncomeMonthly - 166667) * 0.32);
        } else if (taxableIncomeMonthly >= 666667) {
            tR = 200833.33 + ((taxableIncomeMonthly - 666667) * 0.35);
        }
        
        return tR;
    }
    
    /**
     * Display contributions and deductions
     */
    private static void displayContributions(double pagIBIGMonthly, double philHealthMonthly, 
                                           double monthlySSS, double witholdingTax, double deductions) {
        System.out.println("\nContributions and Deductions: -" + df.format(deductions));
        System.out.println("Breakdown: ");
        System.out.println("Pag-IBIG Contribution: -" + df.format(pagIBIGMonthly));
        System.out.println("PhilHealth Contribution: -" + df.format(philHealthMonthly));
        System.out.println("SSS Contribution: -" + df.format(monthlySSS));
        System.out.println("Witholding Tax: -" + df.format(witholdingTax));
    }
    
    /**
     * Display weekly contributions and deductions
     */
    private static void displayWeeklyContributions(double pagIBIGWeekly, double philHealthWeekly, 
                                                 double weeklySSS, double witholdingTaxWeekly, double deductionsWeekly) {
        System.out.println("\nContributions and Deductions (Weekly): -" + df.format(deductionsWeekly));
        System.out.println("Breakdown: ");
        System.out.println("Pag-IBIG Contribution: -" + df.format(pagIBIGWeekly));
        System.out.println("PhilHealth Contribution: -" + df.format(philHealthWeekly));
        System.out.println("SSS Contribution: -" + df.format(weeklySSS));
        System.out.println("Income Tax: -" + df.format(witholdingTaxWeekly));
    }
    
    /**
     * Display weekly net salary
     */
    private static void displayWeeklyNetSalary(double weeklyGrossSalary, double allowance, 
                                             double deductionsWeekly, double totalWeeklyNet) {
        System.out.println("\nWeekly Net Salary: " + df.format(totalWeeklyNet));
        System.out.println("Breakdown: ");
        System.out.println("Gross Weekly Salary: +" + df.format(weeklyGrossSalary));
        System.out.println("Contributions and Deductions (Weekly): -" + df.format(deductionsWeekly));
        System.out.println("\nAllowance: +" + df.format(allowance));
    }
}
