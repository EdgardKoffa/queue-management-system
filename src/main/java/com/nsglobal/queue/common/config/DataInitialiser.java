package com.nsglobal.queue.common.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.common.enums.EnumPermissions;
import com.nsglobal.queue.common.enums.EnumRole;
import com.nsglobal.queue.role.entity.Permission;
import com.nsglobal.queue.role.entity.Permission.PermissionBuilder;
import com.nsglobal.queue.role.entity.Role;
import com.nsglobal.queue.role.repository.PermissionRepository;
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
	
	private final PermissionRepository permRepo;

    private final UserRepository userRepository;

    private final BranchRepository branchRepository;

    private final PasswordEncoder passwordEncoder;
    
	@Override
	public void run(String... args) throws Exception {
		SecretKey key = Jwts.SIG.HS256.key().build();

		String secret = Encoders.BASE64.encode(key.getEncoded());
		System.out.println("-----------Initializing.....-----------\n");
		//System.out.println(secret);
	//System.out.println("\n-----------Initializing.....-----------");
				initializeRoles();
	//System.out.println("|||||||||||||ende role Initializing.....|||||||||||||");			
			    initializeSuperAdmin();
	//System.out.println("==========Initialized.......===========");
		
		
	}
	
	private void createRole(EnumRole roleName, String description,
            Set<Permission> permissions) {

        if (!roleRepository.existsByName(roleName)) {

            Role role =Role.builder()
            		.name(roleName)
            		.description(description)
            		.permissions(permissions)
            		.build();

            roleRepository.save(role);

        }else {
        	System.out.println("Role %s existe deja.".formatted(roleName));
        }

    }
	private void createRole(EnumRole roleName, String description) {

        if (!roleRepository.existsByName(roleName)) {

            Role role =Role.builder()
            		.name(roleName)
            		.description(description)
            		.build();

            roleRepository.save(role);

        }else {
        	System.out.println("Role %s existe deja.".formatted(roleName));
        }

    }
	
	private Permission createPermission(EnumPermissions permissionName, String description) {
		PermissionBuilder perm =Permission.builder();
       
		if (!permRepo.existsByName(permissionName)) {

        	perm
        		.name(permissionName)
        		.description(description);
      return permRepo.save(perm.build());
            
        }else {
        	System.out.println("Role %s existe deja.".formatted(permissionName));
        return perm.build();
        }

    }

	 private void initializeRoles() {
		 
		 	List<EnumPermissions> perms= Arrays.asList(EnumPermissions.values());
		 	
		 	Set<Permission> listPerms=new HashSet<>();
		 	
		 	for (EnumPermissions enumPermissions : perms) {
		 		listPerms.add(createPermission(
		 				enumPermissions,
		 				enumPermissions.getDescription()
		 				));
			}
		 	
	        createRole(EnumRole.SUPER_ADMIN,
	        		"Super administrateur du systeme",
	        		listPerms
	        		);
	        
	        createRole(EnumRole.ADMIN,"");
	       createRole(EnumRole.AGENCY_MANAGER,"");
	       createRole(EnumRole.SUPERVISOR,"");
	       createRole(EnumRole.OPERATOR,"");
	       createRole(EnumRole.DISPLAY,"");
	       createRole(EnumRole.AUDITOR,"");

	    }
	
	private void initializeSuperAdmin() {

        if (userRepository.existsByUserName("superadmin")) {
        	System.out.println("User %s existe deja.".formatted("superadmin"));
            return;

        }

        Role role = roleRepository.findByName(EnumRole.SUPER_ADMIN)
                .orElseThrow();

      //  Branch branch = branchRepository.findById(1L)
          //      .orElseThrow(()-> new RuntimeException("Aucun agence de l'entrerise trouver"));

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

       // user.setBranch(branch);

        userRepository.save(user);

    }


}
