package com.example.animeplex.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.animeplex.data.Aired

@TypeConverters
class AnimeTypeConvertor {

    @TypeConverter
    fun fromAnyToString(attribute: Any?): String{
        return if (attribute == null){
            ""
        }else {
            attribute as String
        }
    }

    @TypeConverter
    fun fromStringToAny(attribute: String?): Any{
        return attribute ?: ""
    }

    @TypeConverter
    fun fromAiredToString(attribute: Aired?): String {
        return if (attribute == null){
            ""
        }else {
            attribute as String
        }
    }

//    @TypeConverter
//    fun fromStringToAired(attribute: String?): Aired {
//        return if (attribute == null){
//            ""
//        }else {
//            attribute
//        }
//    }

}