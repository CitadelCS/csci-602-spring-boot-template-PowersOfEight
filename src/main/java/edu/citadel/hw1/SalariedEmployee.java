package edu.citadel.hw1;

import java.time.LocalDate;
import java.util.Objects;

public class SalariedEmployee extends Employee {

  private final double annualSalary;

  public SalariedEmployee(String name,
      LocalDate hireDate,
      double annualSalary) {
    super(name, hireDate);
    this.annualSalary = annualSalary;
  }

  @Override
  public double getMonthlyPay() {
    return this.annualSalary / 12.0;
  }

  public double getAnnualSalary() {
    return annualSalary;
  }

  @Override
  public String toString() {
    return String.format(
        "SalariedEmployee[name=%s, hireDate=%s, annualSalary=%01.1f]",
        this.getName(),
        this.getHireDate(),
        this.annualSalary);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(annualSalary);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = this.getName() != null ? this.getName().hashCode() : 0;
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = this.getHireDate() != null ? this.getHireDate().hashCode() : 0;
    result = prime * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || this.getClass() != obj.getClass())
      return false;
    if (this == obj)
      return true;
    SalariedEmployee other = (SalariedEmployee) obj;
    return Objects.equals(this.getName(), other.getName()) &&
        Objects.equals(this.getHireDate(), other.getHireDate()) &&
        Double.compare(this.annualSalary, other.annualSalary) == 0;
  }
}
