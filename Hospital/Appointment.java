public class Appointment {

	private String patientId;
	private String doctorId;
	private String date;
	
	public Appointment()
	{
		patientId = "Unknown";
		doctorId = "Unspecified";
		date ="Undetermined";
	}
	
	public Appointment(String patientId, String doctorId,String date)
	{
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.date = date;
	}
	
	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}
	public void setDoctorId(String doctorId)
	{
		this.doctorId=doctorId;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public String getPatientId()
	{
		return patientId;
	}
	public String getDoctorId()
	{
		return doctorId;
	}
	public String getDate()
	{
		return date;
	}
	@Override
	public String toString()
	{
		return 
				"Appointment Date: " + date + "\n" + 
				"Patient Id: " + patientId + "\n" + 
				"Doctor Id: " + doctorId + "\n";
				
		
	}
}