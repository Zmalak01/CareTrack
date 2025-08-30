public class Employee {
	
	private String id;
	private String name;
	private String specialty;
	private double salary;
	
	
	public Employee()
	{
		id = "Unknown";
		name = "Undetermined";
		specialty = "Undefined";
		salary = 0.0;
	}
	
	public Employee(String id, String name,String specialty, double salary)
	{
		this.id = id;
		this.name = name;
		this.specialty = specialty;
		this.salary = salary;
	}
	
	
	public void setId(String id)
	{
		this.id = id;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setSalary(double salary)
	{
		this.salary = salary;
	}
	public void setSpecialty(String specialty)
	{
		this.specialty = specialty;
	}
	
	
	
	public String getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public String getSpecialty()
	{
		return specialty;
	}
	public double getSalary()
	{
		return salary;
	}
	
	@Override
	public String toString()
	{
		return
				"Id: " + id + "\n" + 
				"Name: " + name + "\n" + 
				"Specialty: (" + specialty + ") " + " \n" + 
				"Salary: " + salary;
				
	}
}
