package ru.boomearo.gamecontrol.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;

import ru.boomearo.gamecontrol.GameControl;
import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.objects.arena.ClipboardRegenableGameArena;

public class RegenTask implements Runnable {

    private final ClipboardRegenableGameArena arena;
    private final Runnable afterTask;
    
    public RegenTask(ClipboardRegenableGameArena arena, Runnable afterTask) {
        this.arena = arena;
        this.afterTask = afterTask;
    }
    
    @Override
    public void run() {
        String gameName = this.arena.getManager().getGameName();
        
        try {
            
            GameControl.getInstance().getLogger().info("Начинаю регенерацию арены '" + this.arena.getName() + "' в игре '" + gameName + "'");
            long start = System.currentTimeMillis();
            
            Clipboard clipboard = arena.getClipboard();
            if (clipboard == null) {
                throw new ConsoleGameException("Схема арены '" + this.arena.getName() + "' игры '" + gameName + "' является нулем!");
            }

            Location loc = this.arena.getOriginCenter();

            if (loc == null) {
                throw new ConsoleGameException("Центральная точка схемы арены '" + this.arena.getName() + "' игры '" + gameName + "' является нулем!");
            }

            ClipboardHolder ch = new ClipboardHolder(clipboard);

            World w = FaweAPI.getWorld(this.arena.getWorld().getName());
            
            try (EditSession es = new EditSessionBuilder(w).build()) {
                Operation op = ch.createPaste(es)
                        .to(BlockVector3.at(loc.getX(), loc.getY(), loc.getZ()))
                        .ignoreAirBlocks(false)
                        .copyEntities(false)
                        .copyBiomes(false)
                        .build();

                Operations.complete(op);
            }
            
            long end = System.currentTimeMillis();
            GameControl.getInstance().getLogger().info("Регенерация арены '" + this.arena.getName() + "' в игре '" + gameName + "' успешно завершена за " + (end - start) + "мс.");
        }
        catch (Throwable e) {
            GameControl.getInstance().getLogger().warning("Произошла ошибка при регенерации арены '" + this.arena.getName() + "' в игре '" + gameName + "'");
            e.printStackTrace();
        }
        finally {
            //Как только регенерация произойдет, не важно, была ошибка или нет, выполняем действие после, а именно в ОСНОВНОМ потоке.
            if (this.afterTask != null) {
                Bukkit.getScheduler().runTask(GameControl.getInstance(), this.afterTask);
            }
        }
    }

}
