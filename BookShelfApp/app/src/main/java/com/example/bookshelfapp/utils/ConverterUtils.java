package com.example.bookshelfapp.utils;

import androidx.room.TypeConverter;

import com.example.bookshelfapp.data.model.book.VolumeInfo;
import com.example.bookshelfapp.data.model.book.VolumeInfoDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ConverterUtils {
    // Chuyển List<String> thành String (dạng JSON)
    @TypeConverter
    public static String fromList(List<String> list) {
        if (list == null) {
            return null;
        }
        // Chuyển đổi List thành JSON String
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    // Chuyển String (JSON) thành List<String>
    @TypeConverter
    public static List<String> toList(String data) {
        if (data == null) {
            return null;
        }
        // Chuyển đổi JSON String thành List
        Gson gson = new Gson();
        return gson.fromJson(data, new TypeToken<List<String>>(){}.getType());
    }
    // Chuyển List<Object> thành String (dạng JSON)
    @TypeConverter
    public static String fromListObject(List<Object> list) {
        if (list == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    // Chuyển String (JSON) thành List<Object>
    @TypeConverter
    public static List<Object> toListObject(String data) {
        if (data == null) {
            return null;
        }
        Gson gson = new Gson();
        // Lưu ý: Bạn cần phải cung cấp đúng type của Object trong TypeToken
        return gson.fromJson(data, new TypeToken<List<Object>>(){}.getType());
    }
    @TypeConverter
    public static String fromVolumeInfo(VolumeInfo volumeInfo) {
        if (volumeInfo == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(volumeInfo);
    }

    // Chuyển từ String (JSON) thành Object
    @TypeConverter
    public static VolumeInfo toVolumeInfo(String volumeInfoJson) {
        if (volumeInfoJson == null) {
            return null;
        }
        Gson gson = new Gson();

        return gson.fromJson(volumeInfoJson, VolumeInfoDetail.class);
    }
}
