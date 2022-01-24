package cz.devfire.firelibs.Spigot.Utils.Vectors;

import cz.devfire.firelibs.Spigot.Utils.Reflections.RefUtils;

public class Vector3f implements Cloneable {
    private float x;
    private float y;
    private float z;

    public Vector3f() {
        this(0,0,0);
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3f ZERO = new Vector3f(0,0,0);

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public void setZ(Float z) {
        this.z = z;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public Float getZ() {
        return z;
    }

    public Object getNMS() {
        return RefUtils.getVector3f(x,y,z);
    }

    public Vector3f add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;

        return this;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
