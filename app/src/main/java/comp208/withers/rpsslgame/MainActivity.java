package comp208.withers.rpsslgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is the main activity for the app. It contains the main menu and the login/logout functionality.
 * It also contains the click listeners for the normal and extended game buttons.
 */
public class MainActivity extends AppCompatActivity {
    // The list of users which is populated from the shared preferences file
    static ArrayList<Score> userList = new ArrayList<>();
    // A boolean to keep track of whether a user is logged in
    static Boolean isLoggedIn = false;
    /**
     * This method is called when the activity is created. It sets the content view and sets up the click listeners for the buttons.
     * It also sets the visibility of the logout button based on whether a user is logged in or not.
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the super class onCreate method
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_main layout
        setContentView(R.layout.activity_main);
        // Get the logout button and the normal and extended game buttons
        final Button logOutBtn = findViewById(R.id.logOutBtn);
        final Button clearScoresBtn = findViewById(R.id.clearScoresBtn);
        final ImageButton normalGame = findViewById(R.id.normalGame);
        final ImageButton extendedGame = findViewById(R.id.extendedGame);
        // Set the visibility of the logout button based on whether a user is logged in or not
        logOutBtn.setVisibility(isLoggedIn ? View.VISIBLE : View.INVISIBLE);
        clearScoresBtn.setVisibility(isLoggedIn ? View.VISIBLE : View.INVISIBLE);
        // Set the click listener for the logout button
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the isLoggedIn boolean to false and set the visibility of the logout button to invisible
                isLoggedIn = false;
                logOutBtn.setVisibility(View.INVISIBLE);
            }
        });
        clearScoresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer index = getIntent().getIntExtra("index", 0);
                ScoreLogic.clearUserScores(index, MainActivity.this);
            }
        });
        // Set the click listeners for the normal and extended game buttons
        clickListener(normalGame, "normal");
        clickListener(extendedGame, "extended");
    }
    /**
     * This method shows the login popup when a user tries to play a game without being logged in.
     * @param gameType The type of game the user is trying to play
     */
    private void showLoginPopup(String gameType) {
        LoginPopup loginPopup = new LoginPopup(gameType);
        loginPopup.show(getSupportFragmentManager(), "login_popup");
    }
    /**
     * This method sets the click listener for the normal and extended game buttons.
     * @param button The button to set the click listener for
     * @param gameType The type of game the button is for
     */
    private void clickListener(ImageButton button, String gameType) {
        // Set the click listener for the button
        button.setOnClickListener(v -> {
            // If the user is logged in, start the game activity with the game type as an extra
            if (isLoggedIn) {
                // Create an intent for the game activity based on the game type
                Intent intent = new Intent(MainActivity.this, gameType.equals("normal") ? NormalGame.class : ExtendedGame.class);
                // Add the game type as an extra to the intent
                intent.putExtra("gameType", gameType);
                // Start the game activity
                startActivity(intent);
            } else {
                // If the user is not logged in, show the login popup
                showLoginPopup(gameType);
            }
        });
    }
}