package be.charleshornick.supra.shared;

import org.pragmatica.lang.Cause;
import org.pragmatica.lang.utils.Causes;

public interface ErrorCause {

    Cause NAME_EMPTY_VALUE = Causes.cause("Name cannot be empty.");
    Cause NAME_ALREADY_TAKEN = Causes.cause("Name is already taken.");
    Cause RACE_DOES_NOT_EXIST = Causes.cause("Race does not exist.");
    Cause CHARACTER_DOES_NOT_EXIST = Causes.cause("Character does not exist.");
    Cause PROFESSION_EMPTY_VALUE = Causes.cause("Profession cannot be empty.");
    Cause PROFESSION_DOES_NOT_EXIST = Causes.cause("Profession does not exist.");
    Cause NOT_ENOUGH_CREATION_POINT = Causes.cause("Not enough creation points.");
}
