package ru.dragonknight.signfix;

/**
 * @author Dragon_Knight
 *
 */

import java.io.UnsupportedEncodingException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MyListener implements Listener
{
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) throws UnsupportedEncodingException, IndexOutOfBoundsException
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Block block = event.getClickedBlock();
			Player player = event.getPlayer();
			
			if(block.getState() instanceof Sign)
			{
				ItemStack item = event.getItem();
				
				if(item == null || item.getType() == Material.AIR)
				{
					Main.celectedBlock = block;
					Main.sendMessage(player, ChatColor.AQUA + "Редактирование таблички: Введите команду /signfix edit НомерСтроки Текст");
				}
				else if(item.getType() == Material.STICK)
				{
					Main.celectedBlock = null;
					
					Sign sign = (Sign)block.getState();
					String[] lines = sign.getLines();
					
					Boolean convert = false;
					for(String line : lines)
					{
						for(int i = 0; i < line.length(); i++)
						{
							if(line.charAt(i) >= 0x00C0 && line.charAt(i) <= 0x00FF)
							{
								convert = true;
								break;
							}
						}
					}
					
					if(convert == true)
					{
						Main.sendMessage(player, ChatColor.GREEN + "Обнаружена табличка с кодировкой CP1252, конвертируем ...");
						
						int idx = 0;
						for(String line : lines)
						{
							String tmp = new String(line.getBytes("Cp1252"), "Cp1251");
							Main.sendMessage(player,  ChatColor.GREEN + "|- '" + ChatColor.GOLD + line + ChatColor.GREEN + "' => '" + ChatColor.GOLD + tmp + ChatColor.GREEN + "'");
							sign.setLine(idx, tmp);
							idx++;
						}
						sign.update(true);
						
						Main.sendMessage(player, ChatColor.GREEN + "Табличка сконвертирована.");
					}
					else
					{
						Main.sendMessage(player, ChatColor.RED + "Табличка не требует конвертации.");
					}
				}
			}
		}
	}
}
