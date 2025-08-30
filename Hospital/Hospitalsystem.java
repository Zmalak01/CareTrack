import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Hospitalsystem extends JFrame {

	private JTabbedPane tabs = new JTabbedPane();
	private JButton returnButton = new JButton("Return to Login");

	public Hospitalsystem() {
		super("Hospital Information System");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 600);
		setLayout(new BorderLayout());

		// init DB once
		Database.init();

		JLabel title = new JLabel("Hospital Information System", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 24));
		title.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 10));
		add(title, BorderLayout.NORTH);
		add(tabs, BorderLayout.CENTER);

		returnButton.setFont(new Font("Arial", Font.PLAIN, 14));
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabs.removeAll();
				showLoginDialog();
			}
		});
		add(returnButton, BorderLayout.SOUTH);

		showLoginDialog();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void showLoginDialog() {
		boolean validlogin = false;
		while (!validlogin) {
			String[] options = { "Admin Login", "Patient Login", "Exit" };
			int loginchoice = JOptionPane.showOptionDialog(
					null,
					"Welcome! Please choose login type:",
					"Hospital Login",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					options,
					options[0]);

			if (loginchoice == 0) {
				String password = JOptionPane.showInputDialog("Enter Admin Password:");
				if (password != null && password.equals("Admin324")) {
					tabs.removeAll();
					tabs.addTab("Staff", new StaffPanel());
					tabs.addTab("Patients", new PatientPanel());
					tabs.addTab("Billings", new BillingPanel());
					tabs.addTab("Medical Records", new MedicalrecordPanel(true));
					tabs.addTab("Appointments", new AppointmentPanel());
					validlogin = true;
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect password.");
				}

			} else if (loginchoice == 1) {
				int confirm = JOptionPane.showConfirmDialog(
						null, "Continue as patient?", "Patient Login", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					tabs.removeAll();
					tabs.addTab("Medical Records", new MedicalrecordPanel(false));
					tabs.addTab("Appointments", new AppointmentPanel());
					validlogin = true;
				}

			} else {
				System.exit(0);
			}
		}
	}

	// STAFF
	class StaffPanel extends JPanel {
		private JPanel tablePanel = new JPanel();

		public StaffPanel() {
			setBackground(new Color(245, 245, 245));
			setLayout(new BorderLayout());

			tablePanel.setLayout(new GridLayout(0, 4));
			tablePanel.setBackground(new Color(0, 102, 204));
			tablePanel.add(new JLabel("ID"));
			tablePanel.add(new JLabel("Name"));
			tablePanel.add(new JLabel("Specialty"));
			tablePanel.add(new JLabel("Salary"));
			add(tablePanel, BorderLayout.NORTH);

			// Load employees from DB
			for (Database.EmployeeRow r : Database.getAllEmployees()) {
				tablePanel.add(new JLabel(r.id));
				tablePanel.add(new JLabel(r.name));
				tablePanel.add(new JLabel(r.specialty));
				tablePanel.add(new JLabel(String.valueOf(r.salary)));
			}

			JPanel bottom = new JPanel(new FlowLayout());
			JTextField id = new JTextField(5);
			JTextField name = new JTextField(10);
			JTextField salary = new JTextField(6);
			JComboBox role = new JComboBox(new String[] { "Doctor", "Nurse", "Cleaner", "Receptionist", "Manager" });
			JButton add = new JButton("Add Staff");

			bottom.add(new JLabel("ID: "));
			bottom.add(id);
			bottom.add(new JLabel("Name: "));
			bottom.add(name);
			bottom.add(new JLabel("Salary: "));
			bottom.add(salary);
			bottom.add(new JLabel("Role: "));
			bottom.add(role);
			bottom.add(add);

			add(bottom, BorderLayout.SOUTH);

			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String Id = id.getText().trim();
					String Name = name.getText().trim();
					String Salaryt = salary.getText().trim();
					if (Id.isEmpty() || Name.isEmpty() || Salaryt.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill all fields!");
						return;
					}

					// validate number
					double salaryvar;
					try {
						salaryvar = Double.parseDouble(Salaryt);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid Salary!");
						return;
					}

					String roleStr = role.getSelectedItem().toString();
					String specialty;
					if ("Doctor".equals(roleStr)) {
						specialty = JOptionPane.showInputDialog("Dear User Please Enter Doctor's Specialty:");
						if (specialty == null || specialty.trim().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Specialty is required for Doctor.");
							return;
						}
					} else {
						specialty = roleStr; // Nurse/Cleaner/Receptionist/Manager
					}

					// save -> DB
					Database.insertEmployee(Id, Name, specialty, salaryvar);

					// render -> UI
					tablePanel.add(new JLabel(Id));
					tablePanel.add(new JLabel(Name));
					tablePanel.add(new JLabel(specialty));
					tablePanel.add(new JLabel(String.valueOf(salaryvar)));
					tablePanel.revalidate();
					tablePanel.repaint();
				}
			});
		}
	}

	// PATIENTS
	class PatientPanel extends JPanel {
		private JPanel tablePanel = new JPanel();

		public PatientPanel() {
			setLayout(new BorderLayout());
			setBackground(new Color(230, 240, 255));

			tablePanel.setLayout(new GridLayout(0, 5));
			tablePanel.setBackground(new Color(46, 204, 113));
			tablePanel.add(new JLabel("ID"));
			tablePanel.add(new JLabel("Name"));
			tablePanel.add(new JLabel("Age"));
			tablePanel.add(new JLabel("Type"));
			tablePanel.add(new JLabel("Gender"));
			add(tablePanel, BorderLayout.NORTH);

			// Load patients from DB
			for (Database.PatientRow r : Database.getAllPatients()) {
				tablePanel.add(new JLabel(r.id));
				tablePanel.add(new JLabel(r.name));
				tablePanel.add(new JLabel(String.valueOf(r.age)));
				tablePanel.add(new JLabel(r.type));
				tablePanel.add(new JLabel(r.gender));
			}

			JPanel bottom = new JPanel(new FlowLayout());
			JTextField id = new JTextField(5);
			JTextField name = new JTextField(10);
			JTextField age = new JTextField(3);
			JComboBox type = new JComboBox(new String[] { "Ambulatory", "Outpatient" });
			JComboBox gender = new JComboBox(new String[] { "Male", "Female" });
			JButton add = new JButton("Add Patient!");

			bottom.add(new JLabel("ID: "));
			bottom.add(id);
			bottom.add(new JLabel("Name: "));
			bottom.add(name);
			bottom.add(new JLabel("Age: "));
			bottom.add(age);
			bottom.add(new JLabel("Type: "));
			bottom.add(type);
			bottom.add(new JLabel("Gender: "));
			bottom.add(gender);
			bottom.add(add);

			add(bottom, BorderLayout.SOUTH);

			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String Id = id.getText().trim();
					String Name = name.getText().trim();
					String Aget = age.getText().trim();
					if (Id.isEmpty() || Name.isEmpty() || Aget.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill all fields!");
						return;
					}
					int agevar;
					try {
						agevar = Integer.parseInt(Aget);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid Age!");
						return;
					}
					String Type = type.getSelectedItem().toString();
					String Gender = gender.getSelectedItem().toString();

					// save -> DB
					Database.insertPatient(Id, Name, agevar, Type, Gender);

					// render -> UI
					tablePanel.add(new JLabel(Id));
					tablePanel.add(new JLabel(Name));
					tablePanel.add(new JLabel(String.valueOf(agevar)));
					tablePanel.add(new JLabel(Type));
					tablePanel.add(new JLabel(Gender));
					tablePanel.revalidate();
					tablePanel.repaint();
				}
			});
		}
	}

	// BILLING
	class BillingPanel extends JPanel {
		private JPanel tablePanel = new JPanel();

		public BillingPanel() {
			setLayout(new BorderLayout());
			setBackground(new Color(245, 245, 245));

			tablePanel.setBackground(Color.WHITE);
			tablePanel.setLayout(new GridLayout(0, 3));
			tablePanel.add(new JLabel("Type"));
			tablePanel.add(new JLabel("ID"));
			tablePanel.add(new JLabel("Amount"));
			add(tablePanel, BorderLayout.CENTER);

			// Load bills from DB
			for (Database.BillRow r : Database.getAllBills()) {
				tablePanel.add(new JLabel(r.type));
				tablePanel.add(new JLabel(r.id));
				tablePanel.add(new JLabel(String.valueOf(r.amount)));
			}

			JPanel bottom = new JPanel(new GridLayout(2, 4, 10, 10));
			bottom.setBackground(new Color(230, 240, 255));

			JComboBox type = new JComboBox(new String[] { "Customerbill", "Supplierbill" });
			JTextField id = new JTextField(8);
			JTextField amount = new JTextField(6);
			JButton add = new JButton("Add Bill");
			JButton report = new JButton("Generate billing report");

			bottom.add(new JLabel("Type: "));
			bottom.add(type);
			bottom.add(new JLabel("ID: "));
			bottom.add(id);
			bottom.add(new JLabel("Amount: "));
			bottom.add(amount);
			bottom.add(add);
			bottom.add(report);

			add(bottom, BorderLayout.SOUTH);

			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String Id = id.getText().trim();
					String Amountt = amount.getText().trim();
					if (Id.isEmpty() || Amountt.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill all fields!");
						return;
					}

					double amt;
					try {
						amt = Double.parseDouble(Amountt);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid Amount!");
						return;
					}

					String Type = type.getSelectedItem().toString();

					// save -> DB
					Database.insertBill(Type, Id, amt);

					// render -> UI
					tablePanel.add(new JLabel(Type));
					tablePanel.add(new JLabel(Id));
					tablePanel.add(new JLabel(String.valueOf(amt)));
					tablePanel.revalidate();
					tablePanel.repaint();
				}
			});

			report.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					double totalCustomer = 0;
					double totalSupplier = 0;

					for (Database.BillRow r : Database.getAllBills()) {
						if ("Customerbill".equals(r.type))
							totalCustomer += r.amount;
						else if ("Supplierbill".equals(r.type))
							totalSupplier += r.amount;
					}
					double total = totalCustomer + totalSupplier;

					JOptionPane.showMessageDialog(
							null,
							"Billing Reports:\n" +
									"Total Customer Bills: $" + totalCustomer + "\n" +
									"Total Supplier Bill: $" + totalSupplier + "\n" +
									"Total: $" + total,
							"Billing Summary",
							JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
	}

	// MEDICAL RECORDS
	class MedicalrecordPanel extends JPanel {
		private JPanel tablePanel = new JPanel();

		public MedicalrecordPanel(boolean isAdmin) {
			setLayout(new BorderLayout());
			setBackground(new Color(240, 248, 255));

			tablePanel.setBackground(Color.WHITE);
			tablePanel.setLayout(new GridLayout(0, 3));
			tablePanel.add(new JLabel("Patient ID"));
			tablePanel.add(new JLabel("Date"));
			tablePanel.add(new JLabel("Notes"));
			add(tablePanel, BorderLayout.NORTH);

			for (Database.MedicalRecordRow r : Database.getAllMedicalRecords()) {
				tablePanel.add(new JLabel(r.patientId));
				tablePanel.add(new JLabel(r.date));
				tablePanel.add(new JLabel(r.description));
			}

			if (isAdmin) {
				JPanel bottom = new JPanel(new GridLayout(2, 4, 10, 10));
				bottom.setBackground(new Color(230, 240, 255));

				JTextField patientId = new JTextField(6);
				JTextField date = new JTextField(8);
				JTextField notes = new JTextField(20);
				JButton add = new JButton("Add Record");

				bottom.add(new JLabel("Patient Id: "));
				bottom.add(patientId);
				bottom.add(new JLabel("Date: "));
				bottom.add(date);
				bottom.add(new JLabel("Notes: "));
				bottom.add(notes);
				bottom.add(add);

				add.setBackground(new Color(46, 204, 113));
				add.setForeground(Color.WHITE);
				add.setFocusPainted(false);
				add(bottom, BorderLayout.SOUTH);

				add.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String patientID = patientId.getText().trim();
						String visitdate = date.getText().trim();
						String Notes = notes.getText().trim();

						if (patientID.isEmpty() || visitdate.isEmpty() || Notes.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please fill all field required!");
							return;
						}

						// save -> DB
						Database.insertMedicalRecord(patientID, Notes, visitdate);

						// render -> UI
						tablePanel.add(new JLabel(patientID));
						tablePanel.add(new JLabel(visitdate));
						tablePanel.add(new JLabel(Notes));
						tablePanel.revalidate();
						tablePanel.repaint();
					}
				});
			}
		}
	}

	// APPOINTMENTS
	class AppointmentPanel extends JPanel {
		private JPanel tablePanel = new JPanel();

		public AppointmentPanel() {
			setLayout(new BorderLayout());

			tablePanel.setLayout(new GridLayout(0, 3));
			tablePanel.setBackground(Color.WHITE);
			tablePanel.add(new JLabel("Patient Id"));
			tablePanel.add(new JLabel("Doctor Id"));
			tablePanel.add(new JLabel("Date"));
			add(tablePanel, BorderLayout.NORTH);

			// Load appointments from DB
			for (Database.AppointmentRow r : Database.getAllAppointments()) {
				tablePanel.add(new JLabel(r.patientId));
				tablePanel.add(new JLabel(r.doctorId));
				tablePanel.add(new JLabel(r.date));
			}

			JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
			bottom.setBackground(new Color(224, 255, 255));

			JTextField patientId = new JTextField(6);
			JTextField doctorId = new JTextField(6);
			JTextField date = new JTextField(10);
			JButton book = new JButton("Book Appointment");

			bottom.add(new JLabel("Patient's ID: "));
			bottom.add(patientId);
			bottom.add(new JLabel("Doctor's ID: "));
			bottom.add(doctorId);
			bottom.add(new JLabel("Date: "));
			bottom.add(date);
			bottom.add(book);

			add(bottom, BorderLayout.SOUTH);

			book.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String Patientid = patientId.getText().trim();
					String Doctorid = doctorId.getText().trim();
					String Appdate = date.getText().trim();

					if (Patientid.isEmpty() || Doctorid.isEmpty() || Appdate.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill all required fields!");
						return;
					}

					// save -> DB
					Database.insertAppointment(Patientid, Doctorid, Appdate);

					// render -> UI
					tablePanel.add(new JLabel(Patientid));
					tablePanel.add(new JLabel(Doctorid));
					tablePanel.add(new JLabel(Appdate));
					tablePanel.revalidate();
					tablePanel.repaint();
				}
			});
		}
	}

	public static void main(String[] args) {
		new Hospitalsystem();
	}
}
