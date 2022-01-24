package cz.devfire.firelibs.Spigot.Utils.Item;

import cz.devfire.firelibs.Spigot.Utils.Reflections.Ref;
import cz.devfire.firelibs.Spigot.Utils.Reflections.RefUtils;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.Set;

public class ItemNBT {
    private final Object itemStack;
    private final Object nbt;

    private static final Class<?> nbtClass;
    private static final Method getNBT, setNBT;
    private static final Method set, setString, setBoolean, setByte, setDouble, setFloat, setInt, setLong, setShort;
    private static final Method get, getString, getBoolean, getByte, getDouble, getFloat, getInt, getLong, getShort, getKeys, getTypeId, getCompound, getList;
    private static final Method remove, hasKey;

    static {
        nbtClass = Ref.nms("nbt.NBTTagCompound","NBTTagCompound");

        getNBT = Ref.method(Ref.nms("world.item.ItemStack","ItemStack"),"getTag");
        setNBT = Ref.method(Ref.nms("world.item.ItemStack","ItemStack"),"setTag",nbtClass);

        set = Ref.method(nbtClass,"set",String.class, Ref.nms("nbt.NBTBase","NBTBase"));
        setString = Ref.method(nbtClass,"setString",String.class, String.class);
        setBoolean = Ref.method(nbtClass,"setBoolean",String.class, boolean.class);
        setByte = Ref.method(nbtClass,"setByte",String.class, byte.class);
        setDouble = Ref.method(nbtClass,"setDouble",String.class, double.class);
        setFloat = Ref.method(nbtClass,"setFloat",String.class, float.class);
        setInt = Ref.method(nbtClass,"setInt",String.class, int.class);
        setLong = Ref.method(nbtClass,"setLong",String.class, long.class);
        setShort = Ref.method(nbtClass,"setShort",String.class, short.class);

        get = Ref.method(nbtClass,"get",String.class);
        getString = Ref.method(nbtClass,"getString",String.class);
        getBoolean = Ref.method(nbtClass,"getBoolean",String.class);
        getByte = Ref.method(nbtClass,"getByte",String.class);
        getDouble = Ref.method(nbtClass,"getDouble",String.class);
        getFloat = Ref.method(nbtClass,"getFloat",String.class);
        getInt = Ref.method(nbtClass,"getInt",String.class);
        getLong = Ref.method(nbtClass,"getLong",String.class);
        getShort = Ref.method(nbtClass,"getShort",String.class);
        getKeys = Ref.method(nbtClass,"getKeys");
        getTypeId = Ref.method(nbtClass,"getTypeId");
        getCompound = Ref.method(nbtClass,"getCompound",String.class);
        getList = Ref.method(nbtClass,"getList",String.class, int.class);

        remove = Ref.method(nbtClass,"remove",String.class);
        hasKey = Ref.method(nbtClass,"hasKey",String.class);
    }

    public ItemNBT(ItemStack itemStack) {
        this.itemStack = RefUtils.getCraftStack(itemStack);
        this.nbt = getNBT();
    }

    public Object getNBT() {
        return Ref.invoke(itemStack,getNBT);
    }

    public void setNBT() {
        Ref.invoke(itemStack,setNBT,nbt);
    }

    public ItemNBT set(String key, Object value) {
        Ref.invoke(nbt,set,key, value);
        setNBT();

        return this;
    }

    public ItemNBT setString(String key, String value) {
        Ref.invoke(nbt,setString,key, value);
        setNBT();

        return this;
    }

    public ItemNBT setBoolean(String key, boolean value) {
        Ref.invoke(nbt,setBoolean,key, value);
        setNBT();

        return this;
    }

    public ItemNBT setByte(String key, byte value) {
        Ref.invoke(nbt,setByte,key, value);
        setNBT();

        return this;
    }

    public ItemNBT setDouble(String key, double value) {
        Ref.invoke(nbt,setDouble,key, value);
        setNBT();

        return this;
    }

    public ItemNBT setFloat(String key, float value) {
        Ref.invoke(nbt,setFloat,key, value);
        setNBT();

        return this;
    }

    public ItemNBT setInt(String key, int value) {
        Ref.invoke(nbt,setInt,key, value);
        setNBT();

        return this;
    }

    public ItemNBT setLong(String key, long value) {
        Ref.invoke(nbt,setLong,key, value);
        setNBT();

        return this;
    }

    public ItemNBT setShort(String key, short value) {
        Ref.invoke(nbt,setShort,key, value);
        setNBT();

        return this;
    }

    public Object get(String key) {
        return Ref.invoke(nbt,get,key);
    }

    public String getString(String key) {
        return (String) Ref.invoke(nbt,getString,key);
    }

    public boolean getBoolean(String key) {
        return (boolean) Ref.invoke(nbt,getBoolean,key);
    }

    public byte getByte(String key) {
        return (byte) Ref.invoke(nbt,getByte,key);
    }

    public double getDouble(String key) {
        return (double) Ref.invoke(nbt,getDouble,key);
    }

    public float getFloat(String key) {
        return (float) Ref.invoke(nbt,getFloat,key);
    }

    public int getInt(String key) {
        return (int) Ref.invoke(nbt,getInt,key);
    }

    public long getLong(String key) {
        return (long) Ref.invoke(nbt,getLong,key);
    }

    public short getShort(String key) {
        return (short) Ref.invoke(nbt,getShort,key);
    }

    public Object getKeys() {
        return Ref.invoke(nbt,getKeys);
    }

    public Object getTypeId() {
        return Ref.invoke(nbt,getTypeId);
    }

    public Object getCompound(String key) {
        return Ref.invoke(nbt,getCompound,key);
    }

    public Object getList(String key) {
        return null; // TODO:
    }

    public ItemNBT remove(String path) {
        Ref.invoke(nbt, remove,path);
        setNBT();

        return this;
    }

    public boolean hasKey(String path) {
        return (boolean) Ref.invoke(nbt,hasKey,path);
    }

    public boolean hasKeys() {
        return !((Set<String>) getKeys()).isEmpty();
    }

}
