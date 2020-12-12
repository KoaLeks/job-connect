package at.ac.tuwien.sepm.groupphase.backend.service;

public interface TokenService {
    String getEmailFromHeader(String authHeader);
}
