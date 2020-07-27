package com.pineconelp.mc;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.pineconelp.mc.commands.ICommandHandler;
import com.pineconelp.mc.commands.UpdateCommandHandler;
import com.pineconelp.mc.commands.UpdateOwnerCommandHandler;
import com.pineconelp.mc.commands.UpdateRangeCommandHandler;
import com.pineconelp.mc.commands.UpdateThresholdCommandHandler;
import com.pineconelp.mc.commands.CamAlertCommand;
import com.pineconelp.mc.commands.CreateCommandHandler;
import com.pineconelp.mc.commands.WelcomeCommandHandler;
import com.pineconelp.mc.items.cameras.ICameraItemDetailer;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;
import com.pineconelp.mc.items.cameras.ICameraItemValidator;
import com.pineconelp.mc.items.cameras.NMSCameraItemFactory;
import com.pineconelp.mc.listeners.CameraDestroyedListener;
import com.pineconelp.mc.listeners.CameraPlacedListener;
import com.pineconelp.mc.listeners.PlayerCameraMovementListener;
import com.pineconelp.mc.runnables.EntityCameraMovementRunnable;
import com.pineconelp.mc.seeders.CamAlertDatabaseSeeder;
import com.pineconelp.mc.seeders.ICamAlertSettingsSeeder;
import com.pineconelp.mc.seeders.Seeder;
import com.pineconelp.mc.services.cam_alert_settings_repositories.ConfigCamAlertSettingsRepository;
import com.pineconelp.mc.services.cam_alert_settings_repositories.ICamAlertSettingsRepository;
import com.pineconelp.mc.services.camera_notifiers.ChatCameraNotifier;
import com.pineconelp.mc.services.camera_notifiers.ICameraNotifier;
import com.pineconelp.mc.services.camera_repositories.ICameraRepository;
import com.pineconelp.mc.services.camera_repositories.database.DatabaseCameraRepository;
import com.pineconelp.mc.stores.CamAlertSettingsStore;
import com.pineconelp.mc.stores.CameraStore;
import com.pineconelp.mc.utilities.DatabaseSessionFactory;
import com.pineconelp.mc.utilities.TaskRunner;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;

public class CamAlertModule extends AbstractModule {

    private App app;

    public CamAlertModule(App app) {
        this.app = app;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        String dataFolderPath = this.app.getDataFolder().getAbsolutePath();
        String connectionString = "jdbc:h2:file:" + dataFolderPath + "\\camalert.db;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE;";
        bind(DatabaseSessionFactory.class).toInstance(new DatabaseSessionFactory(connectionString));
        bind(TaskRunner.class).in(Singleton.class);;        

        bind(ICameraItemFactory.class).to(NMSCameraItemFactory.class).in(Singleton.class);
        bind(ICameraItemValidator.class).to(NMSCameraItemFactory.class).in(Singleton.class);
        bind(ICameraItemDetailer.class).to(NMSCameraItemFactory.class).in(Singleton.class);

        bind(ICameraNotifier.class).to(ChatCameraNotifier.class).in(Singleton.class);

        bind(ConfigCamAlertSettingsRepository.class).in(Singleton.class);
        bind(ICamAlertSettingsRepository.class).to(ConfigCamAlertSettingsRepository.class);
        bind(ICamAlertSettingsSeeder.class).to(ConfigCamAlertSettingsRepository.class);

        bind(ICameraRepository.class).to(DatabaseCameraRepository.class).in(Singleton.class);
        
        bind(CameraStore.class).in(Singleton.class);
        bind(CamAlertSettingsStore.class).in(Singleton.class);

        bind(ICommandHandler.class).annotatedWith(Names.named("NoArgsHandler")).to(WelcomeCommandHandler.class).in(Singleton.class);
        bind(ICommandHandler.class).annotatedWith(Names.named("CreateCommandHandler")).to(CreateCommandHandler.class).in(Singleton.class);
        bind(ICommandHandler.class).annotatedWith(Names.named("UpdateCommandHandler")).to(UpdateCommandHandler.class).in(Singleton.class);
        bind(ICommandHandler.class).annotatedWith(Names.named("UpdateRangeCommandHandler")).to(UpdateRangeCommandHandler.class).in(Singleton.class);
        bind(ICommandHandler.class).annotatedWith(Names.named("UpdateThresholdCommandHandler")).to(UpdateThresholdCommandHandler.class).in(Singleton.class);
        bind(ICommandHandler.class).annotatedWith(Names.named("UpdateOwnerCommandHandler")).to(UpdateOwnerCommandHandler.class).in(Singleton.class);
        bind(CommandExecutor.class).to(CamAlertCommand.class).in(Singleton.class);

        bind(CameraPlacedListener.class).in(Singleton.class);
        bind(CameraDestroyedListener.class).in(Singleton.class);
        bind(PlayerCameraMovementListener.class).in(Singleton.class);

        bind(EntityCameraMovementRunnable.class).in(Singleton.class);

        bind(CamAlertDatabaseSeeder.class).in(Singleton.class);
        bind(Seeder.class).in(Singleton.class);

        bind(Plugin.class).toInstance(app);
    }
}