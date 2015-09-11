# MVVMDemo
## This is a Demo About Google Data Binding Guide
## The URL is https://developer.android.com/intl/zh-cn/tools/data-binding/guide.html

---

### 初始化依赖

在 Top-level build 文件中加入以下代码

    dependencies {
           classpath "com.android.tools.build:gradle:1.3.1"
           classpath "com.android.databinding:dataBinder:1.0-rc1"
       }

在 project build 文件中加入以下代码

    apply plugin: 'com.android.databinding'

**如果你gradle sync时遇到了错误，首先尝试升级你的support library**

---

###
Data-binding layout 文件和之前的layout 文件有轻微的不同, 以layout标签作为根元素，
然后其中放置了data标签和之前的view标签。这个view标签就是你之前不使用Data-binding时的根标签。
下面是一个简单的例子
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
这个在`data`标签内被定义的`user`变量可以在这个`layout`中使用

```<variable name="user" type="com.example.User"/>```

在这个`layout`中使用表达式的语法为："@{}"，
在下面的代码中，`TextView`的文字将会被设置为user.firstName

```<TextView android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:text="@{user.firstName}"/>```

####



