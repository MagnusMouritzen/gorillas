package unibanden.ist.gorillas.game;

public enum Layer {
    Nothing,
    Destructible,
    Indestructible,
    Player,
    Projectile,
    Trigger;

    public static int generateLayerMask(Layer... layers){
        int layerMask = 0;
        for (Layer layer : layers){
            layerMask += 1 << layer.ordinal();
        }
        return layerMask;
    }
}
