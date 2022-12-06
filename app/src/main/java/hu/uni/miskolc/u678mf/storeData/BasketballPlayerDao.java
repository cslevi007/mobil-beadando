package hu.uni.miskolc.u678mf.storeData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hu.uni.miskolc.u678mf.storeData.model.BasketballPlayer;

@Dao
public interface BasketballPlayerDao {
    @Query("SELECT * FROM BasketballPlayer")
    List<BasketballPlayer> getAll();

    @Query("SELECT * FROM BasketballPlayer WHERE jerseyNumber >:number")
    List<BasketballPlayer> getPlayerWithJerseyNumberGreaterThan(int number);

    @Query("SELECT * FROM BasketballPlayer WHERE firstName =:firstName AND lastName =:lastName AND jerseyNumber =:jerseyNumber AND team =:team")
    List<BasketballPlayer> findSame(String firstName, String lastName, int jerseyNumber, String team);

    default List<BasketballPlayer> findSame(BasketballPlayer basketballPlayer) {
        return findSame(basketballPlayer.getFirstName(), basketballPlayer.getLastName(), basketballPlayer.getJerseyNumber(), basketballPlayer.getTeam());
    }

    @Insert
    void insertAll(BasketballPlayer... basketballPlayers);

    @Insert
    void insert(BasketballPlayer basketballPlayer);

    @Delete
    void delete(BasketballPlayer basketballPlayer);

    @Update
    void update(BasketballPlayer basketballPlayer);
}
