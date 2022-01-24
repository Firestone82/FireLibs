package cz.devfire.firelibs.Spigot.Utils.Reflections;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Ref {
    private Ref() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // --

    public static void set(Object main, Field field, Object object) {
        try {
            field.setAccessible(true);
            field.set(main, object);
        } catch(Exception e) { /* */ }
    }

    public static void set(Object main, String fieldString, Object object) {
        try {
            set(main,Ref.field(main.getClass(),fieldString), object);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // --

    public static Object get(Object main, Field field) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            return field.get(main);
        } catch(Exception e) {
            return null;
        }
    }

    public static Object get(Object main, String fieldString) {
        try {
            return Ref.field(main.getClass(),fieldString).get(main);
        } catch(Exception | NoSuchFieldError es) {
            return null;
        }
    }

    public static Object getNulled(Field field) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            return field.get(null);
        } catch(Exception e) {
            return null;
        }
    }

    public static Object getNulled(Class<?> classs, String fieldString) {
        try {
            return Ref.field(classs,fieldString).get(null);
        } catch(Exception e) {
            return null;
        }
    }

    // --

    public static Object cast(Class<?> classs, Object object) {
        try {
            return classs.cast(object);
        } catch(Exception e) {
            return null;
        }
    }

    public static Object handle(Object object) {
        try {
            return Ref.invoke(object,Ref.method(object.getClass(),"getHandle"));
        } catch(Exception e) {
            return null;
        }
    }

    public static Object invoke(Object main, Method method, Object... bricks) {
        try {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }

            return method.invoke(main,bricks);
        } catch(Exception | NoSuchMethodError e) {
            return null;
        }
    }

    public static Object invoke(Object main, String method, Object... bricks) {
        try {
            return Ref.getMethod(main.getClass(),method,bricks).invoke(main,bricks);
        } catch(Exception e) {
            return null;
        }
    }

    public static Object invokeNulled(Class<?> classInMethod, String method, Object... bricks) {
        try {
            return Ref.getMethod(classInMethod,method,bricks).invoke(null,bricks);
        } catch(Exception e) {
            return null;
        }
    }

    public static Object invokeNulled(Method method, Object... bricks) {
        try {
            return method.invoke(null,bricks);
        } catch(Exception es) {
            return null;
        }
    }

    // --

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch(Exception e) {
            return null;
        }
    }

    public static Class<?>[] getClasses(Class<?> classs) {
        try {
            return classs.getClasses();
        } catch(Exception e) {
            return new Class<?>[0];
        }
    }

    public static Class<?>[] getDeclaredClasses(Class<?> classs) {
        try {
            return classs.getDeclaredClasses();
        } catch(Exception e) {
            return new Class<?>[0];
        }
    }

    // --

    public static Field field(Class<?> main, String name) {
        try {
            Field f = main.getField(name);

            if (f != null) {
                f.setAccessible(true);
            }

            return f;
        } catch(Exception es) {
            try {
                Field f = main.getDeclaredField(name);
                f.setAccessible(true);

                return f;
            } catch(Exception e) {
                try {
                    if (main.getSuperclass() != null) {
                        return Ref.field(main.getSuperclass(),name);
                    }
                } catch(Exception er) { /* */ }

                return null;
            }
        }
    }

    public static Field[] getFields(Class<?> classs) {
        try {
            return classs.getFields();
        } catch(Exception e) {
            return null;
        }
    }

    public static Field[] getDeclaredFields(Class<?> classs) {
        try {
            return classs.getDeclaredFields();
        } catch(Exception e) {
            return new Field[0];
        }
    }

    // --

    public static Method method(Class<?> main, String name, Class<?>... bricks) {
        try {
            Method f = main.getMethod(name,bricks);

            f.setAccessible(true);
            return f;
        } catch(Exception e) {
            try {
                Method f = main.getDeclaredMethod(name,bricks);
                f.setAccessible(true);
                return f;
            } catch(Exception e2) {
                try {
                    if (main.getSuperclass() != null) {
                        return method(main.getSuperclass(),name,bricks);
                    }
                } catch(Exception e3) { /* */ }

                return null;
            }
        }
    }

    public static Boolean existMethod(Class<?> classs, String name) {
        for (Method method : Ref.getMethods(classs)) {
            if (method.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    public static Method getMethod(Object object, String name, Object... bricks) {
        return Ref.getMethod(object.getClass(),name,bricks);
    }

    public static Method getMethod(Class<?> classs, String name, Object... bricks) {
        Method a = null;
        Class<?>[] param = new Class<?>[bricks.length];
        int i = 0;

        for (Object o : bricks) {
            if (o != null) {
                param[++i] = o instanceof Class ? (Class<?>) o : o.getClass();
            }
        }

        if (bricks.length == 0) {
            for (Method m : getMethods(classs)) {
                if (m.getParameterCount() == 0 && m.getName().equals(name)) {
                    a = m;
                    break;
                }
            }

            if (a == null) {
                for (Method m : Ref.getDeclaredMethods(classs)) {
                    if (m.getParameterCount() == 0 && m.getName().equals(name)) {
                        a = m;
                        break;
                    }
                }
            }
        } else {
            for (Method m : Ref.getMethods(classs)) {
                if (m.getParameterTypes().equals(param) && m.getName().equals(name)) {
                    a = m;
                    break;
                }
            }

            if (a == null) {
                for (Method m : getDeclaredMethods(classs)) {
                    if (m.getParameterTypes().equals(param) && m.getName().equals(name)) {
                        a = m;
                        break;
                    }
                }
            }
        }

        if (a != null) {
            a.setAccessible(true);
        }

        return a;
    }

    public static Method getMethodByName(Class<?> classs, String name) {
        Method a = null;

        for (Method m : getMethods(classs)) {
            if (m.getName().equals(name)) {
                a = m;
                break;
            }
        }

        if (a == null) {
            for (Method m : Ref.getDeclaredMethods(classs)) {
                if (m.getName().equals(name)) {
                    a = m;
                    break;
                }
            }
        }

        if ( a!= null) {
            a.setAccessible(true);
        }

        return a;
    }

    public static Method[] getMethods(Class<?> classs) {
        try {
            return classs.getMethods();
        } catch(Exception e) {
            return new Method[0];
        }
    }

    public static Method[] getDeclaredMethods(Class<?> classs) {
        try {
            return classs.getDeclaredMethods();
        } catch(Exception e) {
            return null;
        }
    }

    // ---

    public static Constructor<?> constructor(Class<?> main, Class<?>... bricks) {
        try {
            return main.getConstructor(bricks);
        } catch(Exception e) {
            try {
                return main.getDeclaredConstructor(bricks);
            } catch(Exception e2) {
                return null;
            }
        }
    }

    public static Constructor<?>[] getConstructors(Class<?> main) {
        try {
            return main.getConstructors();
        } catch(Exception e) {
            return null;
        }
    }

    public static Constructor<?>[] getDeclaredConstructors(Class<?> main) {
        try {
            return main.getDeclaredConstructors();
        } catch(Exception e) {
            return null;
        }
    }

    public static Constructor<?> findConstructor(Class<?> classs, Object... bricks) {
        Constructor<?> a = null;
        Class<?>[] param = new Class<?>[bricks.length];
        int i = 0;

        for (Object o : bricks) {
            if (o != null) {
                param[++i] = o instanceof Class ? (Class<?>) o : o.getClass();
            }
        }

        if (bricks.length == 0) {
            for (Constructor<?> m : Ref.getConstructors(classs)) {
                if (m.getParameterCount() == 0) {
                    a = m;
                    break;
                }
            }

            if (a == null) {
                for (Constructor<?> m : Ref.getDeclaredConstructors(classs)) {
                    if (m.getParameterCount() == 0) {
                        a = m;
                        break;
                    }
                }
            }
        } else {
            for (Constructor<?> m : Ref.getConstructors(classs)) {
                if (m.getParameterTypes().equals(param)) {
                    a = m;
                    break;
                }
            }

            if (a == null) {
                for (Constructor<?> m : Ref.getDeclaredConstructors(classs)) {
                    if (m.getParameterTypes().equals(param)) {
                        a = m;
                        break;
                    }
                }
            }
        }

        if (a != null) {
            a.setAccessible(true);
        }

        return a;
    }

    // ---

    public static Class<?> nms(String name) {
        try {
            return Class.forName("net.minecraft.server."+ version() +"."+ name);
        } catch(Exception e) {
            return null;
        }
    }

    public static Class<?> nms(String name, String old) {
        try {
            if (name != null) {
                return Class.forName("net.minecraft." + name);
            } else {
                return Class.forName("net.minecraft.server." + version() + "." + old);
            }
        } catch (Exception e) {
            try {
                return Class.forName("net.minecraft.server." + version() + "." + old);
            } catch (Exception e1) {
                return null;
            }
        }
    }

    public static Class<?> craft(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit."+ version() +"."+ name);
        } catch(Exception e) {
            return null;
        }
    }

    // ---

    public static Object newInstance(Constructor<?> constructor, Object... bricks) {
        try {
            constructor.setAccessible(true);
            return constructor.newInstance(bricks);
        } catch(Exception es) {
            return null;
        }
    }

    public static Object newInstanceNms(String className, Object... bricks) {
        return newInstance(findConstructor(nms(className),bricks),bricks);
    }

    public static Object newInstanceCraft(String className, Object... bricks) {
        return newInstance(findConstructor(craft(className),bricks),bricks);
    }

    public static Object newInstanceByClass(String className, Object... bricks) {
        return newInstance(findConstructor(getClass(className),bricks),bricks);
    }

    // ---

    private static String version() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}