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


上面`layout`文件中使用变量的方式转换成在java文件中的方式为，
`com.example.User user = new com.example.User()`
这种方式我们一般比较少用，除非类名冲突时，我们一般使用的是另外一种方式
``` import com.example.User
    User user = new User() ```

所以在`data`标签内，你也可以这样写
``` <import type="com.example.User"/>
   <variable name="user" type="User"/> ```


如果类名冲突，`import`标签有一个子标签叫做`alias`
eg:
``` <import type="android.view.View"/>
    <import type="com.example.real.estate.View"
            alias="Vista"/> ```
现在, "Vista" 将会作为`com.example.real.estate.View`的引用
"View" 将会作为 `android.view.View`的引用

### Data Object
对于上面提到的`User`类来说，可以使用一个
plain-old Java object (POJO) or JavaBeans 对象来表示它

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

### Binding
默认情况下，绑定类的类名依赖`layout`文件，将`layout`文件名转化成驼峰命名，然后接一个"Binding"后缀
eg: `activity_main.xml` 将会产生一个名为 `ActivityMainBinding` 的类

上面的`layout` 文件名为 `main_activity.xml` 所以产生的类为 `MainActivityBinding`.
这个类将会从layout属性中获取所有的绑定变量，layout将会知道在视图中如何给绑定变量赋值
创建一个Binding最简单的方式就是初始化inflating layout的时候
``` @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
       User user = new User("Test", "User");
       binding.setUser(user);
    } ```

如果想绑定事件(eg: View.OnClickListener),






