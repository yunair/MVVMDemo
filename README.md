# MVVMDemo
## This is a Demo About Google Data Binding Guide
## The URL is https://developer.android.com/intl/zh-cn/tools/data-binding/guide.html
## English Version see [there][1]
---

### 初始化依赖

在 Top-level build 文件中加入以下代码
```gradle
dependencies {
       classpath "com.android.tools.build:gradle:1.3.1"
       classpath "com.android.databinding:dataBinder:1.0-rc1"
   }
```
在 project build 文件中加入以下代码
```gradle
apply plugin: 'com.android.databinding'
```
**如果你gradle sync时遇到了错误，首先尝试升级你的support library**

---

###

Data-binding layout 文件和之前的layout 文件有轻微的不同, 以layout标签作为根元素，
然后其中放置了data标签和之前的view标签。这个view标签就是你之前不使用Data-binding时的根标签。
下面是一个简单的例子
```xml
<?xml version="1.0" encoding="utf-8"?>
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
```xml
<variable name="user" type="com.example.User"/>
```
在这个`layout`中使用表达式的语法为："@{}"，
在下面的代码中，`TextView`的文字将会被设置为user.firstName
```xml
<TextView android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:text="@{user.firstName}"/>
```
上面`layout`文件中使用变量的方式转换成在java文件中的方式为，
```java
com.example.User user = new com.example.User();
```
这种方式我们一般比较少用，除非类名冲突时，我们一般使用的是另外一种方式
```java
import com.example.User;
User user = new User();
```
所以在`data`标签内，你也可以这样写
```xml
<import type="com.example.User"/>
<variable name="user" type="User"/>
```
如果类名冲突，`import`标签有一个子标签叫做`alias`

eg:
```xml
<import type="android.view.View"/>
<import type="com.example.real.estate.View"
        alias="Vista"/>
```
现在, "Vista" 将会作为`com.example.real.estate.View`的引用
"View" 将会作为 `android.view.View`的引用

既然引入了包，当然可以像Java一样使用包内的静态方法了，
例如 : `import`了`View`类, 你可以像下面以下使用View中的static方法
```xml
<TextView
   android:text="@{user.lastName}"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:visibility="@{View.VISIBLE}"/>
```
像在java中一样, `java.lang.*` 会默认被import.

### Data Object

对于上面提到的`User`类来说，可以使用一个
plain-old Java object (POJO) or JavaBeans 对象来表示它

POJO:
```java
public class User {
   public final String firstName;
   public final String lastName;
   public User(String firstName, String lastName) {
       this.firstName = firstName;
       this.lastName = lastName;
   }
}
```
JavaBeans:
```java
public class User {
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
}
```

### Binding Data

默认情况下，绑定类的类名依赖`layout`文件，将`layout`文件名转化成驼峰命名，然后接一个"Binding"后缀

eg: `activity_main.xml` 将会产生一个名为 `ActivityMainBinding` 的类

上面的`layout` 文件名为 `main_activity.xml` 所以产生的类为 `MainActivityBinding`.
这个类将会从layout属性中获取所有的绑定变量，layout将会知道在视图中如何给绑定变量赋值
创建一个Binding最简单的方式就是初始化inflating layout的时候
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
   User user = new User("Test", "User");
   binding.setUser(user);
}
```

### Custom Binding Class Names

默认情况下，通过`layout`文件名生成的类将会被放置在当前模块的`databinging`包下，

例如，`layout`文件名为 `contact_item.xml` 将会产生名为`ContactItemBinding`的类。
如果模块的包名为 `com.example.my.app`,那么这个类将会被放置在 `com.example.my.app.databinding`包中.

如果你想自定义自动生成的类所在的名字和位置，可以通过在`data`标签内添加`class`属性来实现

eg:
```xml
<data class="ContactItem">
    ...
</data>
```
上面这种情况将会在当前模块的`databinding`包内生成`ContactItem`类
如果想要在当前模块的包内生成`ContactItem`类,只需要加一个“.”前缀：
```xml
<data class=".ContactItem">
    ...
</data>
```
如果想指定在哪个包内生成绑定的类，使用包的全名+类名的形式
```xml
<data class="com.example.ContactItem">
    ...
</data>
```
你可以在`CustomBindingClassNameActivity.java`中看到其中的区别

### Binding Event

如果想绑定事件(eg: View.OnClickListener),
和绑定数据类似，定义一个Handler类来处理事件，然后将对应的事件在`@{}`中声明，
同时记得在java文件中set那个Handler，你可以在`BaseActivity.java`中看到具体的用法

### Includes

使用最外部bind属性和`data`标签内的变量，可以将需要的变量从当前`layout`传入被include的`layout`
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:bind="http://schemas.android.com/apk/res-auto">
   <data>
       <variable name="user" type="com.example.User"/>
   </data>
   <LinearLayout
       android:orientation="vertical"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <include layout="@layout/name"
           bind:user="@{user}"/>
       <include layout="@layout/contact"
           bind:user="@{user}"/>
   </LinearLayout>
</layout>
```

在你的`name.xml`和`contact.xml`的`data`标签内，必须也拥有`user`变量，该Demo参见`IncludeActivity.java`

### Expression Language

#### Common Features

DataBinding的表达式就像Java的表达式类似，下面这些是相同的:

- Mathematical + - / * %
- String concatenation +
- Logical && ||
- Binary & | ^
- Unary + - ! ~
- Shift >> >>> <<
- Comparison == > < >= <=
- instanceof
- Grouping ()
- Literals - character, String, numeric, null
- Cast
- Method calls
- Field access
- Array access []
- Ternary operator ?:

#### Missing Operations

下面的表达式从Java中去掉了

- this
- super
- new
- Explicit generic invocation

#### Null Coalescing Operator

这就是简化的null判断表达式，使用(`??`)来表示，如果左边的值为null，则使用右边的值。
下面两个表达式是相同的
```xml
android:text="@{user.displayName ?? user.lastName}"
android:text="@{user.displayName != null ? user.displayName : user.lastName}"
```

#### Collections

通用的集合: arrays, lists, sparse lists, and maps 可以通过[]操作符来使用
```xml
<data>
    <import type="android.util.SparseArray"/>
    <import type="java.util.Map"/>
    <import type="java.util.List"/>
    <variable name="list" type="List&lt;String>"/>
    <variable name="sparse" type="SparseArray&lt;String>"/>
    <variable name="map" type="Map&lt;String, String>"/>
    <variable name="index" type="int"/>
    <variable name="key" type="String"/>
</data>
…
android:text="@{list[index]}"
…
android:text="@{sparse[index]}"
…
android:text="@{map[key]}"
```
可以看到我使用了`&lt;`来代替`<`，因为在xml里面，不允许在`""`内使用`<`,所以使用了xml的特殊字符来替代，
下面也有类似的情况

#### String Literals

你有三种方式来使用String
```xml
android:text='@{map["firstName"]}'
android:text="@{map[`firstName`]}"
android:text="@{map[&quot;firstName&quot;]}"
```
- 使用单引号`'`作为最外层包裹
- 使用双引号`"`作为最外层包裹，但是内部使用反引号```
- 使用双引号`"`作为最外层包裹，但是内部使用xml的特殊字符`&quot;`来代替需要的双引号

可以在`QuoteActivity.java`类看具体的用法

#### Resources

当然，在`@{}`使用常规的语法访问资源文件是没问题的
但是，如果是strings和plurals类型，在xml里定义了格式化参数，就需要将参数传入其中
```xml
android:padding="@{large? @dimen/largePadding : @dimen/smallPadding}"
android:text="@{@string/nameFormat(firstName, lastName)}"
android:text="@{@plurals/banana(bananaCount)}"
```

一些元素需要明确的类型

|Type	            |Normal Reference  |Expression Reference
| :---:             | :---:            | :---:
|String[]	        |@array	           |@stringArray
|int[]	            |@array	           |@intArray
|TypedArray	        |@array	           |@typedArray
|Animator	        |@animator	       |@animator
|StateListAnimator	|@animator	       |@stateListAnimator
|color int	        |@color	           |@color
|ColorStateList	    |@color	           |@colorStateList


### Data Objects

一个POJO可以被用在数据绑定中，但是，修改POJO的时候UI不会更新
数据绑定最方便的一点在于给了你数据对象通知的能力，当你的数据改变时。
有三种不同的数据改变通知机制，`Observable objects`, `observable fields`, and `observable collections`.

当这些可以观察的数据对象绑定到UI，当数据改变时，UI也会被自动的改变

#### Observable Objects

一个实现`Observable`接口的类将会

`Observable` 接口拥有添加和移除监听者的方法,但是，什么时候通知这些监听者则由开发者决定。
为了更容易的完成开发, 创造了`BaseObservable`这个基类实现了接口注册监听者的方法。
这些数据类的编写者们需要决定何时通知监听者数据改变了，做法就是把`Bindable`注解赋值给对应的get方法，在set方法里发送通知。

```java
private static class User extends BaseObservable {
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
```
在编辑的时候，`Bindable`注解将会在`BR`类文件内产生一个实体，`BR`类文件将会被生成在当前模块的包内。
如果数据类的基类不能被改变，使用`PropertyChangeRegistry`类来有效的储存和通知监听者来达到实现`Observable`接口的目的

#### ObservableFields

创建 `Observable` 类需要做一些额外的工作, 所以，如果开发者们想省时间或者数据类中只有很少的属性，
可以使用`ObservableField`和它的兄弟姐妹们`ObservableBoolean`,`ObservableByte`, `ObservableChar`,
`ObservableShort`, `ObservableInt`, `ObservableLong`, `ObservableFloat`,`ObservableDouble`, 和 `ObservableParcelable`.
`ObservableFields`是一个有单一变量的可观察的对象。
(不知道怎么翻译，直接把原文放在此做比对`ObservableFields` are self-contained observable objects that have a single field.)
对于原始类型，在访问的时候会防止自动装箱和拆箱操作
使用方法，创建一个final变量在数据类中：
```java
private static class User {
   public final ObservableField<String> firstName =
       new ObservableField<>();
   public final ObservableField<String> lastName =
       new ObservableField<>();
   public final ObservableInt age = new ObservableInt();
}
```
通过get和set方法访问它的值
```java
user.firstName.set("Google");
int age = user.age.get();
```

#### Observable Collections

一些应用需要更灵活的结构来储存数据。Observable集合允许通过key来访问这些数据
集合提供里两种方式：`ObservableArrayMap` and `ObservableArrayList`

当key是引用类型的时候，(例如String)，推荐使用`ObservableArrayMap`
```java
ObservableArrayMap<String, Object> user = new ObservableArrayMap<>();
user.put("firstName", "Google");
user.put("lastName", "Inc.");
user.put("age", 17);
```
在layout中, map通过字符串的keys来访问:
```xml
<data>
    <import type="android.databinding.ObservableMap"/>
    <variable name="user" type="ObservableMap&lt;String, Object>"/>
</data>
…
<TextView
   android:text='@{user["lastName"]}'
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>
<TextView
   android:text='@{String.valueOf(1 + (Integer)user["age"])}'
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>
```
当key是int类型的时候，推荐使用`ObservableArrayList` :
```java
ObservableArrayList<Object> user = new ObservableArrayList<>();
user.add("Google");
user.add("Inc.");
user.add(17);
```
在layout中, list通过indices来访问:
```xml
<data>
    <import type="android.databinding.ObservableList"/>
    <import type="com.example.my.app.Fields"/>
    <variable name="user" type="ObservableList&lt;Object>"/>
</data>
…
<TextView
   android:text='@{user[Fields.LAST_NAME]}'
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>
<TextView
   android:text='@{String.valueOf(1 + (Integer)user[Fields.AGE])}'
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>
```

该Demo参见`ObservableModel.java`

### Generated Binding



[1]: ./README_en.md




