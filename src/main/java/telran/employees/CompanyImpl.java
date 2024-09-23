package telran.employees;

import java.util.*;

public class CompanyImpl implements Company{
   private TreeMap<Long, Employee> employees = new TreeMap<>();
   private HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
   private TreeMap<Float, List<Manager>> managersFactor = new TreeMap<>();

    @Override
    public Iterator<Employee> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public void addEmployee(Employee empl) {
        Employee oldEmployee = employees.putIfAbsent(empl.getId(), empl);
        if (oldEmployee != null) {
            throw new IllegalStateException();
        }
        addDepartment(empl);
        addManager(empl);
    }

    private void addManager(Employee empl) {
        if (empl instanceof Manager manager) {
            managersFactor.computeIfAbsent(manager.getFactor(), i -> new ArrayList<>()).add(manager);
        }
    }

    private void addDepartment(Employee empl) {
        String department = empl.getDepartment();

        if (department != null) {
            employeesDepartment.computeIfAbsent(department, i -> new ArrayList<>()).add(empl);
        }
    }

    @Override
    public Employee getEmployee(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmployee'");
    }

    @Override
    public Employee removeEmployee(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeEmployee'");
    }

    @Override
    public int getDepartmentBudget(String department) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDepartmentBudget'");
    }

    @Override
    public String[] getDepartments() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDepartments'");
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getManagersWithMostFactor'");
    }

}
