package gamePackage;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.HashSet;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class PathFinder {

    private GameHandler gameHandler;

    private Node[][] nodes;

    public PathFinder(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        this.gameHandler.setPathFinder(this);
    }

    public void createNodes() {
        int gW = this.gameHandler.getWorldHandler().getGameWidth();
        int gH = this.gameHandler.getWorldHandler().getGameHeight();
        nodes = new Node[gW][gH];

        for(int y = 0; y<gH; y++) {
            for(int x = 0; x<gW; x++) {
                nodes[x][y] = new Node(this.gameHandler.getWorldHandler().getTileType(x, y).getId(), x, y);//node has same id as tile
            }
        }

        for(int y = 0; y<gH; y++) {
            for(int x = 0; x<gW; x++) {
                int xSub = x-1 < 0 ? x : x-1;
                int ySub = y-1 < 0 ? y : y-1;
                int xPlus = x+1 > gW-1 ? x : x+1;
                int yPlus = y+1 > gH-1 ? y : y+1;

                Node n = nodes[x][y];
                Node n1 = nodes[xPlus][y];
                Node n2 = nodes[x][yPlus];
                Node n3 = nodes[xSub][y]; 
                Node n4 = nodes[x][ySub];
                
                n.addConnection(n1);
                n.addConnection(n2);
                n.addConnection(n3);
                n.addConnection(n4);
            }
        }
    }

    public Hashtable<Node, Integer> setupHashTable() {

        

        int gW = this.gameHandler.getWorldHandler().getGameWidth();
        int gH = this.gameHandler.getWorldHandler().getGameHeight();
        Hashtable<Node, Integer> hashTable = new Hashtable<Node, Integer>();
        //only search the nodes within the screen not the whole map.
        int xStart = (int) Math.max(0, gameHandler.getCamera().getCameraX() / Tiles.tileWidth);
        int xEnd = (int) Math.min(gW, (gameHandler.getCamera().getCameraX() + gameHandler.getGameWidth()) / Tiles.tileWidth + 1);
        int yStart = (int) Math.max(0, gameHandler.getCamera().getCameraY() / Tiles.tileHeight);
        int yEnd = (int) Math.min(gH, (gameHandler.getCamera().getCameraY() + gameHandler.getGameHeight()) / Tiles.tileHeight + 1);
        for(int y = yStart; y<yEnd; y++) {
            for(int x = xStart; x<xEnd; x++) {
                Node n = nodes[x][y];
                if(Tiles.isSolid(n.getId()))//com player cant see solid blocks
                    continue;
                hashTable.put(n, 5000);
            }

        }

        return hashTable;
        
    }

    public int heuristic(int startX, int startY, int targetX, int targetY) {

        return Math.abs((startX-targetX)) + Math.abs((startY-targetY));
    }

    public ArrayList<Node> buildPath(Hashtable<Node, Node> cameFrom, Node currentNode) {
        ArrayList<Node> path = new ArrayList<Node>();
        path.add(currentNode);
        while(cameFrom.containsKey(currentNode)) {
            currentNode = cameFrom.get(currentNode);
            path.add(currentNode);
        }
        return path;

    }

    public ArrayList<Node> findPath(int startX, int startY, int targetX, int targetY) {
        
        /* DISCLAIMER: This is the A star path finding algorithm with some modifications. I used the pseudo code on wikipedia
        to make it. */

        int count = 0;
        int normX = startX / Tiles.tileWidth;
        int normY = startY / Tiles.tileHeight;
        int normTargetX = targetX / Tiles.tileWidth;
        int normTargetY = targetY / Tiles.tileHeight;

        Node startNode = nodes[normX][normY];
        Node targetNode = nodes[normTargetX][normTargetY];

        Data data = new Data(0, count, startNode);
        PriorityQueue<Data> openSet = new PriorityQueue<Data>();
        openSet.add(data);

        Hashtable<Node, Integer> gScore = setupHashTable();
        gScore.put(startNode, 0);
        Hashtable<Node, Integer> fScore = setupHashTable();
        fScore.put(startNode, heuristic(normX, normY, normTargetX, normTargetY));

        Hashtable<Node, Node> cameFrom = new Hashtable<Node, Node>();

        HashSet<Node> openSetHash = new HashSet<Node>();
        openSetHash.add(startNode);

        ArrayList<Node> path;

        while(!openSet.isEmpty()) {

            Node currentNode = openSet.remove().node;
            openSetHash.remove(currentNode);

            if(currentNode.equals(targetNode)) {
                path = buildPath(cameFrom, currentNode);
                return path;
            }

            for(Node n : currentNode.getConnections()) {

                int tempScore = gScore.get(currentNode) + 1;
                if(gScore.get(n) == null)
                    continue;
                if(tempScore < gScore.get(n)) {
                    cameFrom.put(n, currentNode);
                    gScore.put(n, tempScore);
                    fScore.put(n, tempScore +(heuristic(n.getxPos(), n.getyPos(), normTargetX, normTargetY)));
                    if(!openSetHash.contains(n)){
                        count += 1;
                        Data newData = new Data(fScore.get(n), count, n);
                        openSet.add(newData);
                        openSetHash.add(n);
                    }
                }
            }
        }
        return null; 
    }

    public void drawPath(Graphics2D g2d, ArrayList<Node> path) {

        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(2.0f));
        int[] xPoints = new int[path.size()];
        int[] yPoints = new int[path.size()];
        for(int i = 0; i<path.size()-1; i+=2) {
            Node n1 = path.get(i);
            Node n2 = path.get(i+1);

            int x1 = xPoints[i] = ((int) ((n1.getxPos()*Tiles.tileWidth)-gameHandler.getCamera().getCameraX()));
            int y1 = yPoints[i] = ((int) (((n1.getyPos()*Tiles.tileHeight)-gameHandler.getCamera().getCameraY())+this.gameHandler.getPlayer().getHeight()));
            int x2 = xPoints[i+1] = ((int) ((n2.getxPos()*Tiles.tileWidth)-gameHandler.getCamera().getCameraX()));
            int y2 = yPoints[i+1] = ((int) (((n2.getyPos()*Tiles.tileHeight)-gameHandler.getCamera().getCameraY())+this.gameHandler.getPlayer().getHeight()));
            g2d.drawLine(x1, y1, x2, y2);
        }
        //g2d.drawPolyline(xPoints, yPoints, xPoints.length);
    }

    public void printGraph() {
        int gW = this.gameHandler.getWorldHandler().getGameWidth();
        int gH = this.gameHandler.getWorldHandler().getGameHeight();
        String line = "";

        for(int y = 0; y<gH; y++) {
            line += " r: "+y+"  ";
            for(int x = 0; x<gW; x++) {
                line += " "+nodes[x][y].getId()+" ";
            }
            System.out.println(line);
            line = "";
        }
    }
}

class Node {

    private final int id;
    private ArrayList<Node> connections;

    private int xPos;// normalized position
    private int yPos;

    public Node(int id, int x, int y) {
        this.id = id;
        this.xPos = x;
        this.yPos = y;
        connections = new ArrayList<Node>();
    }

    public void addConnection(Node n) {
        if(n != null && !n.equals(this) && !connections.contains(n)) {
            connections.add(n);
        }
    }
    
    //getters and setters

    public int getxPos() {
        return this.xPos;
    }

    public int getyPos() {
        return this.yPos;
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<Node> getConnections() {
        return this.connections;
    }
}


class Data implements Comparable<Data> {
    public int fScore;
    public int count;
    public Node node;

    public Data(int zc, int c, Node node) {
        this.fScore = zc;
        this.count = c;
        this.node = node;
    }

    @Override
    public int compareTo(Data o) {
        //compares two elements by their fscore or estimated score
        if(fScore > o.fScore)
            return 1;
        else if(fScore < o.fScore) {
            return -1;
        }
        else {
            return 0;
        }    
    }
}
