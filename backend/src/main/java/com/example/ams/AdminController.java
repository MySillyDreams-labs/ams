package com.example.ams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrganizationRepository organizationRepo;

    @Autowired
    private EntityAdminRepository adminRepo;

    @PostMapping("/entities")
    public Organization createOrganization(@RequestBody Organization org) {
        return organizationRepo.save(org);
    }

    @GetMapping("/entities")
    public List<Organization> listOrganizations() {
        return organizationRepo.findAll();
    }

    @PostMapping("/entities/{id}/admins")
    public EntityAdmin createAdmin(@PathVariable Long id, @RequestBody EntityAdmin admin) {
        Organization org = organizationRepo.findById(id).orElseThrow();
        admin.setOrganization(org);
        return adminRepo.save(admin);
    }
}
