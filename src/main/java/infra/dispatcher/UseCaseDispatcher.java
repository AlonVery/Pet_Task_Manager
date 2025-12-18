package infra.dispatcher;

import application.command.Command;
import application.usecase.usecaseimpl.UseCase;

import java.util.Map;

public final class UseCaseDispatcher {
    private static volatile UseCaseDispatcher instance;

    private final Map<Class<? extends Command>, UseCase<?, ?>> commandUseCaseMap;

    private UseCaseDispatcher(Map<Class<? extends Command>, UseCase<?, ?>> useCases) {
        this.commandUseCaseMap = useCases;
    }

    public static UseCaseDispatcher getInstance(Map<Class<? extends Command>, UseCase<?, ?>> useCases) {
        if (instance == null) {
            synchronized (UseCaseDispatcher.class) {
                if (instance == null) {
                    instance = new UseCaseDispatcher(useCases);
                }
            }
        }
        return instance;
    }


    public <C extends Command, V> V dispatch(C command) {
        UseCase<C, V> useCase = (UseCase<C, V>) commandUseCaseMap.get(command.getClass());
        if (useCase == null) {
            throw new IllegalArgumentException(String.format("No command found for %s", command.getClass()));
        }
        return useCase.execute(command);
    }

}
