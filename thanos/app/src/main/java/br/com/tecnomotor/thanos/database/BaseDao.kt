package br.com.tecnomotor.thanos.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * Contém métodos comuns aos outros "Dao's"
 */
interface BaseDao<T> {
    @Insert
    fun add(vararg obj: T)

    @Update
    fun update(vararg obj: T)

    @Delete
    fun delete(vararg obj: T)
}