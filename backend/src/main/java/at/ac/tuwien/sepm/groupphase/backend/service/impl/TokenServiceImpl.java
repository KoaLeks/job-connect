package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployeeService;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployerService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
import at.ac.tuwien.sepm.groupphase.backend.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private final SecurityProperties securityProperties;
    private final ProfileService profileService;
    private final EmployerService employerService;
    private final EmployeeService employeeService;

    @Autowired
    public TokenServiceImpl(SecurityProperties securityProperties, ProfileService profileService, EmployerService employerService, EmployeeService employeeService) {
        this.securityProperties = securityProperties;
        this.profileService = profileService;
        this.employerService = employerService;
        this.employeeService = employeeService;
    }

    @Override
    public String getEmailFromHeader(String authHeader) {
        return getClaims(authHeader).getBody().getSubject();
    }

    @Override
    public Profile getProfileFromHeader(String authHeader) {
        return profileService.findProfileByEmail(getClaims(authHeader).getBody().getSubject());
    }

    @Override
    public Employer getEmployerFromHeader(String authHeader) {
        return employerService.findOneByEmail(getClaims(authHeader).getBody().getSubject());
    }

    @Override
    public Employee getEmployeeFromHeader(String authHeader) {
        return employeeService.findOneByEmail(getClaims(authHeader).getBody().getSubject());
    }

    private Jws<Claims> getClaims(String header){
        byte[] signingKey = securityProperties.getJwtSecret().getBytes();
        return Jwts.parser()
            .setSigningKey(signingKey)
            .parseClaimsJws(header.split(" ")[1]);
    }


}
