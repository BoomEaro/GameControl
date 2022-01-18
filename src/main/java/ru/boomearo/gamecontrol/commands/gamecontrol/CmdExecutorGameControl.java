package ru.boomearo.gamecontrol.commands.gamecontrol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import ru.boomearo.gamecontrol.GameControl;
import ru.boomearo.gamecontrol.managers.GameManager;
import ru.boomearo.gamecontrol.objects.IForceStartable;
import ru.boomearo.gamecontrol.objects.IGameManager;
import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;
import ru.boomearo.gamecontrol.objects.arena.RegenableGameArena;
import ru.boomearo.serverutils.utils.other.commands.AbstractExecutor;

public class CmdExecutorGameControl extends AbstractExecutor implements TabCompleter {

    private static final List<String> empty = new ArrayList<>();

    public CmdExecutorGameControl() {
        super(new GameControlUse());
    }

    @Override
    public boolean zeroArgument(CommandSender sender) {
        sendUsageCommands(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("gamecontrol.admin")) {
            return empty;
        }
        if (args.length == 1) {
            List<String> matches = new ArrayList<>();
            String search = args[0].toLowerCase();
            for (String se : Arrays.asList("list", "regen", "forcestart")) {
                if (se.toLowerCase().startsWith(search)) {
                    matches.add(se);
                }
            }
            return matches;
        }
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("regen") || args[0].equalsIgnoreCase("forcestart")) {
                List<String> matches = new ArrayList<>();
                String search = args[1].toLowerCase();
                for (IGameManager igm : GameControl.getInstance().getGameManager().getAllGameManagers()) {
                    if (igm.getGameName().toLowerCase().startsWith(search)) {
                        matches.add(igm.getGameName());
                    }
                }
                return matches;
            }
        }
        else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("regen")) {
                IGameManager igm = GameControl.getInstance().getGameManager().getGameByName(args[1]);
                if (igm != null) {
                    List<String> matches = new ArrayList<>();
                    String search = args[2].toLowerCase();
                    for (AbstractGameArena aga : igm.getAllArenas()) {
                        if (aga instanceof RegenableGameArena) {
                            if (aga.getName().toLowerCase().startsWith(search)) {
                                matches.add(aga.getName());
                            }
                        }
                    }
                    return matches;
                }
            }
            else if (args[0].equalsIgnoreCase("forcestart")) {
                IGameManager igm = GameControl.getInstance().getGameManager().getGameByName(args[1]);
                if (igm != null) {
                    List<String> matches = new ArrayList<>();
                    String search = args[2].toLowerCase();
                    for (AbstractGameArena aga : igm.getAllArenas()) {
                        if (aga instanceof IForceStartable) {
                            if (aga.getName().toLowerCase().startsWith(search)) {
                                matches.add(aga.getName());
                            }
                        }
                    }
                    return matches;
                }
            }
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
