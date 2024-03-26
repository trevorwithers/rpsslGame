package comp208.withers.rpsslgame;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;
import static comp208.withers.rpsslgame.MainActivity.userList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class is the logic for the score of the user. It contains the operational logic for the score of the user.
 */
public class ScoreLogic {
    // The score data access object
    static ScoreDao scoreDao;
    // The map of user wins which contains the user choice and the computer choice and the reason for the user winning
    private static final Map<String, Map<String, String>> userWinsMap = new HashMap<String, Map<String, String>>() {{
        put("rockIcon.png", new HashMap<String, String>() {{
            put("scissorsIcon.png", "User wins because rock crushes scissors");
            put("lizardIcon.png", "User wins because rock crushes lizard");
        }});
        put("paperIcon.png", new HashMap<String, String>() {{
            put("rockIcon.png", "User wins because paper covers rock");
            put("spockIcon.png", "User wins because paper disproves spock");
        }});
        put("scissorsIcon.png", new HashMap<String, String>() {{
            put("paperIcon.png", "User wins because scissors cuts paper");
            put("lizardIcon.png", "User wins because scissors decapitates lizard");
        }});
        put("lizardIcon.png", new HashMap<String, String>() {{
            put("paperIcon.png", "User wins because lizard eats paper");
            put("spockIcon.png", "User wins because lizard poisons spock");
        }});
        put("spockIcon.png", new HashMap<String, String>() {{
            put("rockIcon.png", "User wins because spock vaporizes rock");
            put("scissorsIcon.png", "User wins because spock smashes scissors");
        }});
    }};
    // The map of computer wins which contains the computer choice and the user choice and the reason for the computer winning
    private static final Map<String, Map<String, String>> computerWinsMap = new HashMap<String, Map<String, String>>() {{
        put("rockIcon.png", new HashMap<String, String>() {{
            put("scissorsIcon.png", "Computer wins because rock crushes scissors");
            put("lizardIcon.png", "Computer wins because rock crushes lizard");
        }});
        put("paperIcon.png", new HashMap<String, String>() {{
            put("rockIcon.png", "Computer wins because paper covers rock");
            put("spockIcon.png", "Computer wins because paper disproves spock");
        }});
        put("scissorsIcon.png", new HashMap<String, String>() {{
            put("paperIcon.png", "Computer wins because scissors cuts paper");
            put("lizardIcon.png", "Computer wins because scissors decapitates lizard");
        }});
        put("lizardIcon.png", new HashMap<String, String>() {{
            put("paperIcon.png", "Computer wins because lizard eats paper");
            put("spockIcon.png", "Computer wins because lizard poisons spock");
        }});
        put("spockIcon.png", new HashMap<String, String>() {{
            put("rockIcon.png", "Computer wins because spock vaporizes rock");
            put("scissorsIcon.png", "Computer wins because spock smashes scissors");
        }});
    }};
    /**
     * This is the constructor for the score logic.
     */
    public ScoreLogic() {
    }

    /**
     * This method calculates the results of the game and returns an intent for the results activity.
     * @param userChoice The user's choice
     * @param computerChoice The computer's choice
     * @param index The index of the user
     * @param gameType The type of game
     * @param context The context for the app
     * @return The intent for the results activity
     */
    public static Intent calculateResults(String userChoice, String computerChoice, Integer index, Boolean gameType, Context context){
        scoreDao = ScoreDB.getInstance(context).scoreDao();
        // Create a new game element for the user and the computer
        // This is used to compare the user's choice and the computer's choice
        GameElement user = new GameElement(userChoice);
        GameElement computer = new GameElement(computerChoice);
        // Set the result to it is a draw as the default result
        String result = "It is a draw";
        // Compare the user's choice and the computer's choice
        switch (user.compareTo(computer)){
            // If the user wins, set the result to the reason for the user winning and increment the number of wins for the user
            case 1:
                result = Objects.requireNonNull(userWinsMap.get(userChoice)).get(computerChoice);
                userList.get(index).setWins(gameType, userList.get(index).getWins(gameType) + 1);
                // Save the user's score to the database
                scoreDao.insertOrUpdate(userList.get(index));
                break;
            // If the user loses, set the result to the reason for the computer winning and increment the number of losses for the user
            case -1:
                result = Objects.requireNonNull(computerWinsMap.get(computerChoice)).get(userChoice);
                userList.get(index).setLosses(gameType, userList.get(index).getLosses(gameType) + 1);
                // Save the user's score to the database
                scoreDao.insertOrUpdate(userList.get(index));
                break;
        }
        // Save the user's score to the shared preferences
        SharedPreferences preferences = context.getSharedPreferences("MainActivity", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        final String json = gson.toJson(userList.get(index));
        editor.putString(userList.get(index).getName(), json);
        editor.commit();
        // Create an intent for the results activity and put the result, index and game type as extras
        Intent intent = new Intent(context, Results.class);
        intent.putExtra("result", result);
        intent.putExtra("index", index);
        intent.putExtra("gameType", gameType ? "NormalGame" : "ExtendedGame");
        // Return the intent for the results activity
        return intent;
    }
    /**
     * This method clears the user's scores and removes the user's score from the database and shared preferences.
     * @param index The index of the user
     * @param context The context for the app
     */
    public static void clearUserScores(Integer index, Context context){
        // Create a score dao instance
        scoreDao = ScoreDB.getInstance(context).scoreDao();
        // Remove the user's score from the database
        scoreDao.deleteById(index);
        // Set the number of wins and losses for the user to 0 in the user list
        userList.get(index).setNumberOfNormalWins(0);
        userList.get(index).setNumberOfNormalLosses(0);
        userList.get(index).setNumberOfExtendedLosses(0);
        userList.get(index).setNumberOfExtendedWins(0);
        // Save the user's score to the shared preferences
        SharedPreferences preferences = context.getSharedPreferences("MainActivity", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        final String json = gson.toJson(userList.get(index));
        editor.putString(userList.get(index).getName(), json);
        editor.commit();
    }
}
