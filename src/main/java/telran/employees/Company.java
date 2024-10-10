package telran.employees;
import java.util.List;
public interface Company extends Iterable<Employee>{
	public void addEmployee(Employee empl) ;
	public Employee getEmployee(long id) ;
	public List<Employee> getAllEmployees();
	public Employee removeEmployee(long id) ;
	public int getDepartmentBudget(String department) ;
	public String[] getDepartments() ;
	public Manager[] getManagersWithMostFactor() ;	
}
