# MVVMDemo
## This is a Demo About Google Data Binding Guide
## The URL is https://developer.android.com/intl/zh-cn/tools/data-binding/guide.html

---

### Build Environment

add below code to Top-level build file

    dependencies {
           classpath "com.android.tools.build:gradle:1.3.1"
           classpath "com.android.databinding:dataBinder:1.0-rc1"
       }

add below code to project build file

    apply plugin: 'com.android.databinding'

**if you meet error, update you support library first**

---

### Data Binding Layout Files
Data-binding layout files are slightly different and start with a root tag of layout
followed by a data element and a view root element.
This view element is what your root would be in a non-binding layout file.
A sample file looks like this:
```<?xml version="1.0" encoding="utf-8"?>
   <layout xmlns:android="http://schemas.android.com/apk/res/android">
      <data>
          <variable name="user" type="com.example.User"/>
      </data>
      <LinearLayout
          android:orientation="vertical"
          android:layout_width="match_parent"
          android:layout_height="match_parent">
          <TextView android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:text="@{user.firstName}"/>
          <TextView android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:text="@{user.lastName}"/>
      </LinearLayout>
   </layout>
```
The `user` variable within `data` describes a property that may be used within this `layout`.

```<variable name="user" type="com.example.User"/>```

Expressions within the layout are written in the attribute properties using the “@{}” syntax.
Here, the TextView’s text is set to the firstName property of user:

```<TextView android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:text="@{user.firstName}"/>```


the way to using variable in 'layout' converting it in 'java' is
`com.example.User user = new com.example.User()`
we don't use this way often, except the class name conflicts,
In general, we will use this way:
``` import com.example.User
    User user = new User() ```

so in `data`element，you can do the same thing by this
``` <import type="com.example.User"/>
   <variable name="user" type="User"/> ```

If you meet class name conflicts, `import` element has a child element named `alias`
eg:
``` <import type="android.view.View"/>
    <import type="com.example.real.estate.View"
            alias="Vista"/> ```
Now, "Vista" may be used to reference the `com.example.real.estate.View`
and "View" may be used to reference `android.view.View`

### Data Object

you can use a plain-old Java object (POJO) or JavaBeans object for User

POJO
``` public class User {
       public final String firstName;
       public final String lastName;
       public User(String firstName, String lastName) {
           this.firstName = firstName;
           this.lastName = lastName;
       }
    } ```

JavaBeans
``` public class User {
       private final String firstName;
       private final String lastName;
       public User(String firstName, String lastName) {
           this.firstName = firstName;
           this.lastName = lastName;
       }
       public String getFirstName() {
           return this.firstName;
       }
       public String getLastName() {
           return this.lastName;
       }
    } ```

### Binding Data

By default, a Binding class will be generated based on the name of the layout file,
converting it to Pascal case and suffixing “Binding” to it.
eg: `activity_main.xml` will be generated a class Named `ActivityMainBinding`

The above layout file was `main_activity.xml` so the generate class was `MainActivityBinding`.
This class holds all the bindings from the layout properties (e.g. the user variable)
to the layout’s Views and knows how to assign values for the binding expressions.
The easiest means for creating the bindings is to do it while inflating:
``` @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
       User user = new User("Test", "User");
       binding.setUser(user);
    } ```