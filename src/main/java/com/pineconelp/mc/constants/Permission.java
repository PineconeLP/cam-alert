package com.pineconelp.mc.constants;

public class Permission {
    
    private static final String CAM_ALERT_PREFIX = "camalert";
    private static final String CAM_ALERT_CREATE_PREFIX = ".create";

    /**
     * Create camera for self with default permissions.
     */
    public static final String CAM_ALERT_CREATE = CAM_ALERT_PREFIX + CAM_ALERT_CREATE_PREFIX;

    /**
     * Create camera for other players.
     */
    public static final String CAM_ALERT_CREATE_OTHERS = CAM_ALERT_PREFIX + CAM_ALERT_CREATE_PREFIX + ".others";
    
    /**
     * Create camera for any range.
     */
    public static final String CAM_ALERT_CREATE_RANGE_ALL = CAM_ALERT_PREFIX + CAM_ALERT_CREATE_PREFIX + ".range";
    
    /**
     * Create camera with minimum range [x].
     */
    public static final String CAM_ALERT_CREATE_MIN_RANGE_X_PREFIX = CAM_ALERT_PREFIX + CAM_ALERT_CREATE_PREFIX + ".minrange.";
    
    /**
     * Create camera with maximum range [x].
     */
    public static final String CAM_ALERT_CREATE_MAX_RANGE_X_PREFIX = CAM_ALERT_PREFIX + CAM_ALERT_CREATE_PREFIX + ".maxrange.";
    
    /**
     * Create camera with specific range [x].
     */
    public static final String CAM_ALERT_CREATE_RANGE_X_PREFIX = CAM_ALERT_PREFIX + CAM_ALERT_CREATE_PREFIX + ".range.";
    
    /**
     * Create camera with any notification threshold seconds.
     */
    public static final String CAM_ALERT_CREATE_NOTIFY_THRESHOLD_ALL = CAM_ALERT_PREFIX + CAM_ALERT_CREATE_PREFIX + ".notifythreshold";

    /**
     * Create camera with minimum notification threshold seconds [x].
     */
    public static final String CAM_ALERT_CREATE_MIN_NOTIFY_THESHOLD_X_PREFIX = CAM_ALERT_PREFIX + CAM_ALERT_CREATE_PREFIX + ".minnotifythreshold.";
    
    /**
     * Create camera with maximum notification threshold seconds [x].
     */
    public static final String CAM_ALERT_CREATE_MAX_NOTIFY_THESHOLD_X_PREFIX = CAM_ALERT_PREFIX + CAM_ALERT_CREATE_PREFIX + ".maxnotifythreshold.";
    
    /**
     * Create camera with specific notification threshold seconds [x].
     */
    public static final String CAM_ALERT_CREATE_NOTIFY_THESHOLD_X_PREFIX = CAM_ALERT_PREFIX + CAM_ALERT_CREATE_PREFIX + ".notifythreshold.";
    
    /**
     * Create any camera.
     */
    public static final String CAM_ALERT_CREATE_ALL = CAM_ALERT_PREFIX + CAM_ALERT_CREATE_PREFIX + ".*";
}