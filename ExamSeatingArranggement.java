import java.awt.*;
import java.awt.event.*;
import java.awt.Panel;
import javax.swing.*;
import java.sql.*;



public class AdminLogin extends Frame implements ActionListener {

    Label nameLabel, passwordLabel;
    TextField nameField;
    TextField passwordField;
    Button loginButton, cancelButton;
    private String room;
    private String capacity;

    public AdminLogin() {
        setTitle("Admin Login");
        setLayout(new GridLayout(3, 2, 10, 10));
        setSize(500, 250);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        nameLabel = new Label("Name");
        add(nameLabel);

        nameField = new TextField(20);
        add(nameField);

        passwordLabel = new Label("Password");
        add(passwordLabel);

        passwordField = new TextField(20);
        add(passwordField);

        loginButton = new Button("Login");
        add(loginButton);
        loginButton.addActionListener(this);

        cancelButton = new Button("Cancel");
        add(cancelButton);
        cancelButton.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            final Connection[] con = {DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/examseating", "root", "as12345")};
            Statement stmt = con[0].createStatement();

            try (ResultSet rs = stmt.executeQuery(
                    String.format("SELECT * FROM admin WHERE name='%s' AND password='%s'",
                            nameField.getText(), passwordField.getText()))) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    JFrame examManagementFrame = new JFrame("Exam Management");
                    examManagementFrame.setLayout(new GridLayout(2, 2, 20, 20));
                    examManagementFrame.setSize(600, 400);

                    // Create buttons for exam management
                    Button createExamButton = new Button("Create Exam");
                    Button updateExamButton = new Button("Update Exam");
                    Button deleteExamButton = new Button("Delete Exam");
                    Button studentRegistrationButton = new Button("Student Registration");

                    // Add buttons to the frame
                    examManagementFrame.add(createExamButton);
                    examManagementFrame.add(updateExamButton);
                    examManagementFrame.add(deleteExamButton);
                    examManagementFrame.add(studentRegistrationButton);

                    // Add action listeners to the buttons
                    createExamButton.addActionListener(e15 -> {
                        JFrame createExamFrame = new JFrame("Create Exam");
                        createExamFrame.setLayout(new GridLayout(4, 4, 20, 20));
                        createExamFrame.setSize(600, 400);
                        Label examNameLabel = new Label("Exam Name");
                        TextField examNameField = new TextField();
                        Label examDateLabel = new Label("Exam Date (yyyy-mm-dd)");
                        TextField examDateField = new TextField();
                        Label roomLabel = new Label("Room");
                        TextField roomField = new TextField();
                        Label capacityLabel = new Label("Capacity");
                        TextField capacityField = new TextField();
                        Button createExamButton1 = new Button("Create Exam");
                        createExamButton1.addActionListener(e13 -> {
                            try {
                                String examName = examNameField.getText();
                                String examDate = examDateField.getText();
                                String room = roomField.getText();
                                int capacity = Integer.parseInt(capacityField.getText());

                                Class.forName("com.mysql.cj.jdbc.Driver");
                                Connection con1 = DriverManager.getConnection(
                                        "jdbc:mysql://localhost:3306/examseating", "root", "as12345");
                                Statement stmt1 = con1.createStatement();
                                int rowsAffected = stmt1.executeUpdate(
                                        String.format("INSERT INTO exam (name, date, room , capacity,duration) " +
                                                        "VALUES ('%s', '%s', '%s', %d, 0)",
                                                examName, examDate, room, capacity));
                                if (rowsAffected > 0) {
                                    System.out.println("Exam created successfully!");
                                    JOptionPane.showMessageDialog(createExamFrame,
                                            "Exam has been created successfully!");
                                    createExamFrame.dispose();
                                } else {
                                    System.out.println("Error creating exam, please try again later.");
                                    JOptionPane.showMessageDialog(createExamFrame,
                                            "Error creating exam. Please try again.");
                                }
                                con1.close();
                            } catch (Exception ex) {
                                System.out.println("Error creating exam: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        });
                        createExamFrame.add(examNameLabel);
                        createExamFrame.add(examNameField);
                        createExamFrame.add(examDateLabel);
                        createExamFrame.add(examDateField);
                        createExamFrame.add(roomLabel);
                        createExamFrame.add(roomField);
                        createExamFrame.add(capacityLabel);
                        createExamFrame.add(capacityField);
                        createExamFrame.add(createExamButton1);
                        createExamFrame.setVisible(true);
                    });

                    updateExamButton.addActionListener(e1 -> {
                        JFrame updateExamFrame = null;
                        try {
                            // create a new connection to the database
                            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/examseating", "root", "as12345");

                            updateExamFrame = new JFrame("Update Exam");
                            updateExamFrame.setLayout(new GridLayout(5, 2, 10, 10));
                            updateExamFrame.setSize(400, 250);

                            JLabel examIDLabel = new JLabel("Exam ID:");
                            JTextField examIDField = new JTextField();

                            JLabel examNameLabel = new JLabel("Exam Name:");
                            JTextField examNameField = new JTextField();

                            JLabel examDateLabel = new JLabel("Exam Date (yyyy-mm-dd):");
                            JTextField examDateField = new JTextField();

                            JLabel examRoomLabel = new JLabel("Room:");
                            JTextField examRoomField = new JTextField();

                            JLabel examCapacityLabel = new JLabel("Capacity:");
                            JTextField examCapacityField = new JTextField();

                            JButton updateExamButton1 = new JButton("Update Exam");

                            JFrame finalUpdateExamFrame = updateExamFrame;
                            updateExamButton1.addActionListener(e13 -> {
                                try {
                                    // get the values from the text fields
                                    int examID = Integer.parseInt(examIDField.getText());
                                    String examName = examNameField.getText();
                                    String examDate = examDateField.getText();
                                    String examRoom = examRoomField.getText();
                                    int examCapacity = Integer.parseInt(examCapacityField.getText());

                                    // update the exam record in the database
                                    String updateQuery = "UPDATE exam SET name = ?, date = ?, room = ?, capacity = ? WHERE id = ?";
                                    PreparedStatement pstmt = con1.prepareStatement(updateQuery);
                                    pstmt.setString(1, examName);
                                    pstmt.setString(2, examDate);
                                    pstmt.setString(3, examRoom);
                                    pstmt.setInt(4, examCapacity);
                                    pstmt.setInt(5, examID);
                                    int rowsUpdated = pstmt.executeUpdate();

                                    // show a success message to the user
                                    if (rowsUpdated > 0)
                                        JOptionPane.showMessageDialog(finalUpdateExamFrame, "Exam updated successfully!");
                                    else
                                        JOptionPane.showMessageDialog(finalUpdateExamFrame, "Error updating exam. Please try again.");

                                    // close the statement and result set
                                    pstmt.close();
                                    con1.close(); // close the database connection
                                } catch (Exception ex) {
                                    System.out.println("Error updating exam:" + ex.getMessage());
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(finalUpdateExamFrame, "Error updating exam. Please try again.");
                                }
                            });

                            updateExamFrame.add(examIDLabel);
                            updateExamFrame.add(examIDField);
                            updateExamFrame.add(examNameLabel);
                            updateExamFrame.add(examNameField);
                            updateExamFrame.add(examDateLabel);
                            updateExamFrame.add(examDateField);
                            updateExamFrame.add(examRoomLabel);
                            updateExamFrame.add(examRoomField);
                            updateExamFrame.add(examCapacityLabel);
                            updateExamFrame.add(examCapacityField);
                            updateExamFrame.add(updateExamButton1);
                            updateExamFrame.setVisible(true);
                        } catch (SQLException ex) {
                            System.out.println("Error creating database connection:" + ex.getMessage());
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(updateExamFrame, "Error creating database connection. Please try again.");
                        }
                    });

                    deleteExamButton.addActionListener(e1 -> {
                        // Create delete exam frame
                        Frame deleteFrame = new Frame("Delete Exam");
                        deleteFrame.setSize(300, 200);
                        deleteFrame.setLayout(null);

                        // Create exam ID label and text field
                        Label examIdLabel = new Label("Enter Exam ID:");
                        examIdLabel.setBounds(50, 50, 100, 20);
                        deleteFrame.add(examIdLabel);

                        TextField examIdField = new TextField();
                        examIdField.setBounds(150, 50, 100, 20);
                        deleteFrame.add(examIdField);

                        // Create delete button
                        Button deleteButton = new Button("Delete");
                        deleteButton.setBounds(100, 100, 100, 30);
                        deleteFrame.add(deleteButton);

                        // Add ActionListener to delete button
                        deleteButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                String examId = examIdField.getText();

                                if (examId.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Please enter exam ID.");
                                    return;
                                }

                                try {
                                    // Connect to the database
                                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/examseating", "root", "as12345");

                                    // Prepare the DELETE statement
                                    String query = "DELETE FROM exam WHERE id = ?";
                                    PreparedStatement stmt = con.prepareStatement(query);
                                    stmt.setInt(1, Integer.parseInt(examId));

                                    // Execute the statement
                                    int rowsDeleted = stmt.executeUpdate();

                                    // Display a success message
                                    if (rowsDeleted > 0)
                                        JOptionPane.showMessageDialog(null, "Exam deleted successfully.");
                                    else JOptionPane.showMessageDialog(null, "No exam found with ID " + examId);

                                    // Close the database connection
                                    con.close();

                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(null, "Invalid exam ID. Please enter a valid integer.");
                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null, "Error deleting exam: " + ex.getMessage());
                                }
                            }
                        });

                        // Display delete exam frame
                        deleteFrame.setVisible(true);
                    });


                    studentRegistrationButton.addActionListener(e12 -> {
                        Button studentRegistrationButton1 = new Button("Student Registration");
                        studentRegistrationButton1.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                // create new frame for student registration
                                Frame studentRegistrationFrame = new Frame("Student Registration");
                                studentRegistrationFrame.setLayout(new GridLayout(5, 2));

                                // add components for student name
                                Label nameLabel = new Label("Student Name:");
                                TextField nameTextField = new TextField(50);
                                studentRegistrationFrame.add(nameLabel);
                                studentRegistrationFrame.add(nameTextField);

                                // add components for exam id
                                Label examIdLabel = new Label("Exam ID:");
                                TextField examIdTextField = new TextField(50);
                                studentRegistrationFrame.add(examIdLabel);
                                studentRegistrationFrame.add(examIdTextField);

                                // add components for seat number
                                Label seatNumberLabel = new Label("Seat Number:");
                                TextField seatNumberTextField = new TextField(50);
                                studentRegistrationFrame.add(seatNumberLabel);
                                studentRegistrationFrame.add(seatNumberTextField);

                                // add submit button
                                Button submitButton = new Button("Submit");
                                submitButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        // get input values
                                        String name = nameTextField.getText();
                                        int examId = Integer.parseInt(examIdTextField.getText());
                                        int seatNumber = Integer.parseInt(seatNumberTextField.getText());

                                        // check if exam exists
                                        boolean examExists = false;
                                        for (Exam exam : exams)
                                            if (exam.getId() == examId) {
                                                examExists = true;
                                                // check if seat number is available
                                                if (seatNumber > exam.getCapacity()) {
                                                    // show error message
                                                    JOptionPane.showMessageDialog(studentRegistrationFrame, "Seat not available");
                                                    return;
                                                } else {
                                                    // check if seat number is already assigned
                                                    for (Student student : students)
                                                        if (student.getExamId() == examId && student.getSeatNumber() == seatNumber) {
                                                            // show error message
                                                            JOptionPane.showMessageDialog(studentRegistrationFrame, "Seat not available");
                                                            return;
                                                        }
                                                    // add student to database
                                                    Student student = new Student(name, examId, seatNumber);
                                                    student.add(student);
                                                    // show success message
                                                    JOptionPane.showMessageDialog(studentRegistrationFrame, "Student registered successfully");
                                                    // close frame
                                                    studentRegistrationFrame.dispose();
                                                    return;
                                                }
                                            }
                                        // show error message if exam doesn't exist
                                        if (!examExists)
                                            JOptionPane.showMessageDialog(studentRegistrationFrame, "Exam not found");
                                    }
                                });
                                studentRegistrationFrame.add(submitButton);

                                // add cancel button
                                Button cancelButton = new Button("Cancel");
                                cancelButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        // close frame
                                        studentRegistrationFrame.dispose();
                                    }
                                });
                                studentRegistrationFrame.add(cancelButton);

                                // set frame properties and make visible
                                studentRegistrationFrame.setSize(400, 200);
                                studentRegistrationFrame.setVisible(true);
                            }
                        });
                    });

                    // Make the frame visible
                    examManagementFrame.setVisible(true);
                } else JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
            }
            con[0].close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        else if (e.getSource() == cancelButton) System.exit(0);
    }
    

    public static void main(String[] args) {
        new AdminLogin();
    }
}
