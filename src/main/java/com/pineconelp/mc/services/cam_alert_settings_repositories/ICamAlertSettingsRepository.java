package com.pineconelp.mc.services.cam_alert_settings_repositories;

import com.pineconelp.mc.models.CamAlertSettings;

public interface ICamAlertSettingsRepository {
    CamAlertSettings loadSettings();
    void saveSettings(CamAlertSettings settings);
}