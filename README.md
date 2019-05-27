# JTerminalApp
#### It is a small framework which can be used to compose terminal apps on UNIX systems
I made a snake game in the terminal for fun during one weekend, then generalized out this framework from it.
I take the Unity framework as an example when I designed the mechanism of the AppObjects in terms of extending
them and override their update method to get a unique behavior during frame updates in the engine. My main loop
is not too sophisticated but it is working :D

# So let's see what's inside
![](https://github.com/AdamGonda/JTerminalApp/blob/master/packages.png)

### If you like to use this framework your only job is to inherit from AppObject class.
After that, you can create your unique behavior using the update() method if you like to 
do something in every update during runtime.
```java
public class Spaceship extends AppObject {
    ...
}
```

When you need some kind of input from a user there is an Input class 
that the App object gives access to you during running.
```java
public void update() {
    this.checkUserInput();
    this.checkEnemiesYPos();
    this.checkBulletHit();
}
```

When you like to use your AppObjects you need to create a new App object and add your instances to it.
```java
public static void main(String[] args){
        App app = new App(40, 20, false);

        Spaceship spaceShip = new Spaceship(20, 17, new char[][] {{'^'}}, app, 3);
        
        app.addAppObject(spaceShip);
        
        app.start();

    }
```
### For more information check out the [Docs](https://adamgonda.github.io/JTerminalApp)
