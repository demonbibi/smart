package teacher;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Set;

/**
 * Created by baidu on 16/5/10.
 */
public class HttpParser {
    private static final String TAG = "HTTPPARSER";
    public static void parseRoomStudent(String result,
                                        Set<Student> students,
                                        Course course) {
       try {
           JSONObject object = new JSONObject(result);
           JSONArray objects = object.getJSONArray("students");
           JSONArray chapters = object.getJSONArray("chapters");
           parseCourseInfo(chapters, course);
           Student student;
           if(objects.length()>0){
               for(int i=0; i<objects.length(); i++){
                   JSONObject o = objects.getJSONObject(i);
                   student = new Student();
                   student.setStudentId(o.getInt("studentId"));
                   student.setStudentName(o.getString("studentName"));
                   students.add(student);
                   Log.i(TAG, student.toString());
               }
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
    }
    public static void parseCourseInfo(JSONArray objects, Course course) {

        try {
            if(objects.length()>0){
                course.setChapters(objects.length());
                for(int i=0; i<objects.length(); i++){
                    JSONObject o = objects.getJSONObject(i);
                    if(i==0){
                        course.setCourseId(o.getInt("courseId"));
                    }
                    course.addChapterId(o.getInt("chapterId"));
                    course.addScene(o.getInt("sceneNumbers"));
//                    Log.i(TAG, "scene: "+o.getInt("sceneNumbers"));
                }
//                Log.i(TAG, "chapters: "+objects.length());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void parseStudentChapterSceneScore(String result, List<UniversalObject> sceneScores){
        if(result.equals("")){
            Log.i(TAG, "result has no info");
        }
        UniversalObject uo;
        try {
            JSONObject jsonObject = new JSONObject(result);
            String courseId = String.valueOf(jsonObject.getInt("courseId"));
            JSONArray chapters = jsonObject.getJSONArray("chaptersVO");
            if(chapters.length()>0){
                for(int i=0; i<chapters.length(); i++){
                    JSONObject chapter = chapters.getJSONObject(i);
                    String chapterId = String.valueOf(chapter.getInt("chapterId"));
                    JSONArray scenes = chapter.getJSONArray("sceneVO");
                    if(scenes.length()>0){
                        for(int j=0; j<scenes.length(); j++){
                            JSONObject scene = scenes.getJSONObject(j);
                            String sceneId = String.valueOf(scene.getInt("sceneId"));
                            JSONArray studentSceneScores = scene.getJSONArray("studentSceneScoreVO");
                            if(studentSceneScores.length()>0){
                                for(int k=0; k<studentSceneScores.length(); k++){
                                    JSONObject studentSceneScore = studentSceneScores.getJSONObject(k);
                                    String studentId = String.valueOf(studentSceneScore.getInt("studentId"));
                                    String score = String.valueOf(studentSceneScore.getInt("score"));
                                    String totalId = courseId+"."+chapterId+"."+sceneId+"."+studentId;
                                    uo = new UniversalObject(totalId,score);
                                    Log.i(TAG, uo.toString());
                                    sceneScores.add(uo);
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void parseStudentExerciseRecord(String result,
                                                  List<UniversalObject> universalObjectList){
        if(result.equals("")){
            Log.i(TAG, "result has no info");
            return;
        }
        UniversalObject uo;
        try {
            JSONObject jsonObject = new JSONObject(result);
            String studentId = String.valueOf(jsonObject.getInt("studentId"));
            JSONArray courses = jsonObject.getJSONArray("courseVO");
            if(courses.length()>0){
                for(int i=0; i<courses.length(); i++){
                    JSONObject course = courses.getJSONObject(i);
                    String courseId = String.valueOf(course.getInt("courseId"));
                    JSONArray chapters = course.getJSONArray("chapterScores");
                    if(chapters.length()>0){
                        for(int j=0; j<chapters.length(); j++){
                            JSONObject chapter = chapters.getJSONObject(j);
                            String chapterId = String.valueOf(chapter.getInt("chpaterId"));
                            JSONArray scenes = chapter.getJSONArray("sceneScoresVO");
                            if(scenes.length()>0){
                                for(int k=0; k<scenes.length(); k++){
                                    JSONObject sceneScoreVO = scenes.getJSONObject(k);
                                    String sceneId = String.valueOf(sceneScoreVO.getInt("sceneId"));
                                    String score = String.valueOf(sceneScoreVO.getInt("score"));
                                    String totalId = studentId+"."+courseId+"."+chapterId+"."+sceneId;
                                    uo = new UniversalObject(totalId,score);
                                    Log.i(TAG, uo.toString());
                                    universalObjectList.add(uo);
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void parseLearnSituation(String result, List<UniversalObject> universalObjectList) {
        if(result.equals("")){
            Log.i(TAG, "result has no info");
            return;
        }
        UniversalObject universalObject;
        try {
            JSONObject object = new JSONObject(result);
            String studentId = object.getString("studentId");
            JSONArray courses = object.getJSONArray("courseVO");
            for(int i=0; i<courses.length(); i++){
                JSONObject course = courses.getJSONObject(i);
                String courseId = course.getString("courseId");
                JSONArray chapters = course.getJSONArray("chapterScores");
                for(int j=0; j<chapters.length(); j++){
                    JSONObject chapter = chapters.getJSONObject(j);
                    String chapterId = chapter.getString("chpaterId");
                    String score = chapter.getString("score")!=null ? chapter.getString("score"):"";
                    String comment = chapter.getString("comment")!=null? chapter.getString("comment"):"";
                    universalObject = new UniversalObject(
                            studentId+"."+courseId+"."+chapterId, score, comment);
                    universalObjectList.add(universalObject);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
