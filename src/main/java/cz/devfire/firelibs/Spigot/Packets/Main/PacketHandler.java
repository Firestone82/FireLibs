package cz.devfire.firelibs.Spigot.Packets.Main;

import cz.devfire.firelibs.Spigot.FireLibs;
import cz.devfire.firelibs.Spigot.Packets.Main.Events.PacketPlayInEvent;
import cz.devfire.firelibs.Spigot.Packets.Main.Events.PacketPlayOutEvent;
import cz.devfire.firelibs.Spigot.Packets.Main.Listeners.PacketHandlerListener;
import cz.devfire.firelibs.Spigot.Utils.Reflections.RefUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PacketHandler {
    private FireLibs plugin;

    public PacketHandler(FireLibs plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(new PacketHandlerListener(plugin,this),plugin);
    }

    public void injectPlayer(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) {
                try {
                    PacketPlayInEvent event = new PacketPlayInEvent(player,packet);
                    Bukkit.getPluginManager().callEvent(event);

                    if (event.isCancelled()) {
                        return;
                    }

                    super.channelRead(channelHandlerContext,packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) {
                try {
                    PacketPlayOutEvent event = new PacketPlayOutEvent(player,packet);
                    Bukkit.getPluginManager().callEvent(event);

                    if (event.isCancelled()) {
                        return;
                    }

                    super.write(channelHandlerContext,packet,channelPromise);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            Channel ch = ((Channel) RefUtils.getChannel(RefUtils.getNetworkManager(RefUtils.getPlayerConnection(player))));
            ch.pipeline().addBefore("packet_handler","FireLibs-"+ player.getName(),channelDuplexHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void injectPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            injectPlayer(player);
        }
    }

    public void extractPlayer(Player player) {
        Channel ch = ((Channel) RefUtils.getChannel(RefUtils.getNetworkManager(RefUtils.getPlayerConnection(player))));

        ch.eventLoop().submit(() -> {
            ch.pipeline().remove("FireLibs-"+ player.getName());
            return null;
        });
    }

    public void extractPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            extractPlayer(player);
        }
    }
}
