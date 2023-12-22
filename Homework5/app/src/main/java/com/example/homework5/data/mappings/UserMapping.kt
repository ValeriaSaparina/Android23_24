package com.example.homework5.data.mappings

import com.example.homework5.data.db.entities.UserEntity
import com.example.homework5.data.models.UserModel

object UserMapping {
    fun toEntity(userModel: UserModel): UserEntity {
        return UserEntity(
            userId = userModel.userId,
            phone = userModel.phone,
            email = userModel.email,
            name = userModel.name,
            password = userModel.password,
        )
    }

    fun toModel(userEntity: UserEntity): UserModel {
        return UserModel(
            userId = userEntity.userId,
            phone = userEntity.phone,
            email = userEntity.email,
            name = userEntity.name,
            password = userEntity.password,
        )
    }
}