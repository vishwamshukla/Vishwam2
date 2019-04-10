package c.su.vishwam.Model;

public class IpdPatients {
    private String name, problem, others, age, sex,time,date,phone,image,ward,bed;

    public IpdPatients() {
    }

    public IpdPatients(String name, String problem, String others, String age, String sex, String time, String date,String phone,String image,String ward,String bed) {
        this.name = name;
        this.problem = problem;
        this.others = others;
        this.age = age;
        this.sex = sex;
        this.time = time;
        this.date = date;
        this.phone = phone;
        this.image = image;
        this.ward = ward;
        this.bed = bed;
    }
    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward= ward;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
