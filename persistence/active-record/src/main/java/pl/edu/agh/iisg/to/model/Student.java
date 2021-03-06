package pl.edu.agh.iisg.to.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import pl.edu.agh.iisg.to.executor.QueryExecutor;

public class Student {

	public static final String TABLE_NAME = "student";

	private final int id;

	private final String firstName;

	private final String lastName;

	private final int indexNumber;

	private Student(final int id, final String firstName, final String lastName, final int indexNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.indexNumber = indexNumber;
	}

	public static Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
		String insertSql = String.format("INSERT INTO %s (first_name, last_name, index_number) VALUES ('%s', '%s', %d);", TABLE_NAME, firstName, lastName, indexNumber); // TODO implement
		int id;
        try {
            id = QueryExecutor.createAndObtainId(insertSql);
            return Student.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return Optional.empty();
	}

	public static Optional<Student> findByIndexNumber(final int indexNumber) {
		String findByIndexNumberSql = String.format("SELECT * FROM student where index_number = %d", indexNumber);
        try{
            ResultSet rs = QueryExecutor.read(findByIndexNumberSql);
            return Optional.of(new Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getInt("index_number")));
        } catch (SQLException e){
            e.printStackTrace();
        }
		return Optional.empty();
	}

    public Map<Course, Float> createReport()  {
        String reportQuery = String.format("SELECT c.id AS course_id, c.name AS course_name, AVG(g.grade) AS grade_avg FROM grade AS g JOIN course AS c ON g.course_id = c.id WHERE g.student_id = %d GROUP BY g.student_id, c.id;", this.id);

        Map<Course, Float> result = new HashMap<>();

        try{
            ResultSet rs = QueryExecutor.read(reportQuery);
            while (rs.next()) {
                Optional<Course> course = Course.findById(rs.getInt("course_id"));

                course.ifPresent(key -> {
                    try {
                        result.put(key, rs.getFloat("grade_avg"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (SQLException e){
            e.printStackTrace();
        }


        return result;
    }
	
	public static Optional<Student> findById(final int id) {
		String findByIdSql = String.format("SELECT * FROM student WHERE id = %d", id);
		try {
			ResultSet rs = QueryExecutor.read(findByIdSql);
	        return Optional.of(new Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getInt("index_number")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public int id() {
		return id;
	}

	public String firstName() {
		return firstName;
	}

	public String lastName() {
		return lastName;
	}

	public int indexNumber() {
		return indexNumber;
	}

	public static class Columns {

		public static final String ID = "id";

		public static final String FIRST_NAME = "first_name";

		public static final String LAST_NAME = "last_name";

		public static final String INDEX_NUMBER = "index_number";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Student student = (Student) o;

		if (id != student.id)
			return false;
		if (indexNumber != student.indexNumber)
			return false;
		if (!firstName.equals(student.firstName))
			return false;
		return lastName.equals(student.lastName);
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + indexNumber;
		return result;
	}
}
