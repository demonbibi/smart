package teacher;

/**
 * Created by CC on 2016/5/9.
 */
public class Student {
    public Integer getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    private Integer studentId;
    private String studentName;
    private Integer level;
    private Integer userId;

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    private boolean isConnect;

    @Override
    public boolean equals(Object o) {
        if(this == o)return true;
        if(((Student)o).studentId.equals(this.getStudentId()))return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return studentId;
    }

    public String toString(){
        return studentId+" "+studentName;
    }
}
