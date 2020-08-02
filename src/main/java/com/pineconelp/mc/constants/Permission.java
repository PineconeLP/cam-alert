package com.pineconelp.mc.constants;

public class Permission {
    
    private static final String CAM_ALERT_PREFIX = "camalert";

    private static final String CREATE = ".create";
    private static final String UPDATE = ".update";

    private static final String RANGE = ".range";
    private static final String NOTIFY_THRESHOLD = ".notifythreshold";
    private static final String OWNER = ".owner";

    /**
     * Min value utility.
     */
    public static final String MIN = ".min.";

    /**
     * Max value utility.
     */
    public static final String MAX = ".max.";

    /**
     * Separator utility.
     */
    public static final String SEPARATOR = ".";

    /**
     * Self utility.
     */
    public static final String SELF = "self";

    /**
     * Any value utility.
     */
    public static final String ALL = ".*";

    /**
     * Create a camera.
     */
    public static final String CAM_ALERT_CREATE = CAM_ALERT_PREFIX + CREATE;

    /**
     * Update camera owner.
     */
    public static final String CAM_ALERT_UPDATE_OWNER = CAM_ALERT_PREFIX + UPDATE + OWNER;
    
    /**
     * Update camera range.
     */
    public static final String CAM_ALERT_UPDATE_RANGE = CAM_ALERT_PREFIX + UPDATE + RANGE;
    
    /**
     * Update camera notification threshold seconds.
     */
    public static final String CAM_ALERT_UPDATE_NOTIFY_THRESHOLD = CAM_ALERT_PREFIX + UPDATE + NOTIFY_THRESHOLD;

    /**
     * Super admin.
     */
    public static final String CAM_ALERT_ADMIN = CAM_ALERT_PREFIX + ALL;

    /**
     * Super admin update.
     */
    public static final String CAM_ALERT_UPDATE_ADMIN = CAM_ALERT_PREFIX + UPDATE + ALL;
}