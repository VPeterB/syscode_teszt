package vpteszt.profile_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpteszt.profile_service.dto.StudentCreateDTO;
import vpteszt.profile_service.model.Student;
import vpteszt.profile_service.repository.StudentRepository;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class ProfileService {

    private static final Logger logger = Logger.getLogger(ProfileService.class.getName());

    private final StudentRepository studentRepository;

    @Autowired
    public ProfileService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> getAllStudents() {
        logger.info("find all called");
        return studentRepository.findAll();
    }
    public Student createStudent(StudentCreateDTO student) {
        Student newStudent = new Student(student.getName(), student.getEmail());
        logger.info("create uuid: " + newStudent.getId());
        return studentRepository.save(newStudent);
    }


    public Student updateStudent(UUID id, Student student) {
        Student modStudent = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with this ID: " + id));

        modStudent.setName(student.getName());
        modStudent.setEmail(student.getEmail());
        logger.info("update uuid: " + id);
        return studentRepository.save(modStudent);
    }

    public void deleteStudent(UUID id) {
        logger.info("deleted student uuid: " + id);
        studentRepository.deleteById(id);
    }
}
