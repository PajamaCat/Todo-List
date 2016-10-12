package com.example.jiafang_jiang.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TodoListItem implements Parcelable{

    public long id;
    public String text;

    public static final Parcelable.Creator<TodoListItem> CREATOR = new Parcelable.Creator<TodoListItem>() {

        public TodoListItem[] newArray(int size) {
            return new TodoListItem[size];
        }

        public TodoListItem createFromParcel(Parcel source) {
            TodoListItem object = new TodoListItem();
            object.readFromParcel(source);
            return object;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(text);
    }

    public void readFromParcel(Parcel source) {
        id = source.readLong();
        text = source.readString();
    }
}
