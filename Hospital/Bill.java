public abstract class Bill {
	private String id;
	private double amount;

	public Bill() {
		id = "Unspecfied";
		amount = 0.0;
	}

	public Bill(String id, double amount) {
		this.id = id;
		this.amount = amount;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public double getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "Id: " + id + "\n" +
				"Amount: $" + amount;
	}

}
