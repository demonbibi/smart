package teacher;

/**
 * Created by CC on 2016/5/14.
 */
public class UniversalObject {

    private String totalId;//courseId.chapterId.sceneId.studentId
    private String score;//场景分数

    private String studentTotalId;//studentId.courseId.chapterId
    private String chapterScore;//课次分数
    private String chapterComment;//教师评语

    public UniversalObject(String totalId, String score){
        this.totalId = totalId;
        this.score = score;
    }

    public UniversalObject(String studentTotalId,
                           String chapterScore, String chapterComment){
        this.studentTotalId = studentTotalId;
        this.chapterScore = chapterScore;
        this.chapterComment = chapterComment;
    }

    public String getTotalId() {
        return totalId;
    }

    public void setTotalId(String totalId) {
        this.totalId = totalId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String toString(){
        return totalId+": "+score;
    }

    public String getChapterScore() {
        return chapterScore;
    }

    public void setChapterScore(String chapterScore) {
        this.chapterScore = chapterScore;
    }

    public String getChapterComment() {
        return chapterComment;
    }

    public void setChapterComment(String chapterComment) {
        this.chapterComment = chapterComment;
    }

    public String getStudentTotalId() {
        return studentTotalId;
    }

    public void setStudentTotalId(String studentTotalId) {
        this.studentTotalId = studentTotalId;
    }

}
