/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.entities;

/**
 *
 * @author USER
 */
public class Recap {

    private String no;
    private String idEmployee;
    private String namaEmployee;
    private String positionEmployee;
    private String soId;
    private String ticketNumber;
    private String namePIC;
    private String positionPIC;
    private String salaryEmployee;
    private String location;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String totalHour;
    private String dateType;
    private String createDate;
    private String updateBy;
    private String updateDate;
    private String periode;
    private String assignment;
    private String namaCustomer;
    private String status;

    private String employeeContractStatus;
    private String mealsTransport;
    private String ChargerbackStatus;
    private String ChargerBackType;

    private String cell1;
    private String cell2;
    private String cell3;
    private String cell4;
    private String cell5;
    private String cell6;

    private String totalRounded;
    private String totalFee;

    public Recap() {
    }

    public Recap(String no, String idEmployee, String namaEmployee, String positionEmployee, String salaryEmployee, String startDate, String endDate, String startTime, String endTime, String totalHour, String dateType, String periode, String assignment, String namaCustomer, String status, String employeeContractStatus, String mealsTransport, String ChargerbackStatus, String ChargerBackType, String cell1, String cell2, String cell3, String cell4, String cell5, String cell6, String totalRounded, String totalFee) {
        this.no = no;
        this.idEmployee = idEmployee;
        this.namaEmployee = namaEmployee;
        this.salaryEmployee = salaryEmployee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalHour = totalHour;
        this.dateType = dateType;
        this.periode = periode;
        this.assignment = assignment;
        this.namaCustomer = namaCustomer;
        this.status = status;
        this.employeeContractStatus = employeeContractStatus;
        this.mealsTransport = mealsTransport;
        this.ChargerbackStatus = ChargerbackStatus;
        this.ChargerBackType = ChargerBackType;
        this.cell1 = cell1;
        this.cell2 = cell2;
        this.cell3 = cell3;
        this.cell4 = cell4;
        this.cell5 = cell5;
        this.cell6 = cell6;
        this.totalRounded = totalRounded;
        this.totalFee = totalFee;
    }

    public Recap(String no, String idEmployee, String namaEmployee, String positionEmployee, String soId, String ticketNumber,String namePIC, String positionPIC, String salaryEmployee, String location, String startDate, String endDate, String startTime, String endTime, String totalHour, String dateType, String createDate, String updateBy, String updateDate, String periode, String assignment, String namaCustomer, String status, String employeeContractStatus, String mealsTransport, String ChargerbackStatus, String ChargerBackType, String cell1, String cell2, String cell3, String cell4, String cell5, String cell6, String totalRounded, String totalFee) {
        this.no = no;
        this.idEmployee = idEmployee;
        this.namaEmployee = namaEmployee;
        this.positionEmployee = positionEmployee;
        this.soId = soId;
        this.ticketNumber = ticketNumber;
        this.namePIC = namePIC;
        this.positionPIC = positionPIC;
        this.salaryEmployee = salaryEmployee;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalHour = totalHour;
        this.dateType = dateType;
        this.createDate = createDate;
        this.updateBy = updateBy;
        this.updateDate = updateDate;
        this.periode = periode;
        this.assignment = assignment;
        this.namaCustomer = namaCustomer;
        this.status = status;
        this.employeeContractStatus = employeeContractStatus;
        this.mealsTransport = mealsTransport;
        this.ChargerbackStatus = ChargerbackStatus;
        this.ChargerBackType = ChargerBackType;
        this.cell1 = cell1;
        this.cell2 = cell2;
        this.cell3 = cell3;
        this.cell4 = cell4;
        this.cell5 = cell5;
        this.cell6 = cell6;
        this.totalRounded = totalRounded;
        this.totalFee = totalFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChargerbackStatus() {
        return ChargerbackStatus;
    }

    public void setChargerbackStatus(String ChargerbackStatus) {
        this.ChargerbackStatus = ChargerbackStatus;
    }

    public String getChargerBackType() {
        return ChargerBackType;
    }

    public void setChargerBackType(String ChargerBackType) {
        this.ChargerBackType = ChargerBackType;
    }

    public String getCell1() {
        return cell1;
    }

    public void setCell1(String cell1) {
        this.cell1 = cell1;
    }

    public String getCell2() {
        return cell2;
    }

    public void setCell2(String cell2) {
        this.cell2 = cell2;
    }

    public String getCell3() {
        return cell3;
    }

    public void setCell3(String cell3) {
        this.cell3 = cell3;
    }

    public String getCell4() {
        return cell4;
    }

    public void setCell4(String cell4) {
        this.cell4 = cell4;
    }

    public String getCell5() {
        return cell5;
    }

    public void setCell5(String cell5) {
        this.cell5 = cell5;
    }

    public String getCell6() {
        return cell6;
    }

    public void setCell6(String cell6) {
        this.cell6 = cell6;
    }

    public String getTotalRounded() {
        return totalRounded;
    }

    public void setTotalRounded(String totalRounded) {
        this.totalRounded = totalRounded;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public String getEmployeeContractStatus() {
        return employeeContractStatus;
    }

    public void setEmployeeContractStatus(String employeeContractStatus) {
        this.employeeContractStatus = employeeContractStatus;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNamaEmployee() {
        return namaEmployee;
    }

    public void setNamaEmployee(String namaEmployee) {
        this.namaEmployee = namaEmployee;
    }

    public String getPositionEmployee() {
        return positionEmployee;
    }

    public void setPositionEmployee(String positionEmployee) {
        this.positionEmployee = positionEmployee;
    }

    public String getSoId() {
        return soId;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getNamePIC() {
        return namePIC;
    }

    public void setNamePIC(String namePIC) {
        this.namePIC = namePIC;
    }

    public String getPositionPIC() {
        return positionPIC;
    }

    public void setPositionPIC(String positionPIC) {
        this.positionPIC = positionPIC;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getSalaryEmployee() {
        return salaryEmployee;
    }

    public void setSalaryEmployee(String salaryEmployee) {
        this.salaryEmployee = salaryEmployee;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(String totalHour) {
        this.totalHour = totalHour;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getMealsTransport() {
        return mealsTransport;
    }

    public void setMealsTransport(String mealsTransport) {
        this.mealsTransport = mealsTransport;
    }
}