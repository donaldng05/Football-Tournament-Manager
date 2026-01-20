package java.com.footballtournament.domain.exception;

public class TournamentNotFoundException extends DomainException {
    public TournamentNotFoundException(String id) {
        super("Tournament not found with id: " + id);
    }
}
