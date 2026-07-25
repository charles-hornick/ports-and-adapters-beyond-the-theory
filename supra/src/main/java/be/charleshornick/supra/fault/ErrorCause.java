package be.charleshornick.supra.fault;

import org.pragmatica.lang.Cause;

public interface ErrorCause {

    Cause NAME_EMPTY_VALUE = new SupraCause.InvalidInput("character.name", "cannot.be.empty");
    Cause NAME_ALREADY_TAKEN = new SupraCause.Conflict("character.name", "already.taken.");
    Cause RACE_DOES_NOT_EXIST = new SupraCause.InvalidInput("race", "does.not.exist");
    Cause CHARACTER_DOES_NOT_EXIST = new SupraCause.InvalidInput("character", "does.not.exist");
    Cause PROFESSION_DOES_NOT_EXIST = new SupraCause.InvalidInput("profession", "does.not.exist");
    Cause NOT_ENOUGH_CREATION_POINT = new SupraCause.RuleViolation("creation.points.exhausted", null);
    Cause UNDEFINED_PRIMARY_CHARACTERISTIC = new SupraCause.InvalidInput("characteristic", "undefined");
}
