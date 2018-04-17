package com.example.test.databasemanagerlibrary.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lijie on 2017/4/14.
 */

public class DatabaseUtil {
    /**
     * 获取所有表
     * @param databaseName
     * @param db
     * @return
     */
    public static List<String> getTableList(SQLiteDatabase db, String databaseName){
//        SQLiteDatabase db = context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("select name from sqlite_master where type='table' order by name", null);
        List<String> tableList = new ArrayList<>();
        try{
            while(cursor.moveToNext()){
                //遍历出表名
                String name = cursor.getString(0);
                tableList.add(name);
            }
        }
        finally {
            cursor.close();
        }
        return tableList;
    }

    /**
     * 获取表字段
     * @param db
     * @param tableName
     * @return
     */
    public static String[] getColumns(SQLiteDatabase db, String tableName){
        Cursor cursor = db.rawQuery("select * from " + tableName + " limit 0", null);
        String[] columns = null;
        try {
            columns = cursor.getColumnNames();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }

        return columns;
    }

    /**
     * 分页查询
     * @param db
     * @param tableName
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static List<Map<String, String>> getPage(SQLiteDatabase db, String tableName, int pageIndex, int pageSize){
        Cursor cursor = db.rawQuery("select * from " + tableName + " limit " + (pageIndex * pageSize) + "," + pageSize, null);
        List<Map<String, String>> page = new ArrayList<>();

        try {
            while (cursor.moveToNext()) {
                String[] columns = cursor.getColumnNames();
                Map<String, String> map = new HashMap<>();
                for (String column : columns){
                    int columnIndex = cursor.getColumnIndex(column);
                    switch (cursor.getType(columnIndex)){
                        case Cursor.FIELD_TYPE_INTEGER:
                            map.put(column, String.valueOf(cursor.getInt(columnIndex)));
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            map.put(column, String.valueOf(cursor.getFloat(columnIndex)));
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            map.put(column, cursor.getString(columnIndex));
                            break;
                        case Cursor.FIELD_TYPE_BLOB:
                            map.put(column, String.valueOf(cursor.getBlob(columnIndex)));
                            break;
                    }
                }
                page.add(map);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }

        return page;
    }

    /**
     * 更新行
     * @param db
     * @param tableName
     * @param rowMap
     */
    public static void updateRow(SQLiteDatabase db, String tableName, Map<String, String> rowMap){
        ContentValues contentValues = new ContentValues();
        Iterator<Map.Entry<String, String>> iterator = rowMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            contentValues.put(entry.getKey(), entry.getValue());
        }
        db.update(tableName, contentValues, "rowid=?", new String[]{rowMap.get("rowid")});
    }

    /**
     * 删除行
     * @param db
     * @param tableName
     * @param rowMap
     */
    public static void deleteRow(SQLiteDatabase db, String tableName, Map<String, String> rowMap){
        db.delete(tableName, "rowid=?", new String[]{rowMap.get("rowid")});
    }
}
