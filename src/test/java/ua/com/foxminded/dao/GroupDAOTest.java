package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyInt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.com.foxminded.domain.Group;

class GroupDAOTest {
	@Mock private GroupDAO groupDAO;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void shouldThrowUnsupportedOperationExceptionWhenFindById() {
		when(groupDAO.findById(anyInt())).thenCallRealMethod();
		assertThrows(UnsupportedOperationException.class, () -> groupDAO.findById(1));
	}

	@Test
	void shouldThrowUnsupportedOperationExceptionWhenUpdate() {
		doCallRealMethod().when(groupDAO).update(any(Group.class));
		Group group = new Group(1, "QW-01");
		assertThrows(UnsupportedOperationException.class, () -> groupDAO.update(group));
	}

	@Test
	void shouldThrowUnsupportedOperationExceptionWhenDeleteById() {
		doCallRealMethod().when(groupDAO).deleteById(anyInt());
		assertThrows(UnsupportedOperationException.class, () -> groupDAO.deleteById(1));
	}

}
