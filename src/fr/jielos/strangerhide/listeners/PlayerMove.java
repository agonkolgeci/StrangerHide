package fr.jielos.strangerhide.listeners;

import fr.jielos.strangerhide.Main;
import fr.jielos.strangerhide.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Game game = Main.getInstance().getGame();
        final Player player = event.getPlayer();

        if(game.getGameState() == Game.GameState.PLAYING && game.getData().getPlayersCameras().containsKey(player)) {
            player.teleport(game.getData().getPlayersCameras().get(player));
        }
    }

}
