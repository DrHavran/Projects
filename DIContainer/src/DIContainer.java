/**
 * A simple Dependency Injection (DI) container responsible for managing
 * object creation and dependency resolution.
 *
 * <p>This container supports:
 * <ul>
 *     <li>Singleton registrations – one shared instance</li>
 *     <li>Transient registrations – new instance per request</li>
 *     <li>Pre-created instance registrations</li>
 * </ul>
 *
 * <p>The container resolves dependencies using constructor injection.
 * When resolving a type, it will recursively resolve all constructor parameters.
 */
public interface DIContainer {
    /**
     * Registers a type mapping as a singleton.
     *
     * <p>The container will create only one instance of the given implementation
     * and reuse it for all future {@link #resolve(Class)} calls.
     *
     * @param abstraction the abstraction (usually an interface or base class)
     * @param implementation the concrete class to instantiate
     * @param <T> the type of the abstraction
     */
    <T> void registerSingleton(Class<T> abstraction, Class<? extends T> implementation);

    /**
     * Registers a type mapping as transient.
     *
     * <p>A new instance of the implementation will be created each time
     * {@link #resolve(Class)} is called.
     *
     * @param abstraction the abstraction (usually an interface or base class)
     * @param implementation the concrete class to instantiate
     * @param <T> the type of the abstraction
     */
    <T> void registerTransient(Class<T> abstraction, Class<? extends T> implementation);

    /**
     * Registers an already created instance.
     *
     * <p>The container will always return this instance when resolving
     * the given abstraction.
     *
     * @param abstraction the abstraction (usually an interface or base class)
     * @param instance the instance to register
     * @param <T> the type of the abstraction
     */
    <T> void registerInstance(Class<T> abstraction, T instance);

    /**
     * Resolves an instance of the given abstraction.
     *
     * <p>The container will:
     * <ol>
     *     <li>Find the corresponding implementation</li>
     *     <li>Select a constructor</li>
     *     <li>Resolve all constructor dependencies recursively</li>
     *     <li>Create and return the instance</li>
     * </ol>
     *
     * @param abstraction the type to resolve
     * @param <T> the type of the abstraction
     * @return an instance of the requested type
     *
     * @throws RuntimeException if the type is not registered or cannot be instantiated
     */
    <T> T resolve(Class<T> abstraction);

    /**
     * Checks whether the given abstraction is registered in the container.
     *
     * @param abstraction the type to check
     * @return true if the type is registered, false otherwise
     */
    boolean isRegistered(Class<?> abstraction);
}