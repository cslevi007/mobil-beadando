package hu.uni.miskolc.u678mf.storeData;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import hu.uni.miskolc.u678mf.storeData.model.BasketballPlayer;

@Database(entities = { BasketballPlayer.class }, version = 1)
public abstract class BasketballPlayerDatabase extends RoomDatabase {
    public abstract BasketballPlayerDao basketballPlayerDao();
}
