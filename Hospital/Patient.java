public class Patient {
	private String id;
	private String name;
	private int age;
	private String type; //Ambulatory or outpatient check
	private String gender;
	
	public Patient()
	{
		id = "Unknown";
		name = "Undefined";
		age = 0;
		type ="Unspecified";
		gender = "Unknown";
	}
	
	public Patient(String id, String name, int age, String type, String gender)
	{
		
		this.id = id;
		this.name = name;
		this.age = age;
		this.type = type;
		this.gender =gender;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setAge(int age)
	{
		this.age = age;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public void setGender(String gender)
	{
		this.gender = gender;
	}
	
	public String getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public int getAge()
	{
		return age;
	}
	public String getType()
	{
		return type;
	}
	
	public String getGender()
	{
		return gender;
	}
	
	@Override
	public String toString()
	{
		return 
				"Id: " + id + "\n" + 
				"Name:  " +  name + "\n" +
				"Age: " + age + "\n" + 
				"Type: " + type + "\n" +
				"Gender: " + gender;
		
		
	}
	

}
