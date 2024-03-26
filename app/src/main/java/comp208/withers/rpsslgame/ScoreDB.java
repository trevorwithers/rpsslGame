package comp208.withers.rpsslgame;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * This class is the database for the score of the user. It contains the operational logic for the database for the score of the user.
 */
@Database(entities = {Score.class}, version = 2, exportSchema = false)
public abstract class ScoreDB extends RoomDatabase {
    // The name of the database
    public static final String DB_NAME = "score_db";
    // The instance of the database
    private static ScoreDB INSTANCE = null;
    /**
     * This method gets the instance of the database.
     * @param context The context of the app
     * @return The instance of the database
     */
    public static ScoreDB getInstance(Context context) {
        // If the instance is null, create a new instance of the database
        if (INSTANCE == null) {
            // Create a new instance of the database using the context
            // Allow main thread queries and fallback to destructive migration
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScoreDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        // Return the instance of the database
        return INSTANCE;
    }
    // The data access object for the score
    public abstract ScoreDao scoreDao();
}
