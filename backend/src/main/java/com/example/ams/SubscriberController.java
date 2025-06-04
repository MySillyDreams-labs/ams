package com.example.ams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entities/{entityId}/subscribers")
public class SubscriberController {

    @Autowired
    private SubscriberRepository subscriberRepo;

    @Autowired
    private OrganizationRepository organizationRepo;

    @PostMapping
    public Subscriber addSubscriber(@PathVariable Long entityId, @RequestBody Subscriber sub) {
        Organization org = organizationRepo.findById(entityId).orElseThrow();
        sub.setOrganization(org);
        return subscriberRepo.save(sub);
    }

    @GetMapping
    public List<Subscriber> list(@PathVariable Long entityId) {
        Organization org = organizationRepo.findById(entityId).orElseThrow();
        return org.getId() != null ? subscriberRepo.findAll().stream().filter(s -> s.getOrganization().getId().equals(entityId)).toList() : List.of();
    }
}
