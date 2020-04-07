package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Question;
import model.User;

import java.util.List;

public class QuestionCouchDBDAO {
    private QuestionCouchDBaccess db;
    private Gson gson;

    public QuestionCouchDBDAO(QuestionCouchDBaccess db) {
        super();
        this.db = db;
        gson = new Gson();
    }

    public String saveSingleQuestion(Question question) {
        String jsonstring = gson.toJson(question);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = db.saveDocument(jsonobject);
        return doc_Id;
    }

    public Question getQuestionByDocId(String doc_Id) {
        JsonObject json = db.getClient().find(JsonObject.class, doc_Id);
        Question resultaat = gson.fromJson(json, Question.class);
        return resultaat;
    }

    public Question getQuestion(String questionText) {
        Question resultaat = null;
        List<JsonObject> allQuestions = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject json : allQuestions) {
            resultaat = gson.fromJson(json, Question.class);
            if (resultaat.getQuestionText().equals(questionText)) {
                return resultaat;
            }
        }
        return resultaat;
    }
}
