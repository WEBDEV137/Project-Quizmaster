package controller;

import database.mysql.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Course;
import model.Question;
import model.Quiz;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorDashboardController extends AbstractController{

    private QuizDAO quizDAO;
    private QuestionDAO questionDAO;
    private CourseDAO courseDAO;
    private QuizQuestionDAO quizQuestionDAO;
    private DBAccess dbAccess;
    private User user;
    private Quiz quiz;
    private ArrayList<Quiz> Quizzes;
    private List<Question> questions;


    @FXML
    private ListView<Course> courseList;
    @FXML
    private ListView<Quiz> quizList;
    @FXML
    private ListView<Question> questionList;


    public void setup() {
        setupMainObjects();

        populateListView(courseList, courseDAO.getAllByCoordinatorId(user.getUserId()));
        courseList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Course>() {
                    @Override
                    public void changed(ObservableValue<? extends Course> observableValue, Course oldCourse, Course newCourse) {
                        Quizzes = quizDAO.getAllByCourseId(newCourse.getId());
                        populateListView(quizList, Quizzes);
                    }
                });

        quizList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Quiz>() {
                    @Override
                    public void changed(ObservableValue<? extends Quiz> observableValue, Quiz oldQuiz, Quiz newQuiz) {
                        questions = questionDAO.getAllQuestionsByQuizId(newQuiz.getId());
                        populateListView(questionList, questions);
                    }
                });
    }

    public void setupMainObjects(){
        user = Main.getCurrentUser();
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        this.quizDAO = new QuizDAO(dbAccess);
        this.courseDAO = new CourseDAO(dbAccess);
        this.questionDAO = new QuestionDAO(dbAccess);
    }
    public void doNewQuiz() { }

    public void doEditQuiz() { }

    public void doNewQuestion() { }

    public void doEditQuestion() { }

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }
}
