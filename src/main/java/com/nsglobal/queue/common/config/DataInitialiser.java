package com.nsglobal.queue.common.config;

import javax.crypto.SecretKey;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.common.enums.EnumRole;
import com.nsglobal.queue.role.entity.Role;
import com.nsglobal.queue.role.repository.RoleRepository;
import com.nsglobal.queue.user.entity.User;
import com.nsglobal.queue.user.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitialiser implements CommandLineRunner{
	
	private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final BranchRepository branchRepository;

    private final PasswordEncoder passwordEncoder;
    
	@Override
	public void run(String... args) throws Exception {
		//SecretKey key = Jwts.SIG.HS256.key().build();

		//String secret = Encoders.BASE64.encode(key.getEncoded());
		//System.out.println("-----------Initializing.....-----------\n");
		//System.out.println(secret);
	//System.out.println("\n-----------Initializing.....-----------");
		//		initializeRoles();
	//System.out.println("|||||||||||||ende role Initializing.....|||||||||||||");			
		//	    initializeSuperAdmin();
	//System.out.println("==========Initialized.......===========");
		
		
	}
	
	private void createRole(EnumRole roleName) {

        if (!roleRepository.existsByName(roleName.toString())) {

            Role role =Role.builder()
            		.name(roleName.toString())
            		.build();

            roleRepository.save(role);

        }else {
        	System.out.println("Role %s existe deja.".formatted(roleName));
        }

    }
	
	 private void initializeRoles() {

	        createRole(EnumRole.SUPER_ADMIN);
	        createRole(EnumRole.ADMIN);
	        createRole(EnumRole.AGENCY_MANAGER);
	        createRole(EnumRole.SUPERVISOR);
	        createRole(EnumRole.OPERATOR);
	        createRole(EnumRole.DISPLAY);
	        createRole(EnumRole.AUDITOR);

	    }
	
	private void initializeSuperAdmin() {

        if (userRepository.existsByUserName("superadmin")) {
        	System.out.println("User %s existe deja.".formatted("superadmin"));
            return;

        }

        Role role = roleRepository.findByName(EnumRole.SUPER_ADMIN.toString())
                .orElseThrow();

        Branch branch = branchRepository.findById(1L)
                .orElseThrow(()-> new RuntimeException("Aucun succursale de l'entrerise trouver"));

        User user =User.builder()
        		.userName("superadmin")
        		.password(passwordEncoder.encode("S_admin123"))
        		.firstName("System")
        		.lastName("Administrator")
        		.email("admin@nsglobal.com")
        		.build();
        
        user.setEnabled(true);

        user.setLocked(false);

        user.setRole(role);

        user.setBranch(branch);

        userRepository.save(user);

    }


}
