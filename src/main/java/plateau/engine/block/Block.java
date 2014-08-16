package plateau.engine.block;

import plateau.engine.renderer.Icon;

import java.util.ArrayList;

public class Block {

    private Icon blockIcon;

    public ArrayList<Block>  blockList= new ArrayList<Block>();

    public Icon getIcon(int side) {
        return this.blockIcon;
    }

    public boolean isSideSolid(int side) {
        return true;
    }
}
