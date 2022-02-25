package ru.boomearo.gamecontrol.commands.gamecontrol;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import ru.boomearo.gamecontrol.GameControl;
import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.managers.GameManager;
import ru.boomearo.gamecontrol.objects.IForceStartable;
import ru.boomearo.gamecontrol.objects.IGameManager;
import ru.boomearo.gamecontrol.objects.IGamePlayer;
import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;
import ru.boomearo.gamecontrol.objects.arena.ClipboardRegenableGameArena;
import ru.boomearo.serverutils.utils.other.commands.CmdInfo;
import ru.boomearo.serverutils.utils.other.commands.Commands;

public class GameControlUse implements Commands {

    @CmdInfo(name = "list", description = "Показать список всех игр.", usage = "/gamecontrol list", permission = "gamecontrol.admin")
    public boolean list(CommandSender cs, String[] args) {
        if (args.length != 0) {
            return false;
        }

        Collection<IGameManager<? extends IGamePlayer>> igm = GameControl.getInstance().getGameManager().getAllGameManagers();
        if (igm.isEmpty()) {
            cs.sendMessage(GameManager.prefix + "Ни одна игра не зарегистрирована!");
            return true;
        }


        final String sep = GameManager.prefix + "§8============================";

        cs.sendMessage(sep);
        for (IGameManager<? extends IGamePlayer> gm : igm) {
            cs.sendMessage(GameManager.prefix + "Игра '§9" + gm.getGameDisplayName() + "§7'");
            cs.sendMessage(GameManager.prefix + "Арен: §9" + gm.getAllArenas().size() + "§7. Игроков: §9" + gm.getAllPlayers().size());
            cs.sendMessage(sep);
        }

        return true;
    }

    @CmdInfo(name = "regen", description = "Принудительно добавить арену в пул регенерации.", usage = "/gamecontrol regen <игра> <арена>", permission = "gamecontrol.admin")
    public boolean regen(CommandSender cs, String[] args) {
        if (args.length != 2) {
            return false;
        }

        String g = args[0];

        GameManager gm = GameControl.getInstance().getGameManager();

        IGameManager<? extends IGamePlayer> igm = gm.getGameByName(g);
        if (igm == null) {
            cs.sendMessage(GameManager.prefix + "Игра '§9" + g + "§7' не найдена!");
            return true;
        }

        String a = args[1];

        AbstractGameArena<? extends IGamePlayer> aga = igm.getGameArena(a);
        if (aga == null) {
            cs.sendMessage(GameManager.prefix + "Арена '§9" + a + "§7' не найдена!");
            return true;
        }

        if (!(aga instanceof ClipboardRegenableGameArena crga)) {
            cs.sendMessage(GameManager.prefix + "Арена '§9" + a + "§7' не поддерживает регенерацию!");
            return true;
        }

        try {
            gm.queueRegenArena(crga);

            cs.sendMessage(GameManager.prefix + "Регенерация отправлена в очередь для арены '§9" + a + "§7'!");
        }
        catch (ConsoleGameException e) {
            cs.sendMessage(GameManager.prefix + "§9Ошибка: §7" + e.getMessage());
        }

        return true;
    }

    @CmdInfo(name = "forcestart", description = "Принудительно начать игру в указанной арене.", usage = "/gamecontrol forcestart <игра> <арена>", permission = "gamecontrol.admin")
    public boolean forcestart(CommandSender cs, String[] args) {
        if (args.length != 2) {
            return false;
        }

        String g = args[0];

        GameManager gm = GameControl.getInstance().getGameManager();

        IGameManager<? extends IGamePlayer> igm = gm.getGameByName(g);
        if (igm == null) {
            cs.sendMessage(GameManager.prefix + "Игра '§9" + g + "§7' не найдена!");
            return true;
        }

        String a = args[1];

        AbstractGameArena<? extends IGamePlayer> aga = igm.getGameArena(a);
        if (aga == null) {
            cs.sendMessage(GameManager.prefix + "Арена '§9" + a + "§7' не найдена!");
            return true;
        }

        if (!(aga instanceof IForceStartable ifs)) {
            cs.sendMessage(GameManager.prefix + "Арена '§9" + a + "§7' не поддерживает принудительный запуск игры!");
            return true;
        }

        if (ifs.isForceStarted()) {
            cs.sendMessage(GameManager.prefix + "Принудительный запуск уже установлен а арене '§9" + a + "§7'!");
            return true;
        }

        ifs.setForceStarted(true);

        cs.sendMessage(GameManager.prefix + "Игра успешно принудительно запущена в арене '§9" + a + "§7'!");

        return true;
    }

    @CmdInfo(name = "forcesavestats", description = "Принудительно сохранить статистику всех игр.", usage = "/gamecontrol forcesavestats", permission = "gamecontrol.admin")
    public boolean forcesavestats(CommandSender cs, String[] args) {
        GameManager gm = GameControl.getInstance().getGameManager();

        try {
            gm.queueSaveTask(gm::saveAllGameStats);

            cs.sendMessage(GameManager.prefix + "Сохранение всех статистик игр успешно запущено!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
