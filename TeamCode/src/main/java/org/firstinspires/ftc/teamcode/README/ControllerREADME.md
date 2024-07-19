# Controller Class README

The `Controller` class in this package provides a comprehensive interface for interacting with a gamepad in the context of a robotics or game development project. It's designed to facilitate the handling of various buttons, triggers, and thumbsticks.

## Getting Started

### Initialization
To start using the `Controller` class, follow these steps:

1. **Import the Class**: Ensure that the **Controller** class is imported into your project.
   ```java
   import org.firstinspires.ftc.teamcode.Controller;
   ```
2. **Create an Instance**: Instantiate the **Controller** class by passing a **Gamepad** object.
   ```java
   Controller controller = new Controller(gamepad); // 'gamepad' is an instance of com.qualcomm.robotcore.hardware.Gamepad
   ```
   Ensure that you have the necessary **Gamepad** object ready to pass to the **Controller**.

## Updating the Controller
The **update()** method should be called within your main loop or update cycle to refresh the state of all buttons, triggers, and thumbsticks.
```java
// Call this method in your update loop to keep the controller inputs updated
controller.update();
```
## Components of the Controller

### Buttons
The Button`` class provides functionality for various buttons on the gamepad. You can check if a button is held (**hold()**), pressed (**press()**), or toggled (**toggle()**).

Example:

```java
if (controller.circle.press()) {
        // Execute code when the circle button is pressed
        }
```

### Triggers
The **Trigger** class represents the triggers on the gamepad. You can obtain the value of the trigger (**value()**), check if it's held (**hold()**), pressed (**press()**), or toggled (**toggle()**).

Example:

```java
if (controller.rightTrigger.hold()) {
        // Execute code when the right trigger is held
        }
```

### Thumbsticks
The **Thumbstick** class handles the thumbsticks' positional data and angles. You can retrieve raw and shifted X and Y values, as well as the angle of the thumbstick.

Example:

```java
double x = controller.leftStick.X(); // Get raw X value of the left thumbstick
        double angle = controller.rightStick.getAngle(); // Get the angle of the right thumbstick
```

## Notifications and Rumble Management

The **Notification** classes provide capabilities for creating different types of rumble effects. These can be managed using the **RumbleManager** class.

Please refer to the class documentation within the code for more detailed information on how to create and manage rumble notifications.



## Contributors

[Gabriel Ioannidis](https://github.com/Gabkion)
[Gvol](https://github.com/Gvolexe)
[Markos Narinian](https://github.com/markosnarinian)
