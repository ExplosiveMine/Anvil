package me.explosivemine.anvil.version;

import me.explosivemine.anvil.version.abstraction.VersionWrapper;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.protocol.game.PacketPlayOutCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerAnvil;
import net.minecraft.world.inventory.Containers;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Wrapper1_17_R1 implements VersionWrapper {
    private int getRealNextContainerId(Player player) {
        return toNMS(player).nextContainerCounter();
    }

    @Override
    public int getNextContainerId(Player player, Object container) {
        return ((AnvilContainer) container).getContainerId();
    }

    @Override
    public void handleInventoryCloseEvent(Player player) {
        CraftEventFactory.handleInventoryCloseEvent(toNMS(player));
    }

    @Override
    public void sendPacketOpenWindow(Player player, int containerId, String guiTitle) {
        toNMS(player).b.sendPacket(new PacketPlayOutOpenWindow(containerId, Containers.h, new ChatMessage(guiTitle)));
    }

    @Override
    public void sendPacketCloseWindow(Player player, int containerId) {
        toNMS(player).b.sendPacket(new PacketPlayOutCloseWindow(containerId));
    }

    @Override
    public void setActiveContainerDefault(Player player) {
        toNMS(player).bV = (toNMS(player)).bU;
    }

    @Override
    public void setActiveContainer(Player player, Object container) {
        toNMS(player).bV = (Container) container;
    }

    @Override
    public void setActiveContainerId(Object container, int containerId) { }

    @Override
    public void addActiveContainerSlotListener(Object container, Player player) {
        toNMS(player).initMenu((Container) container);
    }

    @Override
    public Inventory toBukkitInventory(Object container) {
        return ((Container) container).getBukkitView().getTopInventory();
    }

    @Override
    public Object newContainerAnvil(Player player, String guiTitle) {
        return new AnvilContainer(player, guiTitle);
    }

    private EntityPlayer toNMS(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    private class AnvilContainer extends ContainerAnvil {
        public AnvilContainer(Player player, String guiTitle) {
            super(getRealNextContainerId(player), ((CraftPlayer) player).getHandle().getInventory(),
                    ContainerAccess.at(((CraftWorld) player.getWorld()).getHandle(), new BlockPosition(0, 0, 0)));
            this.checkReachable = false;
            setTitle(new ChatMessage(guiTitle));
        }

        @Override
        protected void a(EntityHuman entityhuman, IInventory iinventory) { }

        public int getContainerId() {
            return this.j;
        }
    }
}
