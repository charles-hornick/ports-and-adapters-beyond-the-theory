package be.charleshornick.supra.facade.rest.create;

public record CreateCharacterRequest(String characterName) {

    public CreateCharacterCommand toCommand() {
        return new CreateCharacterCommand(characterName);
    }
}
