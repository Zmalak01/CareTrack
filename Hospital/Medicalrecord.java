public class Medicalrecord {
	private String patientId;
	private String date;
	private String notes;
	
	public Medicalrecord()
	{
		patientId = "Unknown";
		date = "Unspecifed";
		notes = "None";
	}
	
	public Medicalrecord(String patientId, String date, String notes)
	{
		this.patientId = patientId;
		this.date = date;
		this.notes = notes;
	}
	
	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public void setNotes(String notes)
	{
		this.notes = notes;
	}
	public String getPatientId()
	{
		return patientId;
	}
	public String getDate()
	{
		return date;

	}
	public String getNotes()
	{
		return notes;
	}
	@Override
	public String toString()
	{
		return 
				
				"Patient Id: " + patientId + "\n" + 
				"On " + date + "\n" + 
				"Notes: " + notes;
		
	}
}
