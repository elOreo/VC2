/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vc2.data;

/**
 *
 * @author Tobias
 */
public class Adult {
    
    
    private final int age;
    private final String workclass;
    private final int fnlwgt;
    private final String education;
    private final int educationNum;
    private final String maritalStatus;
    private final String occupation;
    private final String relationship;
    private final String race;
    private final String sex;
    private final int capitalGain;
    private final int capitalLoss;
    private final int hoursPerWeek;
    private final String nativeCountry;
    
    public Adult(
        String age,
        String workclass,
        String fnlwgt,
        String education,
        String educationNum,
        String maritalStatus,
        String occupation,
        String relationship,
        String race,
        String sex,
        String capitalGain,
        String capitalLoss,
        String hoursPerWeek,
        String nativeCountry)
    {
    
        this.age = Integer.parseInt(age);
        this.workclass = workclass;
        this.fnlwgt = Integer.parseInt(fnlwgt);
        this.education = education;
        this.educationNum = Integer.parseInt(educationNum);
        this.maritalStatus = maritalStatus;
        this.occupation = occupation;
        this.relationship = relationship;
        this.race = race;
        this.sex = sex;
        this.capitalGain = Integer.parseInt(capitalGain);
        this.capitalLoss = Integer.parseInt(capitalLoss);
        this.hoursPerWeek = Integer.parseInt(hoursPerWeek);
        this.nativeCountry = nativeCountry;
    }
    
    public Adult(String[] data){
        this(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11], data[12], data[13]);
    }

    public int getAge() {
        return age;
    }

    public String getWorkclass() {
        return workclass;
    }

    public int getFnlwgt() {
        return fnlwgt;
    }

    public String getEducation() {
        return education;
    }

    public int getEducationNum() {
        return educationNum;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getRace() {
        return race;
    }

    public String getSex() {
        return sex;
    }

    public int getCapitalGain() {
        return capitalGain;
    }

    public int getCapitalLoss() {
        return capitalLoss;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public String getNativeCountry() {
        return nativeCountry;
    }
}
