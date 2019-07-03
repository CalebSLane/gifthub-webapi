package ca.csl.gifthub.web.api.modules.account;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.csl.gifthub.core.model.account.Privilege;
import ca.csl.gifthub.core.model.account.Role;
import ca.csl.gifthub.core.model.account.User;
import ca.csl.gifthub.core.persistence.service.account.PrivilegeService;
import ca.csl.gifthub.core.persistence.service.account.RoleService;
import ca.csl.gifthub.core.persistence.service.account.UserService;
import ca.csl.gifthub.web.api.modules.PersistentObjectApiController;

@RestController
@RequestMapping("/api/users")
public class UserApiController extends PersistentObjectApiController<User, Integer> {

    private UserService userService;
    private RoleService roleService;
    private PrivilegeService privilegeService;

    public UserApiController(UserService userService, RoleService roleService, PrivilegeService privilegeService) {
        super(userService);
        this.userService = userService;
        this.roleService = roleService;
        this.privilegeService = privilegeService;
    }

    @GetMapping(value = "/createdefaults")
    public void createDefaultUsers() {
        Privilege readPrivilege = this.createPrivilegeIfNotFound("GLOBAL_READ_PRIVILEGE");
        Privilege createPrivilege = this.createPrivilegeIfNotFound("GLOBAL_CREATE_PRIVILEGE");
        Privilege editPrivilege = this.createPrivilegeIfNotFound("GLOBAL_EDIT_PRIVILEGE");
        Privilege deletePrivilege = this.createPrivilegeIfNotFound("GLOBAL_DELETE_PRIVILEGE");

        Privilege localReadPrivilege = this.createPrivilegeIfNotFound("LOCAL_READ_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, createPrivilege, editPrivilege, deletePrivilege);
        Role adminRole = this.createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        Role userRole = this.createRoleIfNotFound("ROLE_USER", Arrays.asList(localReadPrivilege));

        User adminUser = this.userService.getUserByUsername("admin")
                .orElse(new User("admin", "adminadmin", "admin@gifthub.com").withEncryptedPassword());
        User user = this.userService.getUserByUsername("user")
                .orElse(new User("user", "useruser", "user@gifthub.com").withEncryptedPassword());
        adminUser.setRoles(Arrays.asList(adminRole));
        user.setRoles(Arrays.asList(userRole));
        this.userService.saveAll(Arrays.asList(user, adminUser));
    }

    private Privilege createPrivilegeIfNotFound(String name) {
        return this.privilegeService.getByName(name).orElse(new Privilege(name));
    }

    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = this.roleService.getByName(name).orElse(new Role(name));
        role.setPrivileges(privileges);
        return role;
    }

}
