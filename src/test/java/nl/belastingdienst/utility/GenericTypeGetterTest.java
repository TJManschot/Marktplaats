package nl.belastingdienst.utility;

import nl.belastingdienst.utility.testclasses.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericTypeGetterTest {
    PersonDao<Person> personDao = new PersonDao<>();
    EmployeeDao employeeDao = new EmployeeDao();

    @Test
    void getGenericsUsedByDirectSubset() {
        Class<?>[] result = GenericTypeGetter.INSTANCE.getGenericTypes(Dao.class, personDao.getClass());

        assertNotNull(result);
        assertEquals(2, result.length);

        assertEquals(Person.class, result[0]);
        assertEquals(ID.class, result[1]);
    }

    @Test
    void getGenericsUsedBySubsetOfDirectSubset() {
        Class<?>[] result = GenericTypeGetter.INSTANCE.getGenericTypes(Dao.class, employeeDao.getClass());

        assertNotNull(result);
        assertEquals(2, result.length);

        assertEquals(Employee.class, result[0]);
        assertEquals(ID.class, result[1]);
    }
}
