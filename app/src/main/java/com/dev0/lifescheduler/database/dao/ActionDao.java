package com.dev0.lifescheduler.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dev0.lifescheduler.database.entity.ActionEntity;

import java.util.List;

@Dao
public interface ActionDao {
    @Query("SELECT * FROM  ActionEntity")
    List<ActionEntity> getAll();

    @Update
    void update(ActionEntity actionEntity);

    @Insert
    void insert(ActionEntity actionEntity);

    @Delete
    void delete(ActionEntity actionEntity);
}
