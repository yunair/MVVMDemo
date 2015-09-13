# MVVMDemo
## This is a Demo About Google Data Binding Guide
## The URL is https://developer.android.com/intl/zh-cn/tools/data-binding/guide.html
## English Version see [there][1]
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

既然引入了包，当然可以像Java一样使用包内的静态方法了，
例如 : `import`了`View`类, 你可以像下面以下使用View中的static方法
``` <TextView
       android:text="@{user.lastName}"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:visibility="@{View.VISIBLE}"/> ```

像在java中一样, `java.lang.*` 会默认被import.

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

### Binding Data
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

### Custom Binding Class Names
默认情况下，通过`layout`文件名生成的类将会被放置在当前模块的`databinging`包下，

例如，`layout`文件名为 `contact_item.xm`l 将会产生名为`ContactItemBinding`的类。
如果模块的包名为 `com.example.my.app`,
那么这个类将会被放置在 `com.example.my.app.databinding`包中.

如果你想自定义自动生成的类所在的名字和位置，可以通过在`data`标签内添加`class`属性来实现

eg:
``` <data class="ContactItem">
        ...
    </data> ```

上面这种情况将会在当前模块的`databinding`包内生成`ContactItem`类
如果想要在当前模块的包内生成`ContactItem`类,只需要加一个“.”前缀：

``` <data class=".ContactItem">
        ...
    </data> ```

如果想指定在哪个包内生成绑定的类，使用包的全名+类名的形式
``` <data class="com.example.ContactItem">
        ...
    </data> ```

你可以在`CustomBindingClassNameActivity.java`中看到其中的区别


### Binding Event
如果想绑定事件(eg: View.OnClickListener),
和绑定数据类似，定义一个Handler类来处理事件，然后将对应的事件在`@{}`中声明，
同时记得在java文件中set那个Handler，你可以在`BaseActivity.java`中看到具体的用法


[1] : README_en.md




