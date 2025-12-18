package infra.dispatcher;

import application.command.Command;
import application.usecase.registration.UserRegistrationUseCase;
import application.usecase.usecaseimpl.UseCase;

import java.util.Map;

public final class UseCaseDispatcher {

    private static volatile UseCaseDispatcher instance;
    private final Object lock = new Object();

    private final Map<Class<? extends Command>, UseCase<?, ?>> useCases;

    private UseCaseDispatcher(Map<Class<? extends Command>, UseCase<?, ?>> useCases) {
        this.useCases = useCases;
    }

    public static UseCaseDispatcher getInstance(Map<Class<? extends Command>, UseCase<?, ?>> useCases) {
        if (instance == null) {
            synchronized (UserRegistrationUseCase.class) {
                if (instance == null) {
                    instance = new UseCaseDispatcher(useCases);
                }
            }
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <C extends Command, R> R dispatch(C command) {
        UseCase<C, R> useCase = (UseCase<C, R>) useCases.get(command.getClass());
        if (useCase == null) {
            throw new IllegalStateException("No use case for " + command.getClass().getSimpleName());
        }
        synchronized (lock) {
            return useCase.execute(command);
        }
    }

}
