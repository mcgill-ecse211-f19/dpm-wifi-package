package ca.mcgill.ecse211.project;

import java.math.BigDecimal;
import java.util.Map;
import ca.mcgill.ecse211.wificlient.WifiConnection;

/**
 * Integrate this carefully with your existing Resources class. The order in which things are
 * declared matters!
 * 
 * @author Younes Boubekeur
 */
public class Resources {
  
  // Set these as appropriate for your team and current situation
  /**
   * The default server IP used by the profs and TA's.
   */
  public static final String DEFAULT_SERVER_IP = "192.168.2.3";
  
  /**
   * The IP address of the server that transmits data to the robot. Set this to the default for the
   * beta demo and competition.
   */
  public static final String SERVER_IP = "192.168.0.101";
  
  /**
   * Your team number.
   */
  public static final int TEAM_NUMBER = 1;
  
  /** 
   * Enables printing of debug info from the WiFi class. 
   */
  public static final boolean ENABLE_DEBUG_WIFI_PRINT = true;
  
  /**
   * Enable this to attempt to receive Wi-Fi parameters at the start of the program.
   */
  public static final boolean RECEIVE_WIFI_PARAMS = true;
  
  /**
   * Container for the Wi-Fi parameters.
   */
  public static Map<String, Object> wifiParameters;
  
  // This static initializer MUST be declared before any Wi-Fi parameters.
  static {
    receiveWifiParameters();
  }
  
  /**
   * Red team number.
   */
  public static int redTeam = get("RedTeam");

  /**
   * Red team's starting corner.
   */
  public static int redCorner = get("RedCorner");

  /**
   * Green team number.
   */
  public static int greenTeam = get("GreenTeam");

  /**
   * Green team's starting corner.
   */
  public static int greenCorner = get("GreenCorner");

  /**
   * Red Zone, lower left hand corner, x value.
   */
  public static int red_ll_x = get("Red_LL_x");

  /**
   * Red Zone, lower left hand corner, y value.
   */
  public static int red_ll_y = get("Red_LL_y");

  /**
   * Red Zone, upper right hand corner, x value.
   */
  public static int red_ur_x = get("Red_UR_x");

  /**
   * Red Zone, upper right hand corner, y value.
   */
  public static int red_ur_y = get("Red_UR_y");

  /**
   * Green Zone, lower left hand corner, x value.
   */
  public static int green_ll_x = get("Green_LL_x");

  /**
   * Green Zone, lower left hand corner, y value.
   */
  public static int green_ll_y = get("Green_LL_y");

  /**
   * Green Zone, upper right hand corner, x value.
   */
  public static int green_ur_x = get("Green_UR_x");

  /**
   * Green Zone, upper right hand corner, y value.
   */
  public static int green_ur_y = get("Green_UR_y");

  /**
   * The Island, lower left hand corner, x value.
   */
  public static int island_ll_x = get("Island_LL_x");

  /**
   * The Island, lower left hand corner, y value.
   */
  public static int island_ll_y = get("Island_LL_y");

  /**
   * The Island, upper right hand corner, x value.
   */
  public static int island_ur_x = get("Island_UR_x");

  /**
   * The Island, upper right hand corner, y value.
   */
  public static int island_ur_y = get("Island_UR_y");

  /**
   * The red tunnel footprint, lower left hand corner, x value.
   */
  public static int tnr_ll_x = get("TNR_LL_x");

  /**
   * The red tunnel footprint, lower left hand corner, y value.
   */
  public static int tnr_ll_y = get("TNR_LL_y");

  /**
   * The red tunnel footprint, upper right hand corner, x value.
   */
  public static int tnr_ur_x = get("TNR_UR_x");

  /**
   * The red tunnel footprint, upper right hand corner, y value.
   */
  public static int tnr_ur_y = get("TNR_UR_y");

  /**
   * The green tunnel footprint, lower left hand corner, x value.
   */
  public static int tng_ll_x = get("TNG_LL_x");

  /**
   * The green tunnel footprint, lower left hand corner, y value.
   */
  public static int tng_ll_y = get("TNG_LL_y");

  /**
   * The green tunnel footprint, upper right hand corner, x value.
   */
  public static int tng_ur_x = get("TNG_UR_x");

  /**
   * The green tunnel footprint, upper right hand corner, y value.
   */
  public static int tng_ur_y = get("TNG_UR_y");

  /**
   * The location of the target bin, x value.
   */
  public static int bin_x = get("BIN_x");

  /**
   * The location of the target bin, y value.
   */
  public static int bin_y = get("BIN_y");
  
  /**
   * Receives Wi-Fi parameters from the server program.
   */
  public static void receiveWifiParameters() {
    // Only initialize the parameters if needed
    if (!RECEIVE_WIFI_PARAMS || wifiParameters != null) return;
    System.out.println("Waiting to receive Wi-Fi parameters.");

    // Connect to server and get the data, catching any errors that might occur
    try (WifiConnection conn =
        new WifiConnection(SERVER_IP, TEAM_NUMBER, ENABLE_DEBUG_WIFI_PRINT)) {
      /*
       * getData() will connect to the server and wait until the user/TA presses the "Start" button
       * in the GUI on their laptop with the data filled in. Once it's waiting, you can kill it by
       * pressing the upper left hand corner button (back/escape) on the EV3. getData() will throw
       * exceptions if it can't connect to the server (e.g. wrong IP address, server not running on
       * laptop, not connected to WiFi router, etc.). It will also throw an exception if it connects
       * but receives corrupted data or a message from the server saying something went wrong. For
       * example, if TEAM_NUMBER is set to 1 above but the server expects teams 17 and 5, this robot
       * will receive a message saying an invalid team number was specified and getData() will throw
       * an exception letting you know.
       */
      wifiParameters = conn.getData();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
  
  /**
   * Returns the Wi-Fi parameter int value associated with the given key.
   * 
   * @param key
   * @return the Wi-Fi parameter int value associated with the given key
   */
  public static int get(String key) {
    if (wifiParameters != null) {
      return ((BigDecimal) wifiParameters.get(key)).intValue();
    } else {
      return 0;
    }
  }
  
}
