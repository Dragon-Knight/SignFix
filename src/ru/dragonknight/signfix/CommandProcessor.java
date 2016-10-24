package ru.dragonknight.signfix;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandProcessor implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			
			if(args.length == 0)
			{
				Main.sendMessage(player, ChatColor.AQUA + "Плагин исправляет текст на табличках, написанный с использованием собственных русификаторов.");
				Main.sendMessage(player, ChatColor.AQUA + "Для исправления достаточно нажать на табличку правой кнопкой мышки палкой в руках.");
				Main.sendMessage(player, ChatColor.AQUA + "Так-же плагин позволяет редактировать таблички, для этого нажмите на табличку правой кнопкой мышки пуской рукой.");
			}
			else
			{
				if(args[0].equalsIgnoreCase("edit"))
				{
					if(Main.celectedBlock != null)
					{
						Sign sign = (Sign)Main.celectedBlock.getState();
						
						String tmpline = "";
						int idx = 0;
						for(String line : args)
						{
							idx++;
							if(idx < 3) continue;
							tmpline += line;
						}
						
						sign.setLine(Integer.parseInt(args[1]) - 1, tmpline);
						sign.update(true);
						
						Main.celectedBlock = null;
						
						Main.sendMessage(player, ChatColor.GREEN + "Табличка отредактированна.");
					}
					else
					{
						Main.sendMessage(player, ChatColor.RED + "Необходимо сначала выбрать табличку. Для этого нажмите на неё правой нопкой и пустой рукой.");
					}
				}
			}
		}
		
		return true;
	}
}
