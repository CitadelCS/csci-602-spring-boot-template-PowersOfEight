package edu.citadel.hw1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class InheritanceDemo {
  public static void main(String[] args) {

    // Create an ArrayList of `Employee`
    ArrayList<Employee> employees = new ArrayList<>();

    // Create two hourly employees
    employees.add(new HourlyEmployee("John Doe", LocalDate.of(2009, 5, 21), 50.5, 160.0));
    employees.add(new HourlyEmployee("Jane Doe", LocalDate.of(2005, 9, 1), 150.5, 80.0));

    // Create two salaried employees
    employees.add(new SalariedEmployee("Moe Howard", LocalDate.of(2004, 1, 1), 75000.0));
    employees.add(new SalariedEmployee("Curly Howard", LocalDate.of(2018, 1, 1), 105000.0));

    // Print the list of employees
    System.out.println("List of Employees (before sorting)");
    for (Employee emp : employees) {
      System.out.println(emp);
    }
    System.out.println();

    // Sort the list of employees by monthly pay
    Collections.sort(employees);

    // Print the sorted list of employees
    System.out.println("List of Employees (after sorting)");
    for (Employee emp : employees) {
      System.out.println(emp);
    }
    System.out.println();

    // Print the monthly pay for each employee
    System.out.println("Monthly Pay");
    for (Employee emp : employees) {
      System.out.printf("%s: $%,.2f\n",
          emp.getName(), emp.getMonthlyPay());
    }

    // Print the total monthly pay
    System.out.printf("Total Monthly Pay: $%,.2f\n", employees.stream()
        .mapToDouble(Employee::getMonthlyPay)
        .reduce(0.0, Double::sum));
  }

}
