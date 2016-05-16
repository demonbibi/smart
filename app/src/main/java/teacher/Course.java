package teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CC on 2016/5/10.
 */
public class Course {


    private int chapters;//有多少个课次
    private List<Integer> chapterIds;
    private int courseId;

    public List<Integer> getSceneNumbers() {
        return sceneNumbers;
    }

    private List<Integer> sceneNumbers;


    public Course(){
        sceneNumbers = new ArrayList<>();
        chapterIds = new ArrayList<>();
    }

    public int getChapters() {
        return chapters;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void addScene(int a){
        sceneNumbers.add(a);
    }

    public int getScene(int position) {
        return sceneNumbers.get(position);
    }

    public void addChapterId(int a){
        chapterIds.add(a);
    }
    public int getChapterId(int position){
        return chapterIds.get(position);
    }
}
