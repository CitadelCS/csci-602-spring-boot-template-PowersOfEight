package edu.citadel.hw1;

import java.time.LocalDate;

public abstract class Employee implements Comparable<Employee> {

  private final String name;
  private final LocalDate hireDate;

  public Employee(String name, LocalDate hireDate) {
    this.name = name;
    this.hireDate = hireDate;
  }

  public String getName() {
    return this.name;
  }

  public LocalDate getHireDate() {
    return this.hireDate;
  }

  public abstract double getMonthlyPay();

  @Override
  public int compareTo(Employee other) {
    return Double.compare(
        this.getMonthlyPay(),
        other.getMonthlyPay());
  }

}
