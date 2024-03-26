package comp208.withers.rpsslgame;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * This class is an entity for the score of the user. It contains the operational logic for the score of the user.
 */
@Entity(tableName = "score_table", indices = {@Index(value = {"name"}, unique = true)})
public class Score {
    // The id of the user
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    // The name of the user
    @ColumnInfo(name = "name")
    private final String name;
    // The number of normal wins and losses
    @ColumnInfo(name = "numberOfNormalWins")
    private Integer numberOfNormalWins;
    @ColumnInfo(name = "numberOfNormalLosses")
    private Integer numberOfNormalLosses;
    // The number of extended wins and losses
    @ColumnInfo(name = "numberOfExtendedWins")
    private Integer numberOfExtendedWins;
    @ColumnInfo(name = "numberOfExtendedLosses")
    private Integer numberOfExtendedLosses;
    /**
     * This is the constructor for the score.
     * It sets the name of the user and the number of wins and losses for the user for both the normal and extended games.
     * @param name The name of the user
     * @param numberOfNormalWins The number of normal wins
     * @param numberOfNormalLosses The number of normal losses
     * @param numberOfExtendedWins The number of extended wins
     * @param numberOfExtendedLosses The number of extended losses
     */
    public Score(String name, Integer numberOfNormalWins, Integer numberOfNormalLosses, Integer numberOfExtendedWins, Integer numberOfExtendedLosses) {
        this.name = name;
        this.numberOfNormalWins = numberOfNormalWins;
        this.numberOfNormalLosses = numberOfNormalLosses;
        this.numberOfExtendedWins = numberOfExtendedWins;
        this.numberOfExtendedLosses = numberOfExtendedLosses;
    }
    /**
     * This method gets the name of the user.
     * @return The name of the user
     */
    public String getName() { return name; }
    /**
     * This method gets the number of wins based on the game type.
     * @param gameType The type of game
     * @return The number of wins
     */
    public Integer getWins(Boolean gameType) { return gameType ? numberOfNormalWins : numberOfExtendedWins; }
    /**
     * This method sets the number of wins based on the game type.
     * @param gameType The type of game
     * @param numberWins The number of wins
     */
    public void setWins(Boolean gameType, Integer numberWins) {
        // Set the number of wins based on the game type
        if (gameType) {
            // Set the number of normal wins if the game type is true
            this.numberOfNormalWins = numberWins;
        } else {
            // Set the number of extended wins if the game type is false
            this.numberOfExtendedWins = numberWins;
        }
    }
    /**
     * This method gets the number of losses based on the game type.
     * @param gameType The type of game
     * @return The number of losses
     */
    public Integer getLosses(Boolean gameType) { return gameType ? numberOfNormalLosses : numberOfExtendedLosses; }
    /**
     * This method sets the number of losses based on the game type.
     * @param gameType The type of game
     * @param numberLosses The number of losses
     */
    public void setLosses(Boolean gameType, Integer numberLosses) {
        // Set the number of losses based on the game type
        if (gameType){
            // Set the number of normal losses if the game type is true
            this.numberOfNormalLosses = numberLosses;
        } else {
            // Set the number of extended losses if the game type is false
            this.numberOfExtendedLosses = numberLosses;
        }
    }
    /**
     * This method gets the id of the user.
     * @return The id of the user
     */
    public Integer getId() {
        return id;
    }
    /**
     * This method sets the id of the user.
     * @param id The id of the user
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * This method gets the number of normal wins.
     * @return The number of normal wins
     */
    public Integer getNumberOfNormalWins() {
        return numberOfNormalWins;
    }
    /**
     * This method sets the number of normal wins.
     * @param numberOfNormalWins The number of normal wins
     */
    public void setNumberOfNormalWins(Integer numberOfNormalWins) {
        this.numberOfNormalWins = numberOfNormalWins;
    }
    /**
     * This method gets the number of normal losses.
     * @return The number of normal losses
     */
    public Integer getNumberOfNormalLosses() {
        return numberOfNormalLosses;
    }
    /**
     * This method sets the number of normal losses.
     * @param numberOfNormalLosses The number of normal losses
     */
    public void setNumberOfNormalLosses(Integer numberOfNormalLosses) {
        this.numberOfNormalLosses = numberOfNormalLosses;
    }
    /**
     * This method gets the number of extended wins.
     * @return The number of extended wins
     */
    public Integer getNumberOfExtendedWins() {
        return numberOfExtendedWins;
    }
    /**
     * This method sets the number of extended wins.
     * @param numberOfExtendedWins The number of extended wins
     */
    public void setNumberOfExtendedWins(Integer numberOfExtendedWins) {
        this.numberOfExtendedWins = numberOfExtendedWins;
    }
    /**
     * This method gets the number of extended losses.
     * @return The number of extended losses
     */
    public Integer getNumberOfExtendedLosses() {
        return numberOfExtendedLosses;
    }
    /**
     * This method sets the number of extended losses.
     * @param numberOfExtendedLosses The number of extended losses
     */
    public void setNumberOfExtendedLosses(Integer numberOfExtendedLosses) {
        this.numberOfExtendedLosses = numberOfExtendedLosses;
    }
    /**
     * This method gets the string representation of the score.
     * @return The string representation of the score
     */
    @NonNull
    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberOfNormalWins=" + numberOfNormalWins +
                ", numberOfNormalLosses=" + numberOfNormalLosses +
                ", numberOfExtendedWins=" + numberOfExtendedWins +
                ", numberOfExtendedLosses=" + numberOfExtendedLosses +
                '}';
    }
}
