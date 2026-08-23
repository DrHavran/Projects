public class Registration {
    private Class<?> classChild;
    private Object instance;
    private ClassType type;

    public Registration(Class<?> classChild, ClassType type) {
        this.classChild = classChild;
        this.type = type;
    }

    public Registration(ClassType type) {
        this.type = type;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }
    public ClassType getType() {
        return type;
    }
    public Object getInstance() {
        return instance;
    }
    public Class<?> getClassChild() {
        return classChild;
    }
}