package com.air.mvvmdemo.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.air.mvvmdemo.BR;

/**
 * Created by Air on 15/9/16.
 */
public class ObservableModel {
    public final ObservableField<String> firstName =
            new ObservableField<>();
    public final ObservableField<String> lastName =
            new ObservableField<>();
    public final ObservableInt age = new ObservableInt();



    public static void main(String[] args) {
        ObservableModel user1 = new ObservableModel();
        user1.firstName.set("Google");
        int age = user1.age.get();

        ObservableArrayMap<String, Object> user2 = new ObservableArrayMap<>();
        user2.put("firstName", "Google");
        user2.put("lastName", "Inc.");
        user2.put("age", 17);


        ObservableArrayList<Object> user3 = new ObservableArrayList<>();
        user3.add("Google");
        user3.add("Inc.");
        user3.add(17);

    }

    private static class User1 extends BaseObservable {
        private String firstName;
        private String lastName;
        @Bindable
        public String getFirstName() {
            return this.firstName;
        }
        @Bindable
        public String getLastName() {
            return this.lastName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
            notifyPropertyChanged(BR.firstName);
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
            notifyPropertyChanged(BR.lastName);
        }
    }

    private static class User2 {
        public final ObservableField<String> firstName =
                new ObservableField<>();
        public final ObservableField<String> lastName =
                new ObservableField<>();
        public final ObservableInt age = new ObservableInt();
    }


}
