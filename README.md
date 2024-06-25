as l# The spigot development library to speed up development, readability and stability!
---
This plugin was created to further boost the speed of plugin development, officially made by **Efnilite**!

NOTE: **This doesn't make your code compile and run faster, it creates the ability for you to develop your plugin faster.**

## How do I use this?
You can use it in a variety of ways:

### Using Maven
Maven: pom.xml:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>


<dependency>
    <groupId>com.github.efnilite</groupId>
    <artifactId>vilib</artifactId>
    <version>version</version>
</dependency>
```

**REPLACE \*version\* WITH THE LATEST VERSION OF VILIB!**

### Using Gradle
Gradle **(Groovy)**
```groovy
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    ...
    implementation 'com.github.efnilite:vilib:version' 
}
```

**REPLACE \*version\* WITH THE LATEST VERSION OF VILIB!**

Gradle **(Kotlin)**
```kotlin
repositories {
    ...
    maven { url = uri('https://jitpack.io') }
}

dependencies {
    ...
    implementation('com.github.efnilite:vilib:version')
}
```

**REPLACE \*version\* WITH THE LATEST VERSION OF VILIB!**

If you use anything other than maven/gradle then you should [check this out](https://jitpack.io/#efnilite/vilib)

## FAQ
**Does vilib have a java documentation**?

No, although we plan to create javadocs for vilib soon.

**Where can I find support for this server**?

You can find the support discord server [here](https://discord.gg/Qd67XnxS5s)

**Which plugins use this?**

You can find all my creations [here](https://github.com/Efnilite/)

Most of my plugins use them.
