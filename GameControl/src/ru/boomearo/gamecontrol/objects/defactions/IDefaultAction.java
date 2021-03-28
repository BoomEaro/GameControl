package ru.boomearo.gamecontrol.objects.defactions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

//Действия по умолчанию которые должны быть зареганы любыи плагином для своих нужд
public interface IDefaultAction {

    public Location getDefaultSpawnLocation();
    
    public void performDefaultLeaveAction(Player pl);
}
