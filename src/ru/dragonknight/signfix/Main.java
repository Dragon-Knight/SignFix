package ru.dragonknight.signfix;

/**
 * @author Dragon_Knight
 *
 */

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	static Block celectedBlock;
	
	@Override
	public void onEnable()
	{
		celectedBlock = null;
		
		getServer().getPluginManager().registerEvents(new MyListener(), this);
		this.getCommand("signfix").setExecutor(new CommandProcessor());
	}
	
	@Override
	public void onDisable()
	{
		celectedBlock = null;
	}
	
	public static void sendMessage(Player player, String str)
	{
		player.sendMessage("[SignFix]: " + str);
	}
	
}
