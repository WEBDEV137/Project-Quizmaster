package database.mysql;

import model.Answer;
import model.Question;
import model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnswerDAO extends AbstractDAO {

    public AnswerDAO(DBAccess dBaccess) {
        super(dBaccess);
    }


    /**
     * Haal alle alle drie de antwoorden op die bij een vraag horen
     *
     * @param questionId De id van de vraag
     * @return ArrayList<Answer>
     * Een lijst met de antwoorden van de vraag. Het juiste antwoord staat op plek met index 0
     */

    // Toms method
    public ArrayList<Answer> getAllByQuestionId(int questionId) {
        String query = "SELECT * FROM antwoord WHERE vraagid = ?;";
        ArrayList<Answer> answers = new ArrayList<>();
        Answer answer;
        Answer rightAnswer = new Answer();
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, questionId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                String text = resultSet.getString("tekst");
                questionId = resultSet.getInt("vraagid");
                boolean isRightAnswer = resultSet.getBoolean("isjuistantwoord");
                answer = new Answer(text, isRightAnswer);
                answer.setQuestionId(questionId);
                if (answer.isRightAnswer()) {
                    rightAnswer = answer;
                }
                else { answers.add(answer);
                }
            }
            if (!rightAnswer.isRightAnswer()) {
                System.out.println("No right answer for this question! id: " + questionId);
            }
            else{
                answers.add(0, rightAnswer);
                }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        } return answers;
    }

    // Joels method

    public ArrayList<Answer> getAllAnswersByQuestionId(int questionId) {
        String query = "SELECT * FROM antwoord WHERE vraagid = ?;";
        ArrayList<Answer> answers = new ArrayList<>();
        Answer answer;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, questionId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                String text = resultSet.getString("tekst");
                questionId = resultSet.getInt("vraagid");
                boolean isRightAnswer = resultSet.getBoolean("isjuistantwoord");
                answer = new Answer(text, isRightAnswer);
                answer.setQuestionId(questionId);
                answers.add(answer);

            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        } return answers;
    }

    public void storeOne(Answer answer, int rightAnswer, int questionId) {
        String query = "INSERT INTO antwoord VALUES (DEFAULT, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1,answer.getText());
            preparedStatement.setInt(2, questionId);
            preparedStatement.setString(3,String.valueOf(rightAnswer));
            executeManipulatePreparedStatement(preparedStatement);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    public void updateAnswerByAnswerText (String newAnswerText, String updatableText){
        String query = "UPDATE antwoord SET tekst = ? WHERE tekst = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1,newAnswerText);
            preparedStatement.setString(2,updatableText);
            executeManipulatePreparedStatement(preparedStatement);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }
    public void storeAnswer(String tekst, int vraagId, boolean isJuist) {
        String query = "INSERT INTO antwoord VALUES (DEFAULT, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1,tekst);
            preparedStatement.setInt(2, vraagId);
            preparedStatement.setBoolean(3,isJuist);
            executeManipulatePreparedStatement(preparedStatement);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }
    public void storeWrongAnswer1(Question question) {
            String query = "INSERT INTO antwoord VALUES (DEFAULT, ?, ?, ?);";
            try {
                PreparedStatement preparedStatement = getStatement(query);
                preparedStatement.setString(1,question.getQuestionText());
                preparedStatement.setInt(2, question.getQuestionID());
                preparedStatement.setBoolean(3,true);
                executeManipulatePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                System.out.println("SQL error " + e.getMessage());
            }
    }

    public void dropAllFromQuestion(int questionId) {
        String query = " DELETE FROM antwoord WHERE vraagid = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, questionId);
            executeManipulatePreparedStatement(preparedStatement);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }
}
