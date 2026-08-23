import java.lang.reflect.Constructor;
import java.util.HashMap;

public class DIContainerImpl implements DIContainer {
    private final HashMap<Class<?>, Registration> objects = new HashMap<>();

    @Override
    public <T> void registerSingleton(Class<T> abstraction, Class<? extends T> implementation) {
        objects.put(abstraction, new Registration(implementation, ClassType.SINGLETON));
    }

    @Override
    public <T> void registerTransient(Class<T> abstraction, Class<? extends T> implementation) {
        objects.put(abstraction, new Registration(implementation, ClassType.TRANSIENT));
    }

    @Override
    public <T> void registerInstance(Class<T> abstraction, T instance) {
        Registration registration = new Registration(ClassType.INSTANCE);
        registration.setInstance(instance);
        objects.put(abstraction, registration);
    }

    @Override
    public <T> T resolve(Class<T> abstraction) {
        if (!isRegistered(abstraction)) { throw new RuntimeException("Class isn't registered"); }
        Registration registration = objects.get(abstraction);

        if(registration.getType() == ClassType.SINGLETON) {
            if(registration.getInstance() == null){
                Object instance = createInstance(registration);
                registration.setInstance(instance);
            }
            return (T) registration.getInstance();
        }
        else if (registration.getType() == ClassType.TRANSIENT) {
            return (T) createInstance(registration);
        }
        else if (registration.getType() == ClassType.INSTANCE) {
            return (T) registration.getInstance();
        }

        throw new RuntimeException("Class type is not recognized");
    }

    @Override
    public boolean isRegistered(Class<?> abstraction) {
        return objects.containsKey(abstraction);
    }

    private Object createInstance(Registration registration) {
        try{
            Constructor<?> constructor = registration.getClassChild().getDeclaredConstructors()[0];
            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] args = new Object[paramTypes.length];

            for (int i = 0; i < paramTypes.length; i++) {
                args[i] = resolve(paramTypes[i]);
            }
            return constructor.newInstance(args);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
