package be.charleshornick.supra.lib.cqs.core;

import org.pragmatica.lang.Result;
import org.pragmatica.lang.Unit;

public interface CommandHandler<C extends Command> {

    Result<Unit> handle(C command);

    Class<C> commandType();
}
