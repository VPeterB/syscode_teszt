package vpteszt.profile_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import vpteszt.profile_service.model.Student;
import vpteszt.profile_service.repository.StudentRepository;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Transactional
class ProfileServiceApplicationIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private StudentRepository studentRepository;

	@BeforeEach
	public void databaseSetUp() {
		Student student1 = new Student("teszt egy", "teszt1@valami.com");
		Student student2 = new Student("teszt ketto", "teszt2@valami.com");
		studentRepository.saveAll(List.of(student1, student2));
	}

	@Test
	public void testGetAllStudents() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/students"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}

	@Test
	public void testCreateStudent() throws Exception {
		Student student = new Student("uj student", "uj.student@valami.com");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
						.contentType("application/json")
						.content("{\"name\": \"" + student.getName() + "\", \"email\": \"" + student.getEmail() + "\"}"))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		List<Student> students = studentRepository.findAll();
		assertThat(students).hasSize(3);
	}

}
