package controller;

import database.mysql.AnswerDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class CreateUpdateQuestionController extends AbstractController {
    private DBAccess dbAccess;
    private User user;
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private QuizDAO quizDAO;
    private Quiz quiz;
    private Question question;
    private boolean isNewCourse = false;

    private Question selectedQuestion;
    @FXML
    public Label titleLabel;
    @FXML
    public Label answerLabel;
    @FXML
    public TextField createUpdateRightAnswerTextField;
    @FXML
    public TextField createUpdateWrongAnswerTextField1;
    @FXML
    public TextField createUpdateWrongAnswerTextField2;
    @FXML
    public TextField createUpdateWrongAnswerTextField3;
    @FXML
    public TextField createUpdateQuestionTextField;
    @FXML
    public Button createUpdateAnswerButton;
    @FXML
    public MenuButton quizButton;

    public void setup(Question question) {
        user = Main.getCurrentUser();
        dbAccess = Main.getDBaccess();
        questionDAO = new QuestionDAO(dbAccess);
        answerDAO = new AnswerDAO(dbAccess);
       quizDAO = new QuizDAO(dbAccess);
       quiz = new Quiz();

        if (question == null) {
            this.question = new Question();
            isNewCourse = true;
            setupCreateNewQuestion();
        } else {
            this.question = question;
            setupUpdateQuestion();
        }
    }
    
    public void setupUpdateQuestion() {
        titleLabel.setText("Wijzig vraag");
        answerLabel.setText("Wijzig antwoorden");
        createUpdateQuestionTextField.setText(question.toString());
        populateQuizMenuButton();
        ArrayList<Answer> answerList = answerDAO.getAllByQuestionId(question.getQuestionID());
        createUpdateRightAnswerTextField.setText(answerList.get(0).getText());
        createUpdateWrongAnswerTextField1.setText(answerList.get(1).getText());
        createUpdateWrongAnswerTextField3.setDisable(false);
        createUpdateWrongAnswerTextField2.setDisable(false);
        if (answerList.size() ==  3) {
           createUpdateWrongAnswerTextField2.setText(answerList.get(2).getText());
        }
        else if(answerList.size() == 4){

            createUpdateWrongAnswerTextField3.setText(answerList.get(3).getText());

        }
    }
    public void setupCreateNewQuestion() {
        titleLabel.setText("Vraag");
        answerLabel.setText("Geef de antwoorden");
        //createUpdateAnswerButton.setText("Save");
        createUpdateWrongAnswerTextField2.setDisable(false);
        createUpdateWrongAnswerTextField3.setDisable(false);
        populateQuizMenuButton();
    }
    public void populateQuizMenuButton() {
        ArrayList<Quiz> quizzes = quizDAO.getAllByCoordinatorId(user.getUserId());

        for (Quiz quiz : quizzes) {
            System.out.println(quiz.getName());
            MenuItem menuItem = new MenuItem(quiz.getName());
            menuItem.setOnAction(event -> changeQuiz(quiz));
            quizButton.getItems().add(menuItem);

        }
    }
    public void changeQuiz(Quiz quiz) {
        this.question.setQuizID(quiz.getId());
        this.quiz = quiz;
        quizButton.setText(quiz.getName());
    }


    private void setQuestion(){ String questionTekst = createUpdateQuestionTextField.getText();
        System.out.println(questionTekst);
        boolean nameIsAllowed = checkIfNameAllowed(questionTekst, Const.NOT_ALLOWED_CHARACTERS);
        if (nameIsAllowed) {
            question.setQuestionText(questionTekst);
        } else showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
    }
    private void setNewRightAnswer(){ String answerTekst = createUpdateRightAnswerTextField.getText();
        boolean nameIsAllowed = checkIfNameAllowed(answerTekst, Const.NOT_ALLOWED_CHARACTERS);
        if (nameIsAllowed) {
            question.setRightAnswer(answerTekst);
        } else showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
    }
    private void setNewWrongAnswer1Answer(){ String answerTekst = createUpdateWrongAnswerTextField1.getText();
        boolean nameIsAllowed = checkIfNameAllowed(answerTekst, Const.NOT_ALLOWED_CHARACTERS);
        if (nameIsAllowed) {
            question.setWrongAnswer1(answerTekst);
        } else showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
    }
    private void setNewWrongAnswer2Answer(){ String answerTekst = createUpdateWrongAnswerTextField2.getText();
        boolean nameIsAllowed = checkIfNameAllowed(answerTekst, Const.NOT_ALLOWED_CHARACTERS);
        if (nameIsAllowed) {
            question.setWrongAnswer2(answerTekst);
        } else showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
    }
    private void setNewWrongAnswer3Answer(){ String answerTekst = createUpdateWrongAnswerTextField3.getText();
        boolean nameIsAllowed = checkIfNameAllowed(answerTekst, Const.NOT_ALLOWED_CHARACTERS);
        if (nameIsAllowed) {
            question.setWrongAnswer3(answerTekst);
        } else showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
    }

    public void storeInQuestionObject(){
        setQuestion();
        setNewRightAnswer();
        setNewWrongAnswer1Answer();
        setNewWrongAnswer2Answer();
        setNewWrongAnswer3Answer();
    }

/*    @FXML
    private void createQuestion(){
        String newQuestion = createUpdateQuestionTextField.getText();
        if (newQuestion.isEmpty()) {
            Alert nothingFilledIn = new Alert(Alert.AlertType.ERROR);
            nothingFilledIn.setContentText("Je moet een vraag invullen.");
            nothingFilledIn.show();
            return;
        }
        this.newQuestion = new Question(newQuestion);
    }

    @FXML
    private void createRightAnswer(){
        String rightAnswer = createUpdateRightAnswerTextField.getText();
        this.newRightAnswer = new Answer(rightAnswer, true);
    }

    @FXML
    private void createWrongAnswer1(){
        String newWrongAnswer = createUpdateWrongAnswerTextField1.getText();
        this.newWrongAnswer1 = new Answer(newWrongAnswer, false);
    }

    @FXML
    private void createWrongAnswer2(){
        String newWrongAnswer = createUpdateWrongAnswerTextField2.getText();
        this.newWrongAnswer2 = new Answer(newWrongAnswer, false);
    }

    @FXML
    private void createWrongAnswer3(){
        String newWrongAnswer = createUpdateWrongAnswerTextField3.getText();
        this.newWrongAnswer3 = new Answer(newWrongAnswer, false);
    }

    @FXML
    private void enableWrongAnswerTextfield2(){
        createUpdateWrongAnswerTextField2.setDisable(false);
    }

    @FXML
    private void enableWrongAnswerTextfield3(){
        createUpdateWrongAnswerTextField3.setDisable(false);
    }

    @FXML
    public void doCreateUpdateQuestionAndAnswers() {
        createQuestion();
        createRightAnswer();
        createWrongAnswer1();
        if (newRightAnswer.getText().isEmpty() || newWrongAnswer1.getText().isEmpty()){
            Alert nothingFilledIn = new Alert(Alert.AlertType.ERROR);
            nothingFilledIn.setContentText("Je moet minstens één juist en één fout antwoord invullen.");
            nothingFilledIn.show();
            return;
        }
        if (newWrongAnswer2 != null) {createWrongAnswer2();}
        if (newWrongAnswer3 != null) {createWrongAnswer3();}
        if (selectedQuestion == null) {
            questionDAO.storeOne(newQuestion);
            answerDAO.storeOne(newRightAnswer, 1, questionDAO.getIdByQuestionText(newQuestion.toString()));
            answerDAO.storeOne(newWrongAnswer1, 0, questionDAO.getIdByQuestionText(newQuestion.toString()));
            if (newWrongAnswer2 != null) {answerDAO.storeOne(newWrongAnswer2, 0, questionDAO.getIdByQuestionText(newQuestion.toString()));}
            if (newWrongAnswer3 != null) {answerDAO.storeOne(newWrongAnswer3, 0, questionDAO.getIdByQuestionText(newQuestion.toString()));}
            Alert stored = new Alert(Alert.AlertType.INFORMATION);
            stored.setContentText("Vraag en antwoorden opgeslagen");
            stored.show();
        } else {
            questionDAO.updateQuestionByQuestionText(newQuestion.toString(), selectedQuestion.toString());
            Alert updated = new Alert(Alert.AlertType.INFORMATION);
            updated.setContentText("Vraag en antwoorden gewijzigd");
            updated.show();
        }
    }*/


    @FXML
    public void doBackToManage() {
        Main.getSceneManager().showManageQuestionsScene();
    }

    public void doMenu() { Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());}

    public void doStore(){
        storeInQuestionObject();
        if(isNewCourse){
            int questionId =  questionDAO.storeOneReturnQuizId(question.getQuestionText(), question.getQuizID());
            answerDAO.storeAnswer(question.getRightAnswer(), questionId, true);
            answerDAO.storeAnswer(question.getWrongAnswer1(), questionId, false);
            answerDAO.storeAnswer(question.getWrongAnswer2(), questionId, false);
            answerDAO.storeAnswer(question.getWrongAnswer3(), questionId, false);
        }
        else{
            int id = question.getQuestionID();
            questionDAO.updateQuestion(question);
            answerDAO.dropAllFromQuestion(id);
            answerDAO.storeAnswer(question.getRightAnswer(), id, true);
            answerDAO.storeAnswer(question.getWrongAnswer1(), id, false);
            answerDAO.storeAnswer(question.getWrongAnswer2(), id, false);
            answerDAO.storeAnswer(question.getWrongAnswer3(), id, false);

        }

    }

}