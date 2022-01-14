package gamePackage;
import java.util.ArrayList;
import java.awt.Graphics2D;

public class ItemManager {
    
    //all items
    private ArrayList<Item> currentItems;

    //game handler
    private GameHandler gameHandler;

    public ItemManager(GameHandler gm) {
        this.gameHandler = gm;
        this.gameHandler.setItemManager(this);
        currentItems = new ArrayList<Item>();
    }

    public void update() {
        for(int i = currentItems.size()-1; i>-1; i--) {
            Item item = currentItems.get(i);

            if(item.isPickedUp()) {
                currentItems.remove(i);
                continue;
            }
            item.update();
        }
    }

    public void render(Graphics2D g2d) {
        for(Item i : currentItems) {
            i.render(g2d);
        }
    }

    public void addItem(Item it) {
        this.currentItems.add(it);
    }

    public void clearAllItems() {
        currentItems.clear();
    }
}