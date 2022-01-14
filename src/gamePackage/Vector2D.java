package gamePackage;

public class Vector2D {
    private float x;
    private float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Vector2D v) {
        return Math.sqrt((Math.pow(getX()-v.getX(),2))/Tiles.tileWidth + (Math.pow(getY()-v.getY(),2))/Tiles.tileHeight);
    }

    public double distance(float x, float y) {
        return Math.sqrt((x*x + y*y));
    }

    public Vector2D normalize(Vector2D V) {
        float normX = V.getX() - getX();
        float normY = V.getY() - getY();
        float len = (float) distance(normX, normY);
        return new Vector2D(normX/len, normY/len); //unit vector
    }

    public Vector2D multiple(int scale) {
        float x = this.getX() * scale;
        float y = this.getY() * scale;
        return new Vector2D(x, y);
    }

    public void addX(float x) {
        this.x += x;
    }

    public void addY(float y) {
        this.y += y;
    }

    //getters and setters

    public float getLength(Vector2D V) {
        float vx = V.getX();
        float vy = V.getY();
        return (float) Math.sqrt((vx * vx + vy * vy));
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setXandY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String toString() {
        return "("+x+", "+y+")";
    }
}