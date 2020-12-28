package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CourseDAOTest {
	@Mock private CourseDAO courseDAO;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldThrowUnsupportedOperationExceptionWhenFindById() {
		when(courseDAO.findById(anyInt())).thenCallRealMethod();
		assertThrows(UnsupportedOperationException.class, () -> courseDAO.findById(1));
	}

	@Test
	void shouldThrowUnsupportedOperationExceptionWhenDeleteById() {
		doCallRealMethod().when(courseDAO).deleteById(anyInt());
		assertThrows(UnsupportedOperationException.class, () -> courseDAO.deleteById(1));
	}

}
