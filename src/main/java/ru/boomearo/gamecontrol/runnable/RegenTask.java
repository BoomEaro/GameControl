package ru.boomearo.gamecontrol.runnable;

import java.io.File;
import java.io.FileInputStream;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import ru.boomearo.gamecontrol.GameControl;
import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.objects.arena.ClipboardRegenableGameArena;
import ru.boomearo.gamecontrol.objects.states.IGameState;
import ru.boomearo.gamecontrol.objects.states.IRegenState;

/**
 * Задача, которая выполняет регенерацию арены, а затем снимаем флаг на ожидание регенерации.
 */
public class RegenTask implements Runnable {

    private final ClipboardRegenableGameArena arena;

    public RegenTask(ClipboardRegenableGameArena arena) {
        this.arena = arena;
    }

    @Override
    public void run() {
        String gameName = this.arena.getManager().getGameName();

        try {

            GameControl.getInstance().getLogger().info("Начинаю регенерацию арены '" + this.arena.getName() + "' в игре '" + gameName + "'");
            long start = System.currentTimeMillis();

            File schFile = new File(GameControl.getInstance().getDataFolder(), File.separator + "schematics" + File.separator + gameName.toLowerCase() + "_" + this.arena.getName().toLowerCase() + ".schem");

            if (!schFile.exists()) {
                throw new ConsoleGameException("Файл арены '" + this.arena.getName() + "' в игре '" + gameName + "' не найден!");
            }

            if (!schFile.isFile()) {
                throw new ConsoleGameException("Файл арены '" + this.arena.getName() + "' в игре '" + gameName + "' не является файлом!");
            }

            Clipboard cb;

            ClipboardFormat format = ClipboardFormats.findByFile(schFile);

            if (format == null) {
                throw new ConsoleGameException("Файл схемы арены '" + this.arena.getName() + "' игры '" + gameName + "' не найден!");
            }

            try (ClipboardReader reader = format.getReader(new FileInputStream(schFile))) {
                cb = reader.read();
            }

            if (cb == null) {
                throw new ConsoleGameException("Схема арены '" + this.arena.getName() + "' игры '" + gameName + "' является нулем!");
            }

            Location loc = this.arena.getOriginCenter();

            if (loc == null) {
                throw new ConsoleGameException("Центральная точка схемы арены '" + this.arena.getName() + "' игры '" + gameName + "' является нулем!");
            }

            World w = BukkitAdapter.adapt(this.arena.getWorld());

            if (w == null) {
                throw new ConsoleGameException("Мир арены '" + this.arena.getName() + "' игры '" + gameName + "' является нулем!");
            }

            //Если при операции появляется исключение, прерываем выполнение всего
            try (EditSession es = WorldEdit.getInstance().newEditSession(w)) {
                Operation op = new ClipboardHolder(cb)
                        .createPaste(es)
                        .to(BlockVector3.at(loc.getX(), loc.getY(), loc.getZ()))
                        .ignoreAirBlocks(false)
                        .copyEntities(false)
                        .copyBiomes(false)
                        .build();

                Operations.complete(op);
            }

            long end = System.currentTimeMillis();

            //Арена теперь считается восстановленной, поэтому снимаем глобальный статус о том что ее надо восстановить.
            GameControl.getInstance().getGameManager().setRegenGame(this.arena, false);

            GameControl.getInstance().getLogger().info("Регенерация арены '" + this.arena.getName() + "' в игре '" + gameName + "' успешно завершена за " + (end - start) + "мс.");
        }
        catch (Throwable e) {
            GameControl.getInstance().getLogger().warning("Произошла ошибка при регенерации арены '" + this.arena.getName() + "' в игре '" + gameName + "'");
            e.printStackTrace();
        }
        finally {
            //После регенерации арены, получаем ее состояние
            IGameState state = this.arena.getState();
            //Снимаем ожидание регенерации
            if (state instanceof IRegenState regenState) {
                regenState.setWaitingRegen(false);
            }
        }
    }

}
