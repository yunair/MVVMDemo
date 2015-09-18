# MVVMDemo
## This is a Demo About Google Data Binding Guide
## The URL is https://developer.android.com/intl/zh-cn/tools/data-binding/guide.html

---

### Build Environment

add below code to Top-level build file
```gradle
dependencies {
       classpath "com.android.tools.build:gradle:1.3.1"
       classpath "com.android.databinding:dataBinder:1.0-rc1"
   }
```
add below code to project build file
```gradle
apply plugin: 'com.android.databinding'
```
**if you meet error, update you support library first**

---

### Data Binding Layout Files

Data-binding layout files are slightly different and start with a root tag of layout
followed by a data element and a view root element.
This view element is what your root would be in a non-binding layout file.
A sample file looks like this:
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
The `user` variable within `data` describes a property that may be used within this `layout`.
```xml
<variable name="user" type="com.example.User"/>
```
Expressions within the layout are written in the attribute properties using the “@{}” syntax.
Here, the TextView’s text is set to the firstName property of user:
```xml
<TextView android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:text="@{user.firstName}"/>
```
the way to using variable in 'layout' converting it in 'java' is
```java
com.example.User user = new com.example.User();
```
we don't use this way often, except the class name conflicts,
In general, we will use this way:
```java
import com.example.User;
User user = new User();
```
so in `data`element，you can do the same thing by this
```xml
<import type="com.example.User"/>
<variable name="user" type="User"/>
```
If you meet class name conflicts, `import` element has a child element named `alias`
eg:
```xml
<import type="android.view.View"/>
<import type="com.example.real.estate.View"
        alias="Vista"/>
```
Now, "Vista" may be used to reference the `com.example.real.estate.View`
and "View" may be used to reference `android.view.View`

You can use static method as in Java
eg :  `android.view.View` is imported, you can use `View`'s static methods as below
```xml
<TextView
   android:text="@{user.lastName}"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:visibility="@{View.VISIBLE}"/>
```
Just as in Java, `java.lang.*` is imported automatically.

### Data Object

you can use a plain-old Java object (POJO) or JavaBeans object for User

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

By default, a Binding class will be generated based on the name of the layout file,
converting it to Pascal case and suffixing “Binding” to it.

eg: `activity_main.xml` will be generated a class Named `ActivityMainBinding`

The above layout file was `main_activity.xml` so the generate class was `MainActivityBinding`.
This class holds all the bindings from the layout properties (e.g. the user variable)
to the layout’s Views and knows how to assign values for the binding expressions.
The easiest means for creating the bindings is to do it while inflating:
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

By default, a Binding class is generated based on the name of the layout file,
starting it with upper-case, removing underscores ( _ ) and
capitalizing the following letter and then suffixing “Binding”.
This class will be placed in a databinding package under the module package.

For example, the layout file `contact_item.xm`l will generate `ContactItemBinding`.
If the module package is `com.example.my.app`,
then it will be placed in `com.example.my.app.databinding`.

If you want to use non-default Binding Class Names or change the place of the class,
You can custom them by adjusting the class attribute of the data element.

For example:
```xml
<data class="ContactItem">
    ...
</data>
```

This generates the binding class as `ContactItem` in the databinding package in the module package.
If the class should be generated in a different package within the module package,
it may be prefixed with “.”:
```xml
<data class=".ContactItem">
    ...
</data>
```
In this case, `ContactItem` is generated in the module package directly.
Any package may be used if the full package is provided:
```xml
<data class="com.example.ContactItem">
    ...
</data>
```
this demo you can see `CustomBindingClassNameActivity.java`

### Binding Event

Binding Event is similar with Binding Data, first define a handler to handle the event,
declare the event in '@{}', at the same time, set the handler in java file,
you can see usage in `BaseActivity.java`

### Includes
Variables may be passed into an included layout's binding from the containing layout
by using the application namespace and the variable name in an attribute:
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

you need `user` variable as a direct child of a data element in your `name.xml` and `contact.xml`

Data binding does not support `include` as a direct child of a `merge` element.

### Expression Language

#### Common Features

The expression language looks a lot like a Java expression. These are the same:

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

A few operations are missing from the expression syntax that you can use in Java.

- this
- super
- new
- Explicit generic invocation

#### Null Coalescing Operator

The null coalescing operator (`??`) chooses the left operand if it is not null or the right if it is null.

```xml
android:text="@{user.displayName ?? user.lastName}"
android:text="@{user.displayName != null ? user.displayName : user.lastName}"
```
two sentences are the same

#### Collections

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

#### String Literals

You have three way to use String literals
```xml
android:text='@{map["firstName"]}'
android:text="@{map[`firstName`]}"
android:text="@{map[&quot;firstName&quot;]}"
```
- using single quotes around the attribute value
- using double quotes around the attribute value,  use the back quote (```) for string.
- using double quotes around the attribute value,  use the `&quot;`for string.

this demo you can see `QuoteActivity.java`


#### Resources

It is possible to access resources as part of expressions using the normal syntax:
```xml
android:padding="@{large? @dimen/largePadding : @dimen/smallPadding}"
```

Format strings and plurals may be evaluated by providing parameters:
```xml
android:text="@{@string/nameFormat(firstName, lastName)}"
android:text="@{@plurals/banana(bananaCount)}"
```

Some resources require explicit type evaluation.

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

Any plain old Java object (POJO) may be used for data binding, but modifying a POJO will not cause the UI to update.
The real power of data binding can be used by giving your data objects the ability to notify when data changes.
There are three different data change notification mechanisms, `Observable objects`, `observable fields`, and `observable collections`.

When one of these observable data object is bound to the UI and a property of the data object changes, the UI will be updated automatically.

#### Observable Objects

A class implementing the Observable interface will allow the binding to attach a single listener to a bound object
to listen for changes of all properties on that object.

The `Observable` interface has a mechanism to add and remove listeners,but notifying is up to the developer.
To make development easier, a base class, `BaseObservable`, was created to implement the listener registration mechanism.
The data class implementer is still responsible for notifying when the properties change.
This is done by assigning a `Bindable` annotation to the getter and notifying in the setter.

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
The `Bindable` annotation generates an entry in the `BR` class file during compilation.
The `BR` class file will be generated in the module package. If the base class for data classes cannot be changed,
the `Observable` interface may be implemented using the convenient `PropertyChangeRegistry` to store and notify listeners efficiently.

#### ObservableFields

A little work is involved in creating `Observable` classes, so developers who want to
save time or have few properties may use `ObservableField` and its siblings `ObservableBoolean`,
`ObservableByte`, `ObservableChar`, `ObservableShort`, `ObservableInt`, `ObservableLong`, `ObservableFloat`,`ObservableDouble`, and `ObservableParcelable`.
`ObservableFields` are self-contained observable objects that have a single field.
The primitive versions avoid boxing and unboxing during access operations.
To use, create a public final field in the data class:
```java
private static class User {
   public final ObservableField<String> firstName =
       new ObservableField<>();
   public final ObservableField<String> lastName =
       new ObservableField<>();
   public final ObservableInt age = new ObservableInt();
}
```
To access the value, use the set and get accessor methods:
```java
user.firstName.set("Google");
int age = user.age.get();
```

#### Observable Collections

Some applications use more dynamic structures to hold data.
Observable collections allow keyed access to these data objects.
there are two ways in collections
`ObservableArrayMap` and `ObservableArrayList`


`ObservableArrayMap` is useful when the key is a reference type, such as String.

```java
ObservableArrayMap<String, Object> user = new ObservableArrayMap<>();
user.put("firstName", "Google");
user.put("lastName", "Inc.");
user.put("age", 17);
```
In the layout, the map may be accessed through the String keys:
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
`ObservableArrayList` is useful when the key is an integer:
```java
ObservableArrayList<Object> user = new ObservableArrayList<>();
user.add("Google");
user.add("Inc.");
user.add(17);
```
In the layout, the list may be accessed through the indices:
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

You can see these in `ObservableModel.java`

### Generated Binding

The generated binding class links the layout variables with the Views within the layout.
As discussed earlier, the name and package of the Binding may be customized.
The Generated binding classes all extend `ViewDataBinding`.

#### Creating

The binding should be created soon after inflation to ensure that the View hierarchy is not disturbed prior to
binding to the Views with expressions within the layout. There are a few ways to bind to a layout.
The most common is to use the static methods on the Binding class.The inflate method inflates the View hierarchy
and binds to it all it one step. There is a simpler version that only takes a `LayoutInflater` and one that takes a `ViewGroup` as well:

```java
MyLayoutBinding binding = MyLayoutBinding.inflate(layoutInflater);
MyLayoutBinding binding = MyLayoutBinding.inflate(layoutInflater, viewGroup, false);
```
If the layout was inflated using a different mechanism, it may be bound separately:
```java
MyLayoutBinding binding = MyLayoutBinding.bind(viewRoot);
```
Sometimes the binding cannot be known in advance. In such cases, the binding can be created using the DataBindingUtil class:
```java
ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater, layoutId,
    parent, attachToParent);
ViewDataBinding binding = DataBindingUtil.bindTo(viewRoot, layoutId);
```

### Views With IDs

A public final field will be generated for each View with an ID in the layout.
The binding does a single pass on the View hierarchy, extracting the Views with IDs.
This mechanism can be faster than calling findViewById for several Views.

```xml
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
           android:text="@{user.firstName}"
   android:id="@+id/firstName"/>
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.lastName}"
  android:id="@+id/lastName"/>
   </LinearLayout>
</layout>
```

Will generate a binding class with:

```xml
public final TextView firstName;
public final TextView lastName;
```
IDs are not nearly as necessary as without data binding,
but there are still some instances where access to Views are still necessary from code.

### Variables

Each variable will be given accessor methods.
```xml
<data>
    <import type="android.graphics.drawable.Drawable"/>
    <variable name="user"  type="com.example.User"/>
    <variable name="image" type="Drawable"/>
    <variable name="note"  type="String"/>
</data>
```

will generate setters and getters in the binding:

```java
public abstract com.example.User getUser();
public abstract void setUser(com.example.User user);
public abstract Drawable getImage();
public abstract void setImage(Drawable image);
public abstract String getNote();
public abstract void setNote(String note);
```

### ViewStubs

`ViewStubs` are a little different from normal Views.
They start off invisible and when they either are made visible or are explicitly told to inflate,
they replace themselves in the layout by inflating another layout.

Because the `ViewStub` essentially disappears from the View hierarchy,
the View in the binding object must also disappear to allow collection.
Because the Views are final, a `ViewStubProxy` object takes the place of the `ViewStub`,
giving the developer access to the `ViewStub` when it exists and also access to the inflated View hierarchy when the `ViewStub` has been inflated.

When inflating another layout, a binding must be established for the new layout.
Therefore, the `ViewStubProxy` must listen to the ViewStub's `ViewStub.OnInflateListener` and establish the binding at that time.
Since only one can exist, the `ViewStubProxy` allows the developer to set an `OnInflateListener` on it that it will call after establishing the binding.


### Advanced Binding

#### Dynamic Variables

At times, the specific binding class won't be known. For example,
a `RecyclerView.Adapter` operating against arbitrary layouts won't know the specific binding class.
It still must assign the binding value during the `onBindViewHolder(VH, int)`.

In this example, all layouts that the RecyclerView binds to have an "item" variable.
The BindingHolder has a getBinding method returning the `ViewDataBinding` base.
```java
public void onBindViewHolder(BindingHolder holder, int position) {
   final T item = mItems.get(position);
   holder.getBinding().setVariable(BR.item, item);
   holder.getBinding().executePendingBindings();
}
```

#### Immediate Binding

When a variable or observable changes, the binding will be scheduled to change before the next frame.
There are times, however, when binding must be executed immediately. To force execution, use the executePendingBindings() method.

#### Background Thread

You can change your data model in a background thread as long as it is not a collection.
Data binding will localize each variable / field while evaluating to avoid any concurrency issues.