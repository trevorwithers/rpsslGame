package comp208.withers.rpsslgame;

import static comp208.withers.rpsslgame.MainActivity.userList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

/**
 * This class is the results activity for the app. It contains the operational logic for the results activity.
 */
public class Results extends AppCompatActivity {
    /**
     * This method is called when the activity is created. It sets the content view and sets up the click listeners for the buttons.
     * It also sets the result message and the number of wins and losses for the user to see.
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the super class onCreate method
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_results layout
        setContentView(R.layout.activity_results);
        // Get the play again and main menu buttons
        Button playAgain = findViewById(R.id.playAgainBtn);
        Button mainMenu = findViewById(R.id.extendedMainMenuBtn);
        // Get the game type from the intent
        String gameType = getIntent().getStringExtra("gameType");
        // Assert that the game type is not null
        assert gameType != null;
        Integer index = getIntent().getIntExtra("index", 0);
        // Set the click listeners for the play again and main menu buttons
        clickListener(playAgain, new Intent(Results.this, gameType.equals("NormalGame") ? NormalGame.class : ExtendedGame.class), index);
        clickListener(mainMenu, new Intent(Results.this, MainActivity.class), index);
        // Get the result message, number of wins and number of losses text views
        TextView resultMsg = findViewById(R.id.resultMsg);
        TextView numWins = findViewById(R.id.numWins);
        TextView numLosses = findViewById(R.id.numLosses);
        // Set the result message and the number of wins and losses for the user to see
        resultMsg.setText(getIntent().getStringExtra("result"));
        numWins.setText("Wins: " + userList.get(index).getWins(gameType.equals("NormalGame")).toString());
        numLosses.setText("Losses: " + userList.get(index).getLosses(gameType.equals("NormalGame")).toString());
    }
    /**
     * This method sets the click listener for a button.
     * @param button The button to set the click listener for
     * @param intent The intent to start when the button is clicked
     */
    private void clickListener(Button button, Intent intent, Integer index) {
        // Set the click listener for the button
        button.setOnClickListener(v -> {
            intent.putExtra("index", index);
            // Start the activity for the intent
            startActivity(intent);
        });
    }
}