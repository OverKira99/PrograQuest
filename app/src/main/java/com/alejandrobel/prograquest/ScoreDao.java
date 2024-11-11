package com.alejandrobel.prograquest;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ScoreDao {

    @Insert
    void insert(Score score);

    @Query("SELECT * FROM scores ORDER BY score DESC")
    List<Score> getAllScores();
}