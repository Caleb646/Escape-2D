package gamePackage;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class StrikerEnemy extends Enemy {

    protected ArrayList<Node> currentPath = new ArrayList<Node>();
    protected float attackSpeed = 2.0f;
    protected int attackRange = 7;
    protected int vision = 50;

    protected float startX;
    protected float startY;
    protected int patrolDistance;
    protected float patrolSpeed = 1.0f;

    //patrolling 
    protected boolean left = false;
    protected boolean right = true;

    //animations
    protected Animater strikerHitAnim = new Animater(GameAssets.strikerHitAnim);
    protected Animater strikerAttackingAnim = new Animater(GameAssets.strikerAttackingAnim);


    public StrikerEnemy(GameHandler gameHandler, float x, float y, int w, int h) {
        super(gameHandler, x, y, w, h);
        startX = x;
        startY = y;
        patrolDistance = 100;
        jumpHeight = 20.0f;
        health = 5;

        strikerAttackingAnim.setInterval(1000);
        strikerHitAnim.setInterval(1000);
    }

    public void update() {        

        velocity.setX(0);
        

        if(canSee()) {

            moveTo();
        }

        else {
            patrol();
        }

        velocity.addY(gravity.getY());

        //need to be after the moveTo method
        strikerAttackingAnim.update();
        strikerHitAnim.update();

        move();

    }

    public void render(Graphics2D g2d) {
        
        g2d.drawImage(getCurrentAnimation(), (int) (pos.getX()-gameHandler.getCamera().getCameraX()),
        (int) (pos.getY()-gameHandler.getCamera().getCameraY()), spriteWidth, spriteHeight, null);

        if(currentPath != null) {
            gameHandler.getPathFinder().drawPath(g2d, currentPath);
        }
    }

    public BufferedImage getCurrentAnimation() {
        if(attacking)
            return strikerAttackingAnim.getCurrentAnimation();
        else if(hit) {
            setHit(false);//TODO need to find a better place for this and setAttack()
            return strikerHitAnim.getCurrentAnimation();
        }
        else  
            return GameAssets.strikerIdleImg;
    }

    public void patrol() {

        if(left) { 
            velocity.addX(-patrolSpeed);
            if(pos.getX() < startX-patrolDistance) {
                left = false;
                right = true;
            }           
        }
        else if(right) {
            velocity.addX(patrolSpeed);
            if(pos.getX() > startX+patrolDistance) {
                left = true;
                right = false;
            } 
        }
    }

    public void moveTo() {

        Node nextNode = getNextNode();
        if(nextNode == null)
            return;
        if(nextNode.getxPos()*Tiles.tileWidth > pos.getX()) {
            velocity.addX(attackSpeed);
        }
        if(nextNode.getxPos()*Tiles.tileWidth < pos.getX()) {
            velocity.addX(-attackSpeed);
        }
        if(nextNode.getyPos()*Tiles.tileHeight < pos.getY() - this.gameHandler.getPlayer().getHeight()) {//up
            jump();
        }
        if(pos.distance(gameHandler.getPlayer().getPos()) < attackRange) {
            if(canAttack()) {
                gameHandler.getPlayer().hurt();
            }
        }
    }

    public boolean canSee() {
        //System.out.println(" Distance: "+pos.distance(gameHandler.getPlayer().getPos()));
        if(pos.distance(gameHandler.getPlayer().getPos()) < vision) {
            xSpeed = attackSpeed;
            ArrayList<Node> path = gameHandler.getPathFinder().findPath((int) pos.getX(), (int) pos.getY(), (int) gameHandler.getPlayer().getX(), (int) gameHandler.getPlayer().getY());
            if(path != null)
                currentPath = path;
            return true;
        }
        currentPath.clear();
        return false;
    }

    public Node getNextNode() {
        if(currentPath.size() > 2) {
            return currentPath.remove(currentPath.size()-2);
        }
        else if(currentPath.size() > 1) {
            return currentPath.remove(0);
        }
        else {
            return null;
        }
    }
}