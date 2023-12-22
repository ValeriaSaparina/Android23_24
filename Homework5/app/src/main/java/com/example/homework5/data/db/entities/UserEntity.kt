package com.example.homework5.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.homework5.data.db.typeConverters.DateConverter
import java.util.Date

@TypeConverters(DateConverter::class)
@Entity(tableName = "user", indices = [Index(value = ["phone"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id") var userId: Long,
    var phone: String,
    var email: String,
    var name: String,
    var password: String,
    @ColumnInfo(name = "deletion_time") var deletionTime: Date? = null
)
