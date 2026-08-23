# DI Container School Task

This repository is a **school assignment**.

Your task is to implement a dependency injection container based on the `DIContainer` contract.  
The concrete implementation should be done in `DIContainerImpl`.

---
## Assignment Goal

Implement a working DI container that supports:

- singleton registrations
- transient registrations
- pre-created instance registrations
- constructor-based dependency resolution
- registration checks

When your implementation is correct, the unit tests must pass.

---

## Interface Methods Explained

The `DIContainer` interface defines these methods:

### `registerSingleton(Class<T> abstraction, Class<? extends T> implementation)`

Registers a mapping where one instance is created once and reused every time.

**Expected behavior:** resolving the same abstraction multiple times returns the same object.

Example:

```java
DIContainer container = new DIContainerImpl();
container.registerSingleton(Repository.class, RepositoryImpl.class);

Repository r1 = container.resolve(Repository.class);
Repository r2 = container.resolve(Repository.class);

System.out.println(r1 == r2); // true
```

### `registerTransient(Class<T> abstraction, Class<? extends T> implementation)`

Registers a mapping where a new instance is created on every resolve.

**Expected behavior:** resolving the same abstraction multiple times returns different objects.

Example:

```java
DIContainer container = new DIContainerImpl();
container.registerTransient(Repository.class, RepositoryImpl.class);

Repository r1 = container.resolve(Repository.class);
Repository r2 = container.resolve(Repository.class);

System.out.println(r1 == r2); // false
```

### `registerInstance(Class<T> abstraction, T instance)`

Registers an already-created object instance.

**Expected behavior:** the same provided object is always returned.

Example:

```java
DIContainer container = new DIContainerImpl();
Repository fixed = new RepositoryImpl();

container.registerInstance(Repository.class, fixed);

Repository resolved = container.resolve(Repository.class);
System.out.println(resolved == fixed); // true
```

### `resolve(Class<T> abstraction)`

Resolves an instance for the given type.

The container should:

1. find the mapping
2. pick a constructor of the implementation
3. resolve constructor parameters recursively
4. create and return the object

Example with constructor injection:

```java
interface Repository {}
class RepositoryImpl implements Repository {}

interface Service {}
class ServiceImpl implements Service {
    private final Repository repository;
    ServiceImpl(Repository repository) {
        this.repository = repository;
    }
}

DIContainer container = new DIContainerImpl();
container.registerSingleton(Repository.class, RepositoryImpl.class);
container.registerTransient(Service.class, ServiceImpl.class);

Service service = container.resolve(Service.class);
System.out.println(service != null); // true
```

### `isRegistered(Class<?> abstraction)`

Checks whether a type has been registered.

Example:

```java
DIContainer container = new DIContainerImpl();
System.out.println(container.isRegistered(Service.class)); // false

container.registerTransient(Service.class, ServiceImpl.class);
System.out.println(container.isRegistered(Service.class)); // true
```

---

## Testing in `main`

You can quickly test behavior by writing checks in a `main` method using `System.out.println(...)`, for example:

- singleton should print `true` for identity comparison
- transient should print `false` for identity comparison
- instance registration should return the same object
- nested dependency resolution should produce non-null objects

---

## Done Criteria

The task is complete only when:

1. `DIContainerImpl` correctly implements the interface behavior.
2. Unit tests pass.

If tests fail, the assignment is not complete yet.
