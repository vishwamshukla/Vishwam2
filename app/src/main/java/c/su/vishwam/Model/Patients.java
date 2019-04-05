package c.su.vishwam.Model;

public class Patients {
    private String name, problem, others,time,date,id;

    public Patients(){

    }

    public Patients(String name, String problem, String others, String time, String date, String id) {
        this.name = name;
        this.problem = problem;
        this.others = others;
        this.time = time;
        this.date = date;
        this.id = id;
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
}
