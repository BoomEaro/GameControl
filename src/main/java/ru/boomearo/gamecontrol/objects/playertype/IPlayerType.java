package ru.boomearo.gamecontrol.objects.playertype;

import ru.boomearo.gamecontrol.objects.IGamePlayer;

public interface IPlayerType<T extends IGamePlayer> {

    public void preparePlayer(T player);

}
