package gamePackage;

public class Camera {

    private float CameraX, CameraY;
    private GameHandler gameHandler;

    public Camera(GameHandler h, float CX, float CY) {
        this.gameHandler = h;
        this.gameHandler.setCamera(this);
        this.CameraX = CX;
        this.CameraY = CY;

    }

    public void balance() {
        //keeps the camera from showing past the frame
        if(this.CameraX < 0) {
            this.CameraX = 0;
        }

        if(this.CameraY < 0) {
            this.CameraY = 0;
        }
    }

    public void center(Player p) {
        //offsets drawing positions based on the players position
        this.CameraX = p.getX() - gameHandler.getGameWidth() * 0.5f + p.getWidth() * 0.5f;
        this.CameraY = p.getY() - gameHandler.getGameHeight() * 0.5f + p.getHeight() * 0.5f;
        balance();
    }

    //getters and setters

    public float getCameraX() {
        return this.CameraX;
    }

    public float getCameraY() {
        return this.CameraY;
    }
}