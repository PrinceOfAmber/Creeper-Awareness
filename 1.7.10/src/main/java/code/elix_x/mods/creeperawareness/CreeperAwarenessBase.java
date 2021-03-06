package code.elix_x.mods.creeperawareness;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import code.elix_x.excore.EXCore;
import code.elix_x.mods.creeperawareness.api.ExplosionSrcManager;
import code.elix_x.mods.creeperawareness.events.BindCreeperEvent;
import code.elix_x.mods.creeperawareness.events.BindTntEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = CreeperAwarenessBase.MODID, name = CreeperAwarenessBase.NAME, version = CreeperAwarenessBase.VERSION, dependencies = "required-after:" + EXCore.DEPENDENCY, acceptedMinecraftVersions = EXCore.MCVERSION, acceptableRemoteVersions = "*")
public class CreeperAwarenessBase {

	public static final String MODID = "creeperawareness";
	public static final String NAME = "Creeper Awareness";
	public static final String VERSION = "2.0.1";

	public static final Logger logger = LogManager.getLogger(NAME);

	public static File configFile;
	public static Configuration config;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		ExplosionSrcManager.preInit(event);

		configFile = new File(ExplosionSrcManager.configFolder, "Explosion Sources.cfg");
		try {
			configFile.createNewFile();
		} catch (IOException e){
			logger.error("Caught exception while creating config file: ", e);
		}
		config = new Configuration(configFile);

		config.load();
		if(config.getBoolean("creeper", "Vanilla", true, "Should creepers be valid explosion sources?")){
			MinecraftForge.EVENT_BUS.register(new BindCreeperEvent());
		}
		if(config.getBoolean("tnt", "Vanilla", true, "Should tnts be valid explosion sources?")){
			MinecraftForge.EVENT_BUS.register(new BindTntEvent());
		}
		config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event){
		ExplosionSrcManager.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		ExplosionSrcManager.postInit(event);
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event){
		ExplosionSrcManager.serverStopping(event);
	}

}