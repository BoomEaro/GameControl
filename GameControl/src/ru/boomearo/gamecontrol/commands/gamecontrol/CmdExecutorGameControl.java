package ru.boomearo.gamecontrol.commands.gamecontrol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import ru.boomearo.gamecontrol.commands.AbstractExecutor;
import ru.boomearo.gamecontrol.commands.CmdList;
import ru.boomearo.gamecontrol.managers.GameManager;

public class CmdExecutorGameControl extends AbstractExecutor {

	public CmdExecutorGameControl() {
		super(new GameControlUse());
	}

	@Override
	public boolean zeroArgument(CommandSender sender, CmdList cmds) {
		cmds.sendUsageCmds(sender);
		return true;
	}

	private static final List<String> empty = new ArrayList<>();

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
	    if (!arg0.hasPermission("gamecontrol.admin")) {
	        return empty;
	    }
        if (arg3.length == 1) {
            List<String> ss = new ArrayList<String>(Arrays.asList("list", "regen", "forcestart"));
            List<String> matches = new ArrayList<>();
            String search = arg3[0].toLowerCase();
            for (String se : ss)
            {
                if (se.toLowerCase().startsWith(search))
                {
                    matches.add(se);
                }
            }
            return matches;
        }
        return empty;
	}

	@Override
	public String getPrefix() {
		return GameManager.prefix;
	}

	@Override
	public String getSuffix() {
		return " §8-§9 ";
	}
}
