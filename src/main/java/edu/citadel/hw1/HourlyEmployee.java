package edu.citadel.hw1;

import java.time.LocalDate;
import java.util.Objects;

public class HourlyEmployee extends Employee {

  private final double wageRate;
  private final double hoursWorked;

  public HourlyEmployee(String name,
      LocalDate hireDate,
      double wageRate,
      double hoursWorked) {
    super(name, hireDate);
    this.wageRate = wageRate;
    this.hoursWorked = hoursWorked;
  }

  public double getWageRate() {
    return wageRate;
  }

  public double getHoursWorked() {
    return hoursWorked;
  }

  @Override
  public double getMonthlyPay() {
    return (this.wageRate * this.hoursWorked);
  }

  @Override
  public String toString() {
    return String.format(
        "HourlyEmployee[name=%s, hireDate=%s, wageRate=%01.1f, hoursWorked=%01.1f]",
        this.getName(),
        this.getHireDate(),
        this.wageRate,
        this.hoursWorked);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(wageRate);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(hoursWorked);
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
    HourlyEmployee other = (HourlyEmployee) obj;
    return Objects.equals(other.getName(), this.getName())
        && Objects.equals(other.getHireDate(), this.getHireDate())
        && Double.compare(other.getWageRate(), this.getWageRate()) == 0
        && Double.compare(other.getHoursWorked(), this.getHoursWorked()) == 0;
  }

}
