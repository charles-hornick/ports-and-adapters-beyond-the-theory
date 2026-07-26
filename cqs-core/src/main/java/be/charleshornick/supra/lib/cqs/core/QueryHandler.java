package be.charleshornick.supra.lib.cqs.core;

import org.pragmatica.lang.Result;

public interface QueryHandler<R, Q extends Query<R>> {

    Result<R> handle(Q query);

    Class<Q> queryType();
}
