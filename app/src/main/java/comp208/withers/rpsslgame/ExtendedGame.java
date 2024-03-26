package comp208.withers.rpsslgame;

import static comp208.withers.rpsslgame.MainActivity.userList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is the activity for the extended game. It contains the operational logic for the extended game.
 */
public class ExtendedGame extends AppCompatActivity {
    // The index of the user in the userList
    Integer index;
    // The buttons for the user to pick an option
    ImageButton extendedRockBtn;
    ImageButton extendedPaperBtn;
    ImageButton extendedScissorsBtn;
    ImageButton extendedLizardBtn;
    ImageButton extendedSpockBtn;
    // The image views for the computer and user choices
    ImageView computerChoice;
    ImageView userChoice;
    // The asset manager for the app
    AssetManager assetManager;
    // The list of image names
    ArrayList<String> imageNames = new ArrayList<String>();
    // A random number generator
    Random random = new Random();
    /**
     * This method is called when the activity is created. It sets the content view and sets up the click listeners for the buttons.
     * It also sets the number of wins and losses for the user to see.
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the super class onCreate method
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_extended_game layout
        setContentView(R.layout.activity_extended_game);
        // Get the main menu button and set the click listener for it
        Button extendedMainMenuBtn = findViewById(R.id.extendedMainMenuBtn);
        // Set the click listener for the main menu button
        extendedMainMenuBtn.setOnClickListener(v -> {
            // Create an intent to go to the main activity
            Intent intent = new Intent(ExtendedGame.this, MainActivity.class);
            // Start the main activity
            startActivity(intent);
        });
        // Get the number of wins and losses for the user and set the text for the number of wins and losses
        TextView normalNumWinsBox = findViewById(R.id.extendedNumWinsBox);
        TextView normalNumLossesBox = findViewById(R.id.extendedNumLossesBox);
        // Get the index of the user from the intent
        index = getIntent().getIntExtra("index", 0);
        normalNumWinsBox.setText(userList.get(index).getWins(false).toString());
        normalNumLossesBox.setText(userList.get(index).getLosses(false).toString());
        // Get the asset manager and initialise the image asset list
        assetManager = getAssets();
        initImageAssetList();
        // Get the rock, paper, scissors, lizard and spock buttons and set the click listeners for them
        extendedRockBtn = findViewById(R.id.extendedRockBtn);
        extendedPaperBtn = findViewById(R.id.extendedPaperBtn);
        extendedScissorsBtn = findViewById(R.id.extendedScissorsBtn);
        extendedLizardBtn = findViewById(R.id.extendedLizardBtn);
        extendedSpockBtn = findViewById(R.id.extendedSpockBtn);
        computerChoice = findViewById(R.id.extendedComputerChoice);
        userChoice = findViewById(R.id.extendedUserChoice);
        clickListener(extendedRockBtn, "rockIcon.png");
        clickListener(extendedPaperBtn, "paperIcon.png");
        clickListener(extendedScissorsBtn, "scissorsIcon.png");
        clickListener(extendedLizardBtn, "lizardIcon.png");
        clickListener(extendedSpockBtn, "spockIcon.png");
    }
    /**
     * This method sets the click listener for the rock, paper, scissors, lizard and spock buttons.
     * @param button The button to set the click listener for
     * @param image The image to display when the button is clicked
     */
    private void clickListener(ImageButton button, String image) {
        // Set the click listener for the button
        button.setOnClickListener(v -> {
            // Create a new game element for the user choice
            displayImage(userChoice, image);
            String computerChoiceStr = computerPickAnOption();
            // Show the results after 3 seconds
            showResults(image, computerChoiceStr);
        });
    }
    /**
     * This method shows the results of the game after the user has picked an option and the computer has picked an option.
     * @param userChoiceFinal The image the user picked
     * @param computerChoiceFinal The image the computer picked
     */
    private void showResults(String userChoiceFinal, String computerChoiceFinal) {
        // Create a count down timer to show the results after 3 seconds
        CountDownTimer countDownTimer = new CountDownTimer(3000, 3000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                // Start the results activity with the result of the game by calling the calculateResults method in the score class
                // and passing the user's choice, the computer's choice, the index of the user and the game type, true for normal game
                // and false for extended game and the application context
                startActivity(ScoreLogic.calculateResults(userChoiceFinal, computerChoiceFinal, index, false, getApplicationContext()));
            }
        };
        // Start the count down timer
        countDownTimer.start();
    }
    /**
     * This method gets the computer to pick an option and displays the image of the option the computer picked.
     * @return The image the computer picked
     */
    private String computerPickAnOption() {
        // Get the computer to pick an option
        String computerChoice = imageNames.get(random.nextInt(imageNames.size()));
        // Display the image of the option the computer picked
        displayImage(this.computerChoice, computerChoice);
        // Return the image the computer picked
        return computerChoice;
    }
    /**
     * This method displays an image in an image view.
     * @param computerChoice The image view to display the image in
     * @param image The image to display
     */
    private void displayImage(ImageView computerChoice, String image) {
        // Try to get the image from the asset manager and display it in the image view
        try {
            // Get the input stream for the image
            InputStream inputStream = assetManager.open(image);
            // Decode the input stream into a bitmap
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            // Set the bitmap in the image view
            computerChoice.setImageBitmap(bmp);
            // Close the input stream
            inputStream.close();
        } catch (Exception e) {
            Log.i("+++", "Problem getting image resource");
        }
    }
    /**
     * This method initialises the image asset list.
     */
    private void initImageAssetList() {
        // Try to get the names of the images from the assets folder
        try {
            // Get the names of the images from the assets folder
            String[] assets = assetManager.list("");
            // Add the names of the images to the imageNames list
            for (String asset : assets) {
                // If the asset is a png file, add it to the imageNames list
                if (asset.endsWith(".png")) {
                    // Add the name of the image to the imageNames list
                    imageNames.add(asset);
                }
            }
        } catch (Exception e) {
            Log.i(">>>", e.getMessage());
        }
    }
}