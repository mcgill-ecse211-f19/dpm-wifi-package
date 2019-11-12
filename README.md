# ECSE211 Wi-Fi Package - Student documentation

### Fall 2019

**Note:** The latest version of this document can always be found at [github.com/mcgill-ecse211-f19/dpm-wifi-package/blob/master/README.md](https://github.com/mcgill-ecse211-f19/dpm-wifi-package/blob/master/README.md).

## Overview

As mentioned in the project document, there are a lot of parameters that your robot must
know about in order to complete the competition. This includes knowing which corner it starts in, where the red and green zones are, and so on. This information will be sent over
Wi-Fi to your robot at the start of the round, after which your robot can localize and do its job.
The software that we provide to do this is split into two parts: a server and a client. This repository contains code for both parts.

## The Server

The server receives and responds to requests from a client, in exactly the same way your
computer is a client that connects to servers running websites. In this case, your robot is the client and you can test with the server on your laptop. In the beta demo and final competition, the
TA/professor will run the server and they will decide what data to send to your robot.

To run the server, double-click the JAR file in your file explorer, or run it from the command line (`./EV3WifiServer.jar` or `java -jar EV3WifiServer.jar`).
If you're running Windows, select `Allow Access` if you get firewall security prompt.

You can populate the parameters manually, from an XML file, or by copying parameters from the [Competition Map website](https://mcgill-dpm.github.io/Utilities/wifi/competition-map).

## The Client

The EV3 client code we provide can be split into two sections. First is the `WifiConnection` class,
which handles all communication to and from the server. It is packaged as a JAR, which you should include on your Java build path.
The second is the `ClientExample`
project, which provides an example of how to use the parameters obtained over Wi-Fi.

## Running the Example Project

0. Connect to the DPM Wi-Fi
([Instructions](https://mcgill-ecse211-f19.github.io/getting_started_guide/GettingStarted-F19-updated#running-code-on-the-brick)).

1. Open the server and populate its parameters. You can use the included `example_data_fill.xml` in the `Server/` folder. Don't click `Start` yet.

2. Import the `ClientExample` folder as an existing Eclipse project. 

    Fix project setup issues, if you see red markings (:heavy_exclamation_mark: or :x:). If you don't see them, go to step 3.

    Sometimes Eclipse does not locate the correct JRE (Java Runtime Enviroment) needed to run the project. To fix this, right-click the project and select `Build Path` > `Configure Build Path` > `Libraries`, then remove the unbound JRE (the one with :x:), then select `Add Library` > `JRE System Library`, and select the JRE that you used in the labs.

3. Set `Resources.SERVER_IP` to the IP address of your computer (**not** the EV3 IP address).
You can find your IP address by running `hostname -I` on Linux, or from the network properties GUI of your OS. It should be in the form "192.168.x.y". Also set `Resources.TEAM_NUMBER` now so you don't forget.

4. Run the `ClientExample` project as an EV3 program. Then click `Start` in the Server GUI after seeing your team is connected. Ensure that you observe output that resembles this in the EV3 Console or on its screen:

    ```
    Running...
    Waiting to receive Wi-Fi parameters.
    Connecting...
    Connected. Sending request.
    Request sent; waiting for response
    Response received OK.
    Map:
    {RedTeam=1, TNR_UR_y=8, TNR_UR_x=6, BIN_y=-3, TNG_LL_x=10, BIN_x=6, Island_UR_x=15, TNR_LL_y=7, Island_UR_y=9, TNR_LL_x=4, GreenCorner=1, TNG_LL_y=3, Green_UR_y=5, Green_UR_x=12, Island_LL_x=6, Red_UR_y=9, Red_UR_x=4, Island_LL_y=5, RedCorner=3, GreenTeam=3, TNG_UR_y=5, TNG_UR_x=11, Red_LL_y=5, Red_LL_x=0, Green_LL_y=0, Green_LL_x=4}
    Red Team: 1
    Green Zone: [(4.0, 0.0), (12.0, 5.0)]
    Island Zone, upper right: (15.0, 9.0)
    Red tunnel footprint, lower left y value: 7.0
    Bin location X >= 5
    ```

## Integrating with your code

Add `EV3WiFiClient.jar` to `lib/` and point your Java Build Path to it by right-clicking the project and selecting `Build Path` > `Configure Build Path` > `Libraries` > `Add JARs` and selecting it from the file picker.

To avoid build errors in Travis CI, add this line in `build.gradle`, in the `dependencies` section:

```groovy
implementation files('lib/EV3WiFiClient.jar')
```

Add all the items you declared in your `Resources` class to the one we provide here, at the location specified by this code comment:

```java
// DECLARE YOUR CURRENT RESOURCES HERE
```

If you do not want the robot to wait for Wi-Fi parameters (eg, if you're testing something else), set `RECEIVE_WIFI_PARAMS` to `false`.

Note that if you follow the simplified example we provide, you do not need to create separate variables for each Wi-Fi parameter described
in the project documents, since we already create variables for you in the sample `Resources` class. You can access them using dot notation.

For example:

- For `Green_UR_x`, use `green.ur.x`.
- For `TNR_LL_x`, use `tnr.ll.x`.

To see all regions, refer to the `Resources` class or use Ctrl-space in Eclipse for code completion suggestions.

Feel free to improve the simple `Region` static class to include logic that helps you calculate its length, width,
or area if you think that would be useful. 

To do that, add instance methods to `Region` like this:

```java
public static class Region {
  // ...

  public double length() {
    // use ur and ll to calculate and return the length
  }
}
```

It is a good idea to test these methods using JUnit.




## Important points

- You **must** use the most recent version of the client and server. We will notify you of major updates.
- In the beta demo and final competition:
  - The TA or professor will run the server published in this repository.
  - Recall that you will need to change the `SERVER_IP` to `DEFAULT_SERVER_IP`.
  - The professors will decide what parameters the server will send to your robot.
As a result, your code must be able to handle **any** valid input as specified in the project description.
- The [website](https://mcgill-dpm.github.io/Utilities/wifi/competition-map) is useful to help you visualize different competition layouts. However, it does not enforce all constraints, so make sure you 
account for all possiblities described by the project specification.
- The very first thing your robot should do is connect to the server and download
the parameters. Your robot should only start moving **after** having received data.
- If you are using the Wi-Fi example we provided, the provided code is tailored to the competition and will throw an exception when 
given invalid input, but since we are using the red tunnel coordinate value as the target angle for the beta demo,
you can bypass this by changing this line (in `Resources`):
  ```java
  public static Region tnr = new Region("TNR_LL_x", "TNR_LL_y", "TNR_UR_x", "TNR_UR_y");
  ```
  to this:
  ```java
  //public static Region tnr = new Region("TNR_LL_x", "TNR_LL_y", "TNR_UR_x", "TNR_UR_y");
  public static double targetAngle = Math.max(get("TNR_LL_x"), get("TNR_UR_x"));
  ```
  Do this only for the beta demo, and remember to change it back afterwards. 

## Useful Information
- The WiFi test code uses `System.out.println()` statements that print to both the screen
and, if connected, the EV3Control console. This is particularly useful for debugging as reading output is easier on your laptop. 
To disable printing of `WifiConnection` messages, set `Resources.ENABLE_DEBUG_WIFI_PRINT` to `false`.
- The file `layout.xml` in the `Server/` folder contains a list of all possible parameters.
- Post on the discussion board if you have questions. You may also contact your mentor TA.
- If there are any issues/bugs, or if you have suggestions on how we can improve this codebase or this documentation, open an issue [here](https://github.com/mcgill-ecse211-f19/dpm-wifi-package/issues).
