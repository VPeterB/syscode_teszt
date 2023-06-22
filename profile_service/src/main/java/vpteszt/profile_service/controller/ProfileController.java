package vpteszt.profile_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vpteszt.profile_service.dto.StudentCreateDTO;
import vpteszt.profile_service.model.Student;
import vpteszt.profile_service.service.ProfileService;
import javax.validation.Valid;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/students")
public class ProfileController {

    private static final Logger logger = Logger.getLogger(ProfileController.class.getName());

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return profileService.getAllStudents();
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentCreateDTO student) {
        Student createdStudent = profileService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable("id") UUID id,
            @Valid @RequestBody Student student
    ) {
        if(!id.equals(student.getId())){
            logger.warning(id + " != " + student.getId());
            logger.warning("id mismatch aborted edit");
            return new ResponseEntity<>(new Student(), HttpStatus.BAD_REQUEST);
        }
        Student updatedStudent = profileService.updateStudent(id, student);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") UUID id) {
        profileService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
