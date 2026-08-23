import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DIContainerImplTest {

    private DIContainer container = new DIContainerImpl();

    interface Repository {
    }

    static class RepositoryImpl implements Repository {
    }

    interface Service {
        Repository getRepository();
    }

    static class ServiceImpl implements Service {
        private final Repository repository;

        ServiceImpl(Repository repository) {
            this.repository = repository;
        }

        @Override
        public Repository getRepository() {
            return repository;
        }
    }

    @Test
    void registerSingleton() {
        container.registerSingleton(Repository.class, RepositoryImpl.class);

        Repository first = container.resolve(Repository.class);
        Repository second = container.resolve(Repository.class);

        assertNotNull(first);
        assertNotNull(second);
        assertSame(first, second);
    }

    @Test
    void registerTransient() {
        container.registerTransient(Repository.class, RepositoryImpl.class);

        Repository first = container.resolve(Repository.class);
        Repository second = container.resolve(Repository.class);

        assertNotSame(first, second);
    }

    @Test
    void registerInstance() {
        Repository repository = new RepositoryImpl();

        container.registerInstance(Repository.class, repository);

        assertSame(repository, container.resolve(Repository.class));
    }

    @Test
    void resolve() {
        container.registerSingleton(Repository.class, RepositoryImpl.class);
        container.registerTransient(Service.class, ServiceImpl.class);

        Service service = container.resolve(Service.class);

        assertNotNull(service);
        assertNotNull(service.getRepository());
        assertSame(container.resolve(Repository.class), service.getRepository());
    }

    @Test
    void isRegistered() {
        assertFalse(container.isRegistered(Repository.class));
        container.registerTransient(Repository.class, RepositoryImpl.class);
        assertTrue(container.isRegistered(Repository.class));
    }
}