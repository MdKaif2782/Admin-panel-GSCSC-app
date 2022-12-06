package com.gscsc.adminpanel;

public class Member {
    private String name;
    private String department;

    public Member(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public Member() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
