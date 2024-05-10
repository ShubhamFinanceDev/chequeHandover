package cheque.handover.services.Services;

import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Repository.UserDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomService implements UserDetailsService {

    @Autowired
    private UserDetailRepo userDetailRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail userDetail= userDetailRepo.findUser(username).orElseThrow(() -> new RuntimeException("user not found"));
        return userDetail;
    }
}
