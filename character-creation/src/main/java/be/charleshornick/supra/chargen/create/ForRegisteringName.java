package be.charleshornick.supra.chargen.create;

import org.pragmatica.lang.Result;
import org.pragmatica.lang.Unit;

public interface ForRegisteringName {
    Result<Unit> register(String name);
}
