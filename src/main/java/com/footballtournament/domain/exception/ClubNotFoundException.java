package java.com.footballtournament.domain.exception;

public class ClubNotFoundException extends DomainException {
    public ClubNotFoundException(String id) {
        super("Club not found with id: " + id);
    }
}
