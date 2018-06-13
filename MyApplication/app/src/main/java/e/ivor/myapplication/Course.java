package e.ivor.myapplication;


import java.util.ArrayList;

public class Course {
    //課程名稱, 教室, 老師
    private String name;
    private String classRoom;
    private String teacher;
    private ArrayList<String> content;
    //第幾堂開始上, 總共上幾堂
    int start;
    int nums;

    public Course(String name, String classRoom, String teacher, int start, int nums) {
        this.name = name;
        this.classRoom = classRoom;
        this.teacher = teacher;
        this.start = start;
        this.nums = nums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNums() {
        return nums;
    }

    public void addContent(String s) {
        this.content.add(s);
    }

    public void delContent(String s) {
        for(int i = 0; i < content.size(); i++) {
            if(s.equals(content.indexOf(i))) {
                content.remove(i);
                return;
            }
        }
    }

    public ArrayList<String> getContent() {
        return content;
    }

}
