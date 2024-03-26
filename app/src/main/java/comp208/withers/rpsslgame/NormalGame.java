package comp208.withers.rpsslgame;

import static comp208.withers.rpsslgame.MainActivity.userList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * This class is the activity for the normal game. It contains the operational logic for the normal game.
 */
public class NormalGame extends AppCompatActivity {
    // The index of the user in the userList
    Integer index;
    // The buttons for the user to pick an option
    ImageButton rockBtn;
    ImageButton paperBtn;
    ImageButton scissorsBtn;
    ImageView computerChoice;
    ImageView userChoice;
    // The asset manager for the app
    AssetManager assetManager;
    // The list of image names
    ArrayList<String> imageNames = new ArrayList<>();
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
        // Set the content view to the activity_normal_game layout
        setContentView(R.layout.activity_normal_game);
        // Get the main menu button and set the click listener for it
        Button normalMainMenuBtn = findViewById(R.id.normalMainMenuBtn);
        normalMainMenuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(NormalGame.this, MainActivity.class);
            startActivity(intent);
        });
        // Get the number of wins and losses for the user and set the text for the number of wins and losses
        TextView normalNumWinsBox = findViewById(R.id.normalNumWinsBox);
        TextView normalNumLossesBox = findViewById(R.id.normalNumLossesBox);
        // Get the index of the user from the intent and set the number of wins and losses for the user
        index = getIntent().getIntExtra("index", 0);
        // Create a score dao instance
        ScoreDao scoreDao = ScoreDB.getInstance(this).scoreDao();
        // Get the number of normal wins by getting the id of the user by the user's name
        Integer normalWins = scoreDao.getNormalWins(scoreDao.getId(userList.get(index).getName()));
        // Set the number of normal wins if there was a user found and a score was found
        normalNumWinsBox.setText(normalWins != null ? normalWins.toString() : "0");
        // Get the number of normal losses by getting the id of the user by the user's name
        Integer normalLosses = scoreDao.getNormalLosses(scoreDao.getId(userList.get(index).getName()));
        // Set the number of normal losses if there was a user found and a score was found
        normalNumLossesBox.setText(normalLosses != null ? normalLosses.toString() : "0");
        // Get the asset manager and initialise the image asset list
        assetManager = getAssets();
        initImageAssetList();
        // Get the rock, paper and scissors buttons and set the click listeners for them
        rockBtn = findViewById(R.id.rockBtn);
        paperBtn = findViewById(R.id.paperBtn);
        scissorsBtn = findViewById(R.id.scissorsBtn);
        computerChoice = findViewById(R.id.computerChoice);
        userChoice = findViewById(R.id.userChoice);
        clickListener(rockBtn, "rockIcon.png");
        clickListener(paperBtn, "paperIcon.png");
        clickListener(scissorsBtn, "scissorsIcon.png");
    }
    /**
     * This method sets the click listener for the rock, paper and scissors buttons.
     * @param button The button to set the click listener for
     * @param image The image to display when the button is clicked
     */
    private void clickListener(ImageButton button, String image) {
        // Set the click listener for the button
        button.setOnClickListener(v -> {
            // When the button is clicked, display the image the user picked and get the computer to pick an option
            displayImage(userChoice, image);
            String computerChoiceStr = computerPickAnOption();
            // Show the results of the game
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
                startActivity(ScoreLogic.calculateResults(userChoiceFinal, computerChoiceFinal, index, true, getApplicationContext()));
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
        // Try to get the image from the assets and display it in the image view
        try {
            // Get the input stream for the image
            InputStream inputStream = assetManager.open(image);
            // Decode the input stream into a bitmap
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            // Set the bitmap to the image view
            computerChoice.setImageBitmap(bmp);
            // Close the input stream
            inputStream.close();
        } catch (Exception e) {
            Log.i("+++", "Problem getting image resource");
        }
    }
    /**
     * This method initialises the image asset list by getting the names of the images from the assets folder.
     */
    private void initImageAssetList() {
        // Try to get the names of the images from the assets folder
        try {
            // Get the names of the images from the assets folder
            String[] assets = assetManager.list("");
            // Add the names of the images to the imageNames list
            assert assets != null;
            for (String asset : assets) {
                // If the asset is a png image, add it to the imageNames list
                if (asset.endsWith(".png") && !asset.equals("lizardIcon.png") && !asset.equals("spockIcon.png")) {
                    // Add the image name to the imageNames list
                    imageNames.add(asset);
                }
            }
        } catch (Exception e) {
            Log.i(">>>", Objects.requireNonNull(e.getMessage()));
        }
    }
}