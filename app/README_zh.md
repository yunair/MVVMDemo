# MVVMDemo
## This is a Demo About Google Data Binding Guide
## The URL is https://developer.android.com/intl/zh-cn/tools/data-binding/guide.html

### 初始化依赖

在 Top-level build 文件中加入以下代码

    dependencies {
           classpath "com.android.tools.build:gradle:1.3.1"
           classpath "com.android.databinding:dataBinder:1.0-rc1"
       }

在 project build 文件中加入以下代码

    apply plugin: 'com.android.databinding'

**如果你gradle sync时遇到了错误，首先尝试升级你的support library**

###


