package plateau.engine.registery;

import plateau.engine.Plateau;
import plateau.engine.entity.Entity;
import plateau.engine.input.IKeyboard;
import plateau.engine.input.IMouse;
import plateau.engine.renderer.entity.RenderEntity;
import plateau.engine.world.World;

import java.util.ArrayList;
import java.util.HashMap;

public class GameRegistry {
    private static ArrayList<World> worldList = new ArrayList<World>();
    private static HashMap<Class, RenderEntity> entityRender = new HashMap<Class, RenderEntity>();

    public static void registerWorld(World world) {
        if (!worldList.contains(world)) {
            worldList.add(world);
        }
    }

    public static ArrayList<World> getWorldList() {
        return worldList;
    }

    /*

        public static World getWorld(World world) {
            for (World worlds : worldList) {
                if (worlds == world) {
                    return worlds;
                }
            }
            EntityPlayer player = Plateau.getPlayer();
            player.setWorld(0);
            return getWorld(player.getWorldID());
        }
        */
    public static void registerInput(IKeyboard keyboard) {
        Plateau.getInput().registerInput(keyboard);
    }

    public static void registerInput(IMouse mouse) {
        Plateau.getInput().registerInput(mouse);
    }

    public static void registerEntity(Class entity, RenderEntity renderEntity) {
        entityRender.put(entity, renderEntity);
    }

    public static RenderEntity getRender(Entity entity) {
        RenderEntity renderEntity = entityRender.get(entity.getClass());
        if (renderEntity == null) {
            System.out.println("Entity " + entity + "does not have a render");
            return null;
        }
        return renderEntity;
    }
}
