package com.posbilling.posbillingapplication.model;

import java.util.Objects;

public class MonthYearModel {
    String month;
    String year;
    int positionofMonth;
    int total;
    int liter;

    public int getPositionofMonth() {
        return positionofMonth;
    }

    public void setPositionofMonth(int positionofMonth) {
        this.positionofMonth = positionofMonth;
    }

    public int getLiter() {
        return liter;
    }

    public void setLiter(int liter) {
        this.liter = liter;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthYearModel that = (MonthYearModel) o;
        return Objects.equals(month, that.month) &&
                Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year);
    }
}
