package vpteszt.profile_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import vpteszt.profile_service.dto.StudentCreateDTO;
import vpteszt.profile_service.model.Student;
import vpteszt.profile_service.repository.StudentRepository;
import vpteszt.profile_service.service.ProfileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class ProfileServiceApplicationUnitTests {

    @Mock
    private StudentRepository studentRepository;

    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profileService = new ProfileService(studentRepository);
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("teszt egy", "teszt1@valami.com"));
        students.add(new Student("teszt ketto", "teszt2@valami.com"));

        Mockito.when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = profileService.getAllStudents();

        Assertions.assertEquals(students.size(), result.size());
        Assertions.assertEquals(students.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(students.get(1).getName(), result.get(1).getName());
        Assertions.assertEquals(students.get(0).getEmail(), result.get(0).getEmail());
        Assertions.assertEquals(students.get(1).getEmail(), result.get(1).getEmail());
    }

    @Test
    void testCreateStudent() {
        StudentCreateDTO studentCreateDTO = new StudentCreateDTO();
        studentCreateDTO.setName("teszt student");
        studentCreateDTO.setEmail("teszt.student@valami.com");

        Student createdStudent = new Student(studentCreateDTO.getName(), studentCreateDTO.getEmail());

        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(createdStudent);

        Student result = profileService.createStudent(studentCreateDTO);

        Assertions.assertEquals(createdStudent.getId(), result.getId());
        Assertions.assertEquals(createdStudent.getName(), result.getName());
        Assertions.assertEquals(createdStudent.getEmail(), result.getEmail());
    }

    @Test
    void testUpdateStudent() {
        Student existingStudent = new Student("teszt valami", "teszt@valami.com");
        UUID studentId = existingStudent.getId();

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(existingStudent);

        Student updatedStudent = new Student("teszt mas", "teszt.mas@valami.com");
        updatedStudent.setId(studentId);

        Student result = profileService.updateStudent(studentId, updatedStudent);

        Assertions.assertEquals(updatedStudent.getName(), result.getName());
        Assertions.assertEquals(updatedStudent.getEmail(), result.getEmail());
    }

    @Test
    void testUpdateStudent_StudentNotFound() {
        Student updatedStudent = new Student("teszt egy", "teszt.egy@valami.com");
        UUID studentId = updatedStudent.getId();

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            profileService.updateStudent(studentId, updatedStudent);
        });
    }

    @Test
    void testDeleteStudent() {
        UUID studentId = UUID.randomUUID();

        profileService.deleteStudent(studentId);

        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(studentId);
    }
}

