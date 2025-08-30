public class Customerbill extends Bill {
	public Customerbill() {
		super(); // sets "Unspecfied", 0.0 (your default)
	}

	public Customerbill(String id, double amount) {
		super(id, amount); // sets the private fields in Bill
	}

	@Override
	public String toString() {
		return "Customer " + super.toString();
	}
}
