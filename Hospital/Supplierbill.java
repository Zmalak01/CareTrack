public class Supplierbill extends Bill {
	public Supplierbill() {
		super();
	}

	public Supplierbill(String id, double amount) {
		super(id, amount);
	}

	@Override
	public String toString() {
		return "Supplier " + super.toString();
	}
}
