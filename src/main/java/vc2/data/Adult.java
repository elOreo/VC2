/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vc2.data;

import vc2.backprop.Input;

/**
 *
 * @author Tobias
 */
public class Adult implements Input{

    private static final String[] workclasses = new String[]{"Private", "Self-emp-not-inc", "Self-emp-inc", "Federal-gov", "Local-gov", "State-gov", "Without-pay", "Never-worked"};
    private static final String[] educations = new String[]{"Bachelors", "Some-college", "11th", "HS-grad", "Prof-school", "Assoc-acdm", "Assoc-voc", "9th", "7th-8th", "12th", "Masters", "1st-4th", "10th", "Doctorate", "5th-6th", "Preschool"};
    private static final String[] maritalStatuses = new String[]{"Married-civ-spouse", "Divorced", "Never-married", "Separated", "Widowed", "Married-spouse-absent", "Married-AF-spouse"};
    private static final String[] occupations = new String[]{"Tech-support", "Craft-repair", "Other-service", "Sales", "Exec-managerial", "Prof-specialty", "Handlers-cleaners", "Machine-op-inspct", "Adm-clerical", "Farming-fishing", "Transport-moving", "Priv-house-serv", "Protective-serv", "Armed-Forces"};
    private static final String[] relationships = new String[]{"Wife", "Own-child", "Husband", "Not-in-family", "Other-relative", "Unmarried"};
    private static final String[] races = new String[]{"White", "Asian-Pac-Islander", "Amer-Indian-Eskimo", "Other", "Black"};
    private static final String[] sexes = new String[]{"Female", "Male"};
    private static final String[] nativeCountries = new String[]{"United-States", "Cambodia", "England", "Puerto-Rico", "Canada", "Germany", "Outlying-US(Guam-USVI-etc)", "India", "Japan", "Greece", "South", "China", "Cuba", "Iran", "Honduras", "Philippines",
    "Italy", "Poland", "Jamaica", "Vietnam", "Mexico", "Portugal", "Ireland", "France", "Dominican-Republic", "Laos", "Ecuador", "Taiwan", "Haiti", "Columbia", "Hungary", "Guatemala", "Nicaragua", "Scotland", "Thailand", "Yugoslavia",
    "El-Salvador", "Trinadad&Tobago", "Peru", "Hong", "Holand-Netherlands"};

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
    private int fillVector(String value, String[] values, double[] out, int offset){
        for( int i = 0; i<values.length; i++){
            out[offset + i] = value.equals(values[i])?1:0;
        }
        return offset + values.length;
    }

    @Override
    public double[] getVector() {
        double[] result = new double[6+workclasses.length+educations.length+maritalStatuses.length+occupations.length+relationships.length+races.length+sexes.length+nativeCountries.length];
        int index = 0;
        result[index++] = age;
        index = fillVector(workclass, workclasses, result, index);
        result[index++] = fnlwgt;
        index = fillVector(education, educations, result, index);
        result[index++] = educationNum;
        index = fillVector(maritalStatus, maritalStatuses, result, index);
        index = fillVector(occupation, occupations, result, index);
        index = fillVector(relationship, relationships, result, index);
        index = fillVector(race, races, result, index);
        index = fillVector(sex, sexes, result, index);
        result[index++] = capitalGain;
        result[index++] = capitalLoss;
        result[index++] = hoursPerWeek;
        index = fillVector(nativeCountry, nativeCountries, result, index);
        return result;
    }
}
