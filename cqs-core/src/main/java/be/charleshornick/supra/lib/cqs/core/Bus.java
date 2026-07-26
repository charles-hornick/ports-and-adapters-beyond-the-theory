package be.charleshornick.supra.lib.cqs.core;

import org.pragmatica.lang.Result;
import org.pragmatica.lang.Unit;

public interface Bus {

    <C extends Command> Result<Unit> executeCommand(C command);

    <R, Q extends Query<R>> Result<R> executeQuery(Q query);
}

