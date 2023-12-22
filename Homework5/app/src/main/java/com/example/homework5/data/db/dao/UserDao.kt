package com.example.homework5.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework5.data.db.entities.UserEntity
import java.util.Date

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addUser(userData: UserEntity)

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<UserEntity>?

    @Query("UPDATE user SET name = :name, phone = :phone, email = :emailAddress WHERE user_id = :id")
    fun updateUserQuery(id: Long, name: String, phone: String, emailAddress: String)

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM user WHERE user_id = :userId")
    fun getUserById(userId: Long): UserEntity?

    @Query("UPDATE user SET phone = :phone, password= :password WHERE user_id = :userId")
    fun updateUserPhonePasswordQuery(phone: String, password: String, userId: Long)

    @Query("UPDATE user SET phone = :phone WHERE user_id = :userId")
    fun updateUserPhoneQuery(phone: String, userId: Long)

    @Query("UPDATE user SET password = :password WHERE user_id = :userId")
    fun updateUserPasswordQuery(password: String, userId: Long)

    @Delete()
    fun deleteUser(user: UserEntity)

    @Query("UPDATE user SET deletion_time = :date WHERE user_id = :userId")
    fun updateUserDeletionTime(date: Date, userId: Long)

    @Query("UPDATE user SET deletion_time = NULL WHERE user_id = :userId")
    fun updateUserDeletionTime(userId: Long)

    @Query("SELECT * FROM user WHERE deletion_time = date('now', '-7 day')")
    fun getUsersForDelete(): List<UserEntity>?

}