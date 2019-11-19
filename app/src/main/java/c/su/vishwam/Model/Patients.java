package c.su.vishwam.Model;

public class Patients {
    private String name,email,details, problem, others,time,date,id,sex,age,phone,image,pulse,bp,bloodgroup,anemia,medicalhistory,allergy,courseinhospital,bed,ward, complaints, gender,relation,weight,visit, referredby;

    public Patients(){

    }

    public Patients(String name, String problem, String others, String time, String date, String id, String sex, String age,String phone,String image,
                    String pulse,String bp,String bloodgroup,String anemia,String medicalhistory,String allergy,String courseinhospital,
                    String bed,String ward, String complaints, String gender, String relation, String weight,String visit, String referredby, String email, String details) {
        this.name = name;
        this.problem = problem;
        this.others = others;
        this.time = time;
        this.date = date;
        this.id = id;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.image = image;
        this.pulse = pulse;
        this.bp = bp;
        this.bloodgroup = bloodgroup;
        this.anemia = anemia;
        this.medicalhistory = medicalhistory;
        this.allergy = allergy;
        this.courseinhospital = courseinhospital;
        this.bed = bed;
        this.ward = ward;
        this.complaints = complaints;
        this.gender = gender;
        this.relation= relation;
        this.weight = weight;
        this.visit = visit;
        this.referredby = referredby;
        this.email = email;
        this.details = details;

    }

    public void setMedicalhistory(String medicalhistory) {
        this.medicalhistory = medicalhistory;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAnemia() {
        return anemia;
    }

    public void setAnemia(String anemia) {
        this.anemia = anemia;
    }

    public String getMedicalhistory() {
        return medicalhistory;
    }

    public void setMedicslhistory(String medicalhistory) {
        this.medicalhistory = medicalhistory;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getCourseinhospital() {
        return courseinhospital;
    }

    public void setCourseinhospital(String courseinhospital) {
        this.courseinhospital = courseinhospital;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getReferredby() {
        return referredby;
    }

    public void setReferredby(String referredby) {
        this.referredby = referredby;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
