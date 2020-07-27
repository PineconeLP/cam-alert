package com.pineconelp.mc.constants;

public class Permission {
    
    private static final String CAM_ALERT_PREFIX = "camalert";
    private static final String CAM_ALERT_CREATE_PREFIX = ".create";
    private static final String CAM_ALERT_UPDATE_PREFIX = ".update";

    /**
     * Create a camera.
     */
    public static final String CAM_ALERT_CREATE = CAM_ALERT_PREFIX + CAM_ALERT_CREATE_PREFIX;

    /**
     * Update camera owner.
     */
    public static final String CAM_ALERT_UPDATE_OTHERS = CAM_ALERT_PREFIX + CAM_ALERT_UPDATE_PREFIX + ".owner";
    
    /**
     * Update camera to any range.
     */
    public static final String CAM_ALERT_UPDATE_RANGE_ALL = CAM_ALERT_PREFIX + CAM_ALERT_UPDATE_PREFIX + ".range";
    
    /**
     * Update camera with minimum range [x].
     */
    public static final String CAM_ALERT_UPDATE_MIN_RANGE_X_PREFIX = CAM_ALERT_PREFIX + CAM_ALERT_UPDATE_PREFIX + ".minrange.";
    
    /**
     * Update camera with maximum range [x].
     */
    public static final String CAM_ALERT_UPDATE_MAX_RANGE_X_PREFIX = CAM_ALERT_PREFIX + CAM_ALERT_UPDATE_PREFIX + ".maxrange.";
    
    /**
     * Update camera with specific range [x].
     */
    public static final String CAM_ALERT_UPDATE_RANGE_X_PREFIX = CAM_ALERT_PREFIX + CAM_ALERT_UPDATE_PREFIX + ".range.";
    
    /**
     * Update camera with any notification threshold seconds.
     */
    public static final String CAM_ALERT_CREATE_NOTIFY_THRESHOLD_ALL = CAM_ALERT_PREFIX + CAM_ALERT_UPDATE_PREFIX + ".notifythreshold";

    /**
     * Update camera with minimum notification threshold seconds [x].
     */
    public static final String CAM_ALERT_CREATE_MIN_NOTIFY_THESHOLD_X_PREFIX = CAM_ALERT_PREFIX + CAM_ALERT_UPDATE_PREFIX + ".minnotifythreshold.";
    
    /**
     * Update camera with maximum notification threshold seconds [x].
     */
    public static final String CAM_ALERT_CREATE_MAX_NOTIFY_THESHOLD_X_PREFIX = CAM_ALERT_PREFIX + CAM_ALERT_UPDATE_PREFIX + ".maxnotifythreshold.";
    
    /**
     * Update camera with specific notification threshold seconds [x].
     */
    public static final String CAM_ALERT_CREATE_NOTIFY_THESHOLD_X_PREFIX = CAM_ALERT_PREFIX + CAM_ALERT_UPDATE_PREFIX + ".notifythreshold.";
    
    /**
     * Update any camera.
     */
    public static final String CAM_ALERT_CREATE_ALL = CAM_ALERT_PREFIX + CAM_ALERT_UPDATE_PREFIX + ".*";
}