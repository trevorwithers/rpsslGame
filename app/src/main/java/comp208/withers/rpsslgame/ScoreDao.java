package comp208.withers.rpsslgame;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * This class is a data access object for the score. It contains the abstract methods for adding a new score, selecting all scores, and for clearing all scores from the database.
 */
@Dao
public interface ScoreDao {
    /**
     * This method selects all scores from the database.
     * @return A list of all scores
     */
    @Query("SELECT * FROM score_table")
    List<Score> findAllData();
    /**
     * This method deletes all scores from the database.
     */
    @Query("DELETE FROM score_table")
    void deleteAll();
    /**
     * This method deletes a score from the database based on the id.
     * @param id The id of the score
     */
    @Query("DELETE FROM score_table WHERE id = :id")
    void deleteById(Integer id);
    /**
     * This method selects a score from the database based on the id.
     * @param id The id of the score
     * @return The score
     */
    @Query("SELECT * FROM score_table WHERE id = :id")
    Score findDataById(Integer id);
    /**
     * This method selects a score from the database based on the name.
     * @param name The name of the score
     * @return The score
     */
    @Query("SELECT * FROM score_table WHERE name = :name")
    Score findDataByName(String name);
    /**
     * This method inserts a new score into the database.
     * @param score The score to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(Score score);

    /**
     * This method gets the number of normal wins for a user based on the id.
     * @param id The id of the user
     * @return The number of normal wins
     */
    @Query("Select numberOfNormalWins FROM score_table WHERE id = :id")
    Integer getNormalWins(Integer id);
    /**
     * This method gets the number of normal losses for a user based on the id.
     * @param id The id of the user
     * @return The number of normal losses
     */
    @Query("Select numberOfNormalLosses FROM score_table WHERE id = :id")
    Integer getNormalLosses(Integer id);
    /**
     * This method gets the id of a user based on the name.
     * @param name The name of the user
     * @return The id of the user
     */
    @Query("Select id FROM score_table WHERE name = :name")
    Integer getId(String name);
}
