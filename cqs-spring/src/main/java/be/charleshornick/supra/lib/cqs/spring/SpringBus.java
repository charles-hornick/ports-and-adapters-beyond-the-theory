package be.charleshornick.supra.lib.cqs.spring;

import be.charleshornick.supra.lib.cqs.core.*;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Unit;
import org.pragmatica.lang.utils.Causes;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class SpringBus implements Bus {

    private final Map<Class<? extends Command>, CommandHandler<?>> commandHandlers;
    private final Map<Class<? extends Query<?>>, QueryHandler<?, ?>> queryHandlers;

    public SpringBus(final List<CommandHandler<?>> commands,
                     final List<QueryHandler<?, ?>> queries) {
        this.commandHandlers = commands.stream()
                .collect(Collectors.toMap(CommandHandler::commandType, Function.identity()));

        this.queryHandlers = queries.stream()
                .collect(Collectors.toMap(QueryHandler::queryType, Function.identity()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C extends Command> Result<Unit> executeCommand(final C command) {
        final var handler = (CommandHandler<C>) this.commandHandlers.get(command.getClass());
        if (handler == null) {
            return Result.failure(Causes.cause("cqs.no.command.handler." + command.getClass().getSimpleName()));
        }
        return handler.handle(command);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, Q extends Query<R>> Result<R> executeQuery(final Q query) {
        final var handler = (QueryHandler<R, Q>) this.queryHandlers.get(query.getClass());
        if (handler == null) {
            return Result.failure(Causes.cause("cqs.no.query.handler." + query.getClass().getSimpleName()));
        }
        return handler.handle(query);
    }
}

