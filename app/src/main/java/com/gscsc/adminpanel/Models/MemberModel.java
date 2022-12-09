package com.gscsc.adminpanel.Models;

public class MemberModel {
    String fname;
   String lname;
   String fullNam;
   String image;
   String fatherName;
   String fatherNumber;
    String FathherOcc;
    String motherName;
   String motherrNumber;
   String motherOcc;
   String email;
   String phone;
  String  present;
   String permanent;
   String fblink;
   String blood;
   String previous;
   String clubID;

    public MemberModel(String fname, String lname, String fullNam, String image, String fatherName, String fatherNumber, String fathherOcc, String motherName, String motherrNumber, String motherOcc, String email, String phone, String present, String permanent, String fblink, String blood, String previous, String clubID) {
        this.fname = fname;
        this.lname = lname;
        this.fullNam = fullNam;
        this.image = image;
        this.fatherName = fatherName;
        this.fatherNumber = fatherNumber;
        FathherOcc = fathherOcc;
        this.motherName = motherName;
        this.motherrNumber = motherrNumber;
        this.motherOcc = motherOcc;
        this.email = email;
        this.phone = phone;
        this.present = present;
        this.permanent = permanent;
        this.fblink = fblink;
        this.blood = blood;
        this.previous = previous;
        this.clubID = clubID;
    }

    public MemberModel() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFullNam() {
        return fullNam;
    }

    public void setFullNam(String fullNam) {
        this.fullNam = fullNam;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherNumber() {
        return fatherNumber;
    }

    public void setFatherNumber(String fatherNumber) {
        this.fatherNumber = fatherNumber;
    }

    public String getFathherOcc() {
        return FathherOcc;
    }

    public void setFathherOcc(String fathherOcc) {
        FathherOcc = fathherOcc;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherrNumber() {
        return motherrNumber;
    }

    public void setMotherrNumber(String motherrNumber) {
        this.motherrNumber = motherrNumber;
    }

    public String getMotherOcc() {
        return motherOcc;
    }

    public void setMotherOcc(String motherOcc) {
        this.motherOcc = motherOcc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getPermanent() {
        return permanent;
    }

    public void setPermanent(String permanent) {
        this.permanent = permanent;
    }

    public String getFblink() {
        return fblink;
    }

    public void setFblink(String fblink) {
        this.fblink = fblink;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getClubID() {
        return clubID;
    }

    public void setClubID(String clubID) {
        this.clubID = clubID;
    }
}
