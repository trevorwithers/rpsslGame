package comp208.withers.rpsslgame;

import static android.content.Context.MODE_PRIVATE;

import static comp208.withers.rpsslgame.MainActivity.isLoggedIn;
import static comp208.withers.rpsslgame.MainActivity.userList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;

import java.util.Objects;

/**
 * This class is the login popup for the app. It handles the login functionality for the app.
 */
public class LoginPopup extends DialogFragment {
    // The string for the user
    String user = "";
    // The game type
    String gameType;
    /**
     * This is the constructor for the login popup.
     * @param gameType The type of game the user is trying to play
     */
    public LoginPopup(String gameType) {
        this.gameType = gameType;
    }
    /**
     * This method is called when the login popup is created. It sets the content view and sets up the click listener for the login button.
     * It also sets the text watcher for the username edit text.
     * @param inflater The layout inflater
     * @param container The view group container
     * @param savedInstanceState The saved instance state
     * @return The view for the login popup
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_popup, container, false);
        // Get the login button and the username edit text
        Button btnLogin = view.findViewById(R.id.btnLogin);
        EditText editTextUsername = view.findViewById(R.id.editTextUsername);
        // Set the text watcher for the username edit text
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            /**
             * This method is called when the text is changed. It sets the login button to enabled if the username is not empty.
             * @param s The editable text
             */
            @Override
            public void afterTextChanged(Editable s) { btnLogin.setEnabled(!s.toString().isEmpty()); }
        });
        // Set the click listener for the login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is called when the login button is clicked. It checks if the user is in the shared preferences file and logs them in if they are.
             * @param v The view
             */
            @Override
            public void onClick(View v) {
                // Get the shared preferences file and the editor for the file
                SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("MainActivity", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                // Get the user from the shared preferences file
                user = preferences.getString(editTextUsername.getText().toString(), "Game");
                // If the user is not in the shared preferences file, add them to the file
                if (user.equals("Game")) {
                    // Add the user to the userList and the shared preferences file
                    userList.add(new Score(editTextUsername.getText().toString(), 0, 0, 0, 0));
                    ScoreDao scoreDao = ScoreDB.getInstance(getActivity()).scoreDao();
                    userList.get(userList.size() - 1).setId(scoreDao.getId(editTextUsername.getText().toString()));
                    // Convert the user to a json string and add it to the shared preferences file
                    Gson gson = new Gson();
                    final String json = gson.toJson(userList.get(userList.size() - 1));
                    editor.putString(editTextUsername.getText().toString(), json);
                    editor.commit();
                } else {
                    // If the user is in the shared preferences file, get the user from the file and add them to the userList
                    Gson gson = new Gson();
                    Score score = gson.fromJson(user, Score.class);
                    userList.add(score);
                }
                // Loop through the userList and check if the user is in the list
                for (int i = 0; i < userList.size(); i++) {
                    // If the user is in the list, dismiss the login popup and start the game activity
                    if (userList.get(i).getName().equals(editTextUsername.getText().toString())) {
                        dismiss();
                        isLoggedIn = true;
                        Intent intent = gameType.equals("normal") ? new Intent(getActivity(), NormalGame.class) : new Intent(getActivity(), ExtendedGame.class);
                        intent.putExtra("index", i);
                        startActivity(intent);
                    }
                }
            }
        });
        return view;
    }
}
