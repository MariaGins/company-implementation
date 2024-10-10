package telran.employees;

import java.io.*;
import java.util.Map.Entry;
import java.lang.IllegalStateException;

import java.util.*;

import telran.io.Persistable;

public class CompanyImpl implements Company, Persistable {
    private TreeMap<Long, Employee> employees = new TreeMap<>();
    private HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
    private TreeMap<Float, List<Manager>> managersFactor = new TreeMap<>();

    private class CompanyIterator implements Iterator<Employee> {
        Iterator<Employee> iterator = employees.values().iterator();
        Employee lastIterated;

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Employee next() {
            lastIterated = iterator.next();
            return lastIterated;
        }

        @Override
        public void remove() {
            iterator.remove();
            removeFromIndexMaps(lastIterated);
        }
    }

    @Override
    public Iterator<Employee> iterator() {
        return new CompanyIterator();
    }

    @Override
    public void addEmployee(Employee empl) {
        long id = empl.getId();
        if (employees.putIfAbsent(id, empl) != null) {
            throw new IllegalStateException("Already exists employee " + id);
        }
        addIndexMaps(empl);
    }

    private void addIndexMaps(Employee empl) {
        employeesDepartment.computeIfAbsent(empl.getDepartment(), k -> new ArrayList<>()).add(empl);
        if (empl instanceof Manager manager) {
            managersFactor.computeIfAbsent(manager.getFactor(), k -> new ArrayList<>()).add(manager);
        }
    }

    @Override
    public Employee getEmployee(long id) {
        return employees.get(id);
    }

    @Override
	public List<Employee> getAllEmployees() {
		
		return new ArrayList<>(employees.values());
	}

    @Override
    public Employee removeEmployee(long id) {
        Employee empl = employees.remove(id);
        if (empl == null) {
            throw new NoSuchElementException("Not found employee " + id);
        }
        removeFromIndexMaps(empl);
        return empl;
    }

    private void removeFromIndexMaps(Employee empl) {
        removeIndexMap(empl.getDepartment(), employeesDepartment, empl);
        if (empl instanceof Manager manager) {
            removeIndexMap(manager.getFactor(), managersFactor, manager);
        }
    }

    private <K, V extends Employee> void removeIndexMap(K key, Map<K, List<V>> map, V empl) {
        List<V> list = map.get(key);
        list.remove(empl);
        if (list.isEmpty()) {
            map.remove(key);
        }
    }

    @Override
    public int getDepartmentBudget(String department) {
        return employeesDepartment.getOrDefault(department, Collections.emptyList())
                .stream().mapToInt(Employee::computeSalary).sum();
    }

    @Override
    public String[] getDepartments() {
        return employeesDepartment.keySet().stream().sorted().toArray(String[]::new);
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        Manager[] res = new Manager[0];
        if (!managersFactor.isEmpty()) {
            res = managersFactor.lastEntry().getValue().toArray(res);
        }
        return res;
    }

    // @Override
    // public void saveToFile(String fileName) {
    //     try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))){
	// 		output.writeObject(getAllEmployees());
	// 	} catch(Exception e) {
	// 		throw new RuntimeException(e.toString()); 
	// 	}
    // }

            
	// @Override
	// public void restoreFromFile(String fileName) {
	// 	try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))) {
	// 		List<Employee> allEmployees = (List<Employee>) input.readObject();
	// 		allEmployees.forEach(this::addEmployee);
	// 	}catch(FileNotFoundException e) {
			
	// 	} catch (Exception e) {
	// 		throw new RuntimeException(e.toString());
	// 	}

	// }

   
}