import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class Main {
    private static CurrencyDAO currencyDAO = new CurrencyDAO();
    private static UserDAO userDAO = new UserDAO();
    private static PositionDAO positionDAO = new PositionDAO();
    private static SuppliersDAO suppliersDAO = new SuppliersDAO();
    private static TransactionsDAO transactionsDAO = new TransactionsDAO();
    private static EmployeesDAO employeesDAO = new EmployeesDAO();

    public static void main(String[] args) {
        JFrame frame = new JFrame("WELCOME TO CEB");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(0x0d47a1), width, height, new Color(0x00c853));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        gradientPanel.setLayout(new BorderLayout());
        frame.setContentPane(gradientPanel);

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome to Currency Exchange Bank.", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(new Color(0xf9f6ee));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(welcomeLabel);
        topPanel.add(Box.createVerticalStrut(20));

        JLabel popularCurrenciesLabel = new JLabel("The most popular currencies:", JLabel.CENTER);
        popularCurrenciesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        popularCurrenciesLabel.setForeground(Color.WHITE);
        popularCurrenciesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(popularCurrenciesLabel);

        gradientPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gradientPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel currencyPanel = new JPanel();
        currencyPanel.setOpaque(false);
        currencyPanel.setLayout(new BoxLayout(currencyPanel, BoxLayout.Y_AXIS));
        currencyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        try {
            List<Currency> topCurrencies = currencyDAO.getTopCurrencies();
            for (Currency currency : topCurrencies) {
                JLabel currencyNameLabel = new JLabel("Name: " + currency.getCurrencyName(), JLabel.CENTER);
                currencyNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                currencyNameLabel.setForeground(Color.WHITE);
                currencyNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                currencyPanel.add(currencyNameLabel);

                JLabel buyRateLabel = new JLabel("Buy rate: " + currency.getBuyRate(), JLabel.CENTER);
                buyRateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                buyRateLabel.setForeground(Color.WHITE);
                buyRateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                currencyPanel.add(buyRateLabel);

                JLabel sellRateLabel = new JLabel("Sell rate: " + currency.getSellRate(), JLabel.CENTER);
                sellRateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                sellRateLabel.setForeground(Color.WHITE);
                sellRateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                currencyPanel.add(sellRateLabel);

                currencyPanel.add(Box.createVerticalStrut(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        centerPanel.add(currencyPanel);
        centerPanel.add(Box.createVerticalGlue());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Registration");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 18));
        registerButton.setFont(new Font("Arial", Font.PLAIN, 18));
        bottomPanel.add(loginButton);
        bottomPanel.add(registerButton);
        gradientPanel.add(bottomPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginForm(frame);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegistrationForm(frame);
            }
        });

        frame.setVisible(true);
    }

    private static void showLoginForm(JFrame parentFrame) {
        JDialog loginDialog = new JDialog(parentFrame, "Login", true);
        loginDialog.setSize(250, 200);
        loginDialog.setLayout(new GridLayout(3, 2));
        loginDialog.getContentPane().setBackground(new Color(0x1b1b1b));

        JLabel userNameLabel = new JLabel("Username:");
        userNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userNameLabel.setForeground(new Color(0xffffff));
        JTextField userNameField = new JTextField();
        userNameField.setToolTipText("Enter your username");
        userNameField.setPreferredSize(new Dimension(150, 30));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(0xffffff));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setToolTipText("Enter your password");
        passwordField.setPreferredSize(new Dimension(150, 30));

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(150, 30));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    User user = userDAO.getUser(userName, password);
                    if (user != null) {
                        JOptionPane.showMessageDialog(loginDialog, "Login successful.");
                        loginDialog.dispose();
                        parentFrame.dispose();
                        showAdminWindow();
                    } else {
                        JOptionPane.showMessageDialog(loginDialog, "Invalid username or password.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(loginDialog, "Error during login.");
                }
            }
        });

        loginDialog.add(userNameLabel);
        loginDialog.add(userNameField);
        loginDialog.add(passwordLabel);
        loginDialog.add(passwordField);
        loginDialog.add(new JLabel());
        loginDialog.add(loginButton);

        loginDialog.setVisible(true);
    }

    private static void showRegistrationForm(JFrame parentFrame) {
        JDialog registerDialog = new JDialog(parentFrame, "Registration", true);
        registerDialog.setSize(400, 300);
        registerDialog.setLayout(new GridLayout(4, 2));
        registerDialog.getContentPane().setBackground(new Color(0x1b1b1b));

        JLabel userNameLabel = new JLabel("Username:");
        userNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userNameLabel.setForeground(new Color(0xffffff));
        JTextField userNameField = new JTextField();
        userNameField.setToolTipText("Enter your username");
        userNameField.setPreferredSize(new Dimension(150, 30));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLabel.setForeground(new Color(0xffffff));
        JTextField emailField = new JTextField();
        emailField.setToolTipText("Enter your email");
        emailField.setPreferredSize(new Dimension(150, 30));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(0xffffff));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setToolTipText("Enter your password");
        passwordLabel.setPreferredSize(new Dimension(150, 30));

        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(150, 30));

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    User user = new User(userName, email, password);
                    userDAO.insertUser(user);
                    JOptionPane.showMessageDialog(registerDialog, "Registration successful.");
                    registerDialog.dispose();
                    parentFrame.dispose();
                    showAdminWindow();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(registerDialog, "Error during registration.");
                }
            }
        });

        registerDialog.add(userNameLabel);
        registerDialog.add(userNameField);
        registerDialog.add(emailLabel);
        registerDialog.add(emailField);
        registerDialog.add(passwordLabel);
        registerDialog.add(passwordField);
        registerDialog.add(new JLabel());
        registerDialog.add(registerButton);

        registerDialog.setVisible(true);
    }

    private static void showAdminWindow() {
        JFrame adminFrame = new JFrame("Admin Panel");
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setSize(750, 500);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("D:/Asgard/background.jpg");
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        adminFrame.setContentPane(backgroundPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton currencyButton = new JButton("Currency");
        JButton positionButton = new JButton("Position");
        JButton suppliersButton = new JButton("Suppliers");
        JButton transactionsButton = new JButton("Transaction");
        JButton employeesButton = new JButton("Employees");

        Dimension buttonSize = new Dimension(140, 40);
        currencyButton.setPreferredSize(buttonSize);
        currencyButton.setForeground(new Color(0xffffff));
        currencyButton.setBackground(new Color(0x000000));
        positionButton.setPreferredSize(buttonSize);
        positionButton.setForeground(new Color(0xffffff));
        positionButton.setBackground(new Color(0x000000));
        suppliersButton.setPreferredSize(buttonSize);
        suppliersButton.setForeground(new Color(0xffffff));
        suppliersButton.setBackground(new Color(0x000000));
        transactionsButton.setPreferredSize(buttonSize);
        transactionsButton.setForeground(new Color(0xffffff));
        transactionsButton.setBackground(new Color(0x000000));
        employeesButton.setPreferredSize(buttonSize);
        employeesButton.setForeground(new Color(0xffffff));
        employeesButton.setBackground(new Color(0x000000));

        buttonPanel.add(currencyButton);
        buttonPanel.add(positionButton);
        buttonPanel.add(suppliersButton);
        buttonPanel.add(transactionsButton);
        buttonPanel.add(employeesButton);

        backgroundPanel.add(buttonPanel, BorderLayout.NORTH);

        currencyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCurrencyActions(adminFrame);
            }
        });

        positionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPositionActions(adminFrame);
            }
        });

        suppliersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSuppliersActions(adminFrame);
            }
        });

        transactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransactionsActions(adminFrame);
            }
        });

        employeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEmployeesActions(adminFrame);
            }
        });

        adminFrame.setVisible(true);
    }
    private static void showCurrencyActions(JFrame adminFrame) {
        JDialog currencyDialog = new JDialog(adminFrame, "Currency Actions", true);
        currencyDialog.setSize(400, 300);
        currencyDialog.setLayout(new GridLayout(4, 1));

        JButton addButton = new JButton("Add Currency");
        JButton editButton = new JButton("Edit Currency");
        JButton deleteButton = new JButton("Delete Currency");
        JButton searchButton = new JButton("Search Currency");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddCurrencyDialog(currencyDialog);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEditCurrencyDialog(currencyDialog);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteCurrencyDialog(currencyDialog);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchCurrencyDialog(currencyDialog);
            }
        });

        currencyDialog.add(addButton);
        addButton.setBackground(new Color(0x000000));
        addButton.setForeground(new Color(0xffffff));
        currencyDialog.add(editButton);
        editButton.setBackground(new Color(0x000000));
        editButton.setForeground(new Color(0xffffff));
        currencyDialog.add(deleteButton);
        deleteButton.setBackground(new Color(0x000000));
        deleteButton.setForeground(new Color(0xffffff));
        currencyDialog.add(searchButton);
        searchButton.setBackground(new Color(0x000000));
        searchButton.setForeground(new Color(0xffffff));

        currencyDialog.setVisible(true);
    }

    private static void showAddCurrencyDialog(JDialog parentDialog) {
        JDialog addCurrencyDialog = new JDialog(parentDialog, "Add Currency", true);
        addCurrencyDialog.setSize(300, 200);
        addCurrencyDialog.setLayout(new GridLayout(6, 2));

        JLabel currencyNameLabel = new JLabel("Currency Name:");
        JTextField currencyNameField = new JTextField();
        currencyNameField.setToolTipText("Enter currency name");

        JLabel buyRateLabel = new JLabel("Buy Rate:");
        JTextField buyRateField = new JTextField();
        buyRateField.setToolTipText("Enter buy rate");

        JLabel sellRateLabel = new JLabel("Sell Rate:");
        JTextField sellRateField = new JTextField();
        sellRateField.setToolTipText("Enter sell rate");

        JLabel yearOfIssueLabel = new JLabel("Year of Issue:");
        JTextField yearOfIssueField = new JTextField();
        yearOfIssueField.setToolTipText("Enter year of issue");

        JLabel accountLabel = new JLabel("Account:");
        JTextField accountField = new JTextField();
        accountField.setToolTipText("Enter account");

        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        addCurrencyDialog.add(currencyNameLabel);
        addCurrencyDialog.add(currencyNameField);
        addCurrencyDialog.add(buyRateLabel);
        addCurrencyDialog.add(buyRateField);
        addCurrencyDialog.add(sellRateLabel);
        addCurrencyDialog.add(sellRateField);
        addCurrencyDialog.add(yearOfIssueLabel);
        addCurrencyDialog.add(yearOfIssueField);
        addCurrencyDialog.add(accountLabel);
        addCurrencyDialog.add(accountField);
        addCurrencyDialog.add(cancelButton);
        addCurrencyDialog.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currencyName = currencyNameField.getText();
                BigDecimal buyRate = new BigDecimal(buyRateField.getText());
                BigDecimal sellRate = new BigDecimal(sellRateField.getText());
                int yearOfIssue = Integer.parseInt(yearOfIssueField.getText());
                String account = accountField.getText();

                try {
                    Currency currency = new Currency(currencyName, buyRate, sellRate, yearOfIssue, account);
                    currencyDAO.insertCurrency(currency);
                    JOptionPane.showMessageDialog(addCurrencyDialog, "Currency added successfully.");
                    addCurrencyDialog.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(addCurrencyDialog, "Error adding currency.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCurrencyDialog.dispose();
            }
        });

        addCurrencyDialog.setVisible(true);
    }

    private static void showEditCurrencyDialog(JDialog parentDialog) {
        JDialog editCurrencyDialog = new JDialog(parentDialog, "Edit Currency", true);
        editCurrencyDialog.setSize(300, 200);
        editCurrencyDialog.setLayout(new GridLayout(6, 2));

        JLabel currencyNameLabel = new JLabel("Currency Name:");
        JTextField currencyNameField = new JTextField();
        currencyNameField.setToolTipText("Enter new currency name");

        JLabel buyRateLabel = new JLabel("Buy Rate:");
        JTextField buyRateField = new JTextField();
        buyRateField.setToolTipText("Enter new buy rate");

        JLabel sellRateLabel = new JLabel("Sell Rate:");
        JTextField sellRateField = new JTextField();
        sellRateField.setToolTipText("Enter new sell rate");

        JLabel yearOfIssueLabel = new JLabel("Year of Issue:");
        JTextField yearOfIssueField = new JTextField();
        yearOfIssueField.setToolTipText("Enter new year of issue");

        JLabel accountLabel = new JLabel("Account:");
        JTextField accountField = new JTextField();
        accountField.setToolTipText("Enter new account");

        JButton editButton = new JButton("Edit");
        JButton cancelButton = new JButton("Cancel");

        editCurrencyDialog.add(currencyNameLabel);
        editCurrencyDialog.add(currencyNameField);
        editCurrencyDialog.add(buyRateLabel);
        editCurrencyDialog.add(buyRateField);
        editCurrencyDialog.add(sellRateLabel);
        editCurrencyDialog.add(sellRateField);
        editCurrencyDialog.add(yearOfIssueLabel);
        editCurrencyDialog.add(yearOfIssueField);
        editCurrencyDialog.add(accountLabel);
        editCurrencyDialog.add(accountField);
        editCurrencyDialog.add(cancelButton);
        editCurrencyDialog.add(editButton);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currencyName = currencyNameField.getText();
                BigDecimal buyRate = new BigDecimal(buyRateField.getText());
                BigDecimal sellRate = new BigDecimal(sellRateField.getText());
                int yearOfIssue = Integer.parseInt(yearOfIssueField.getText());
                String account = accountField.getText();

                try {
                    Currency currency = new Currency(currencyName, buyRate, sellRate, yearOfIssue, account);
                    currencyDAO.updateCurrency(currency);
                    JOptionPane.showMessageDialog(editCurrencyDialog, "Currency updated successfully.");
                    editCurrencyDialog.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(editCurrencyDialog, "Error updating currency.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editCurrencyDialog.dispose();
            }
        });

        editCurrencyDialog.setVisible(true);
    }

    private static void showDeleteCurrencyDialog(JDialog parentDialog) {
        JDialog deleteCurrencyDialog = new JDialog(parentDialog, "Delete Currency", true);
        deleteCurrencyDialog.setSize(300, 100);
        deleteCurrencyDialog.setLayout(new FlowLayout());

        JLabel currencyNameLabel = new JLabel("Currency Name:");
        JTextField currencyNameField = new JTextField(15);
        currencyNameField.setToolTipText("Enter currency name to delete");

        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        deleteCurrencyDialog.add(currencyNameLabel);
        deleteCurrencyDialog.add(currencyNameField);
        deleteCurrencyDialog.add(cancelButton);
        deleteCurrencyDialog.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currencyName = currencyNameField.getText();

                try {
                    currencyDAO.deleteCurrency(currencyName);
                    JOptionPane.showMessageDialog(deleteCurrencyDialog, "Currency deleted successfully.");
                    deleteCurrencyDialog.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(deleteCurrencyDialog, "Error deleting currency.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCurrencyDialog.dispose();
            }
        });

        deleteCurrencyDialog.setVisible(true);
    }

    private static void showSearchCurrencyDialog(JDialog parentDialog) {
        JDialog searchCurrencyDialog = new JDialog(parentDialog, "Search Currency", true);
        searchCurrencyDialog.setSize(300, 150);
        searchCurrencyDialog.setLayout(new FlowLayout());

        JLabel letterLabel = new JLabel("Enter Letter:");
        JTextField letterField = new JTextField(15);
        letterField.setToolTipText("Enter a letter to search for currencies");

        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");

        searchCurrencyDialog.add(letterLabel);
        searchCurrencyDialog.add(letterField);
        searchCurrencyDialog.add(cancelButton);
        searchCurrencyDialog.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String letter = letterField.getText();

                try {
                    List<Currency> currencies = currencyDAO.getCurrenciesContainingLetter(letter);
                    StringBuilder result = new StringBuilder("Currencies containing '" + letter + "':\n");
                    for (Currency currency : currencies) {
                        result.append(currency.getCurrencyName()).append(" - Buy Rate: ").append(currency.getBuyRate())
                                .append(", Sell Rate: ").append(currency.getSellRate()).append("\n");
                    }
                    JOptionPane.showMessageDialog(searchCurrencyDialog, result.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(searchCurrencyDialog, "Error searching currencies.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCurrencyDialog.dispose();
            }
        });

        searchCurrencyDialog.setVisible(true);
    }

    private static void showPositionActions(JFrame adminFrame) {
        JDialog positionDialog = new JDialog(adminFrame, "Position Actions", true);
        positionDialog.setSize(400, 300);
        positionDialog.setLayout(new GridLayout(4, 1));

        JButton addButton = new JButton("Add Position");
        JButton editButton = new JButton("Edit Position");
        JButton deleteButton = new JButton("Delete Position");
        JButton searchButton = new JButton("Search Position");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddPositionDialog(positionDialog);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEditPositionDialog(positionDialog);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeletePositionDialog(positionDialog);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPositionSearchDialog(positionDialog);
            }
        });

        positionDialog.add(addButton);
        addButton.setBackground(new Color(0x000000));
        addButton.setForeground(new Color(0xffffff));
        positionDialog.add(editButton);
        editButton.setBackground(new Color(0x000000));
        editButton.setForeground(new Color(0xffffff));
        positionDialog.add(deleteButton);
        deleteButton.setBackground(new Color(0x000000));
        deleteButton.setForeground(new Color(0xffffff));
        positionDialog.add(searchButton);
        searchButton.setBackground(new Color(0x000000));
        searchButton.setForeground(new Color(0xffffff));

        positionDialog.setVisible(true);
    }

    private static void showAddPositionDialog(JDialog parentDialog) {
        JDialog addPositionDialog = new JDialog(parentDialog, "Add Position", true);
        addPositionDialog.setSize(300, 150);
        addPositionDialog.setLayout(new GridLayout(3, 2));

        JLabel positionNameLabel = new JLabel("Position Name:");
        JTextField positionNameField = new JTextField();
        positionNameField.setToolTipText("Enter position name");

        JLabel positionStatusLabel = new JLabel("Position Status:");
        JTextField positionStatusField = new JTextField();
        positionStatusField.setToolTipText("Enter position status");

        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        addPositionDialog.add(positionNameLabel);
        addPositionDialog.add(positionNameField);
        addPositionDialog.add(positionStatusLabel);
        addPositionDialog.add(positionStatusField);
        addPositionDialog.add(cancelButton);
        addPositionDialog.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String positionName = positionNameField.getText();
                String positionStatus = positionStatusField.getText();

                try {
                    Position position = new Position(positionName, positionStatus);
                    positionDAO.insertPosition(position);
                    JOptionPane.showMessageDialog(addPositionDialog, "Position added successfully.");
                    addPositionDialog.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(addPositionDialog, "Error adding position.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPositionDialog.dispose();
            }
        });

        addPositionDialog.setVisible(true);
    }

    private static void showEditPositionDialog(JDialog parentDialog) {
        JDialog editPositionDialog = new JDialog(parentDialog, "Edit Position", true);
        editPositionDialog.setSize(300, 150);
        editPositionDialog.setLayout(new GridLayout(3, 2));

        JLabel positionNameLabel = new JLabel("Position Name:");
        JTextField positionNameField = new JTextField();
        positionNameField.setToolTipText("Enter new position name");

        JLabel positionStatusLabel = new JLabel("Position Status:");
        JTextField positionStatusField = new JTextField();
        positionStatusField.setToolTipText("Enter new position status");

        JButton editButton = new JButton("Edit");
        JButton cancelButton = new JButton("Cancel");

        editPositionDialog.add(positionNameLabel);
        editPositionDialog.add(positionNameField);
        editPositionDialog.add(positionStatusLabel);
        editPositionDialog.add(positionStatusField);
        editPositionDialog.add(cancelButton);
        editPositionDialog.add(editButton);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String positionName = positionNameField.getText();
                String positionStatus = positionStatusField.getText();

                try {
                    Position position = new Position(positionName, positionStatus);
                    positionDAO.updatePosition(position);
                    JOptionPane.showMessageDialog(editPositionDialog, "Position updated successfully.");
                    editPositionDialog.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(editPositionDialog, "Error updating position.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editPositionDialog.dispose();
            }
        });

        editPositionDialog.setVisible(true);
    }

    private static void showDeletePositionDialog(JDialog parentDialog) {
        JDialog deletePositionDialog = new JDialog(parentDialog, "Delete Position", true);
        deletePositionDialog.setSize(300, 100);
        deletePositionDialog.setLayout(new GridLayout(2, 2));

        JLabel positionNameLabel = new JLabel("Position Name:");
        JTextField positionNameField = new JTextField();
        positionNameField.setToolTipText("Enter position name");

        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        deletePositionDialog.add(positionNameLabel);
        deletePositionDialog.add(positionNameField);
        deletePositionDialog.add(cancelButton);
        deletePositionDialog.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String positionName = positionNameField.getText();

                try {
                    positionDAO.deletePosition(positionName);
                    JOptionPane.showMessageDialog(deletePositionDialog, "Position deleted successfully.");
                    deletePositionDialog.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(deletePositionDialog, "Error deleting position.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePositionDialog.dispose();
            }
        });

        deletePositionDialog.setVisible(true);
    }

    private static void showPositionSearchDialog(JDialog parentDialog) {
        JDialog searchPositionDialog = new JDialog(parentDialog, "Search Position", true);
        searchPositionDialog.setSize(300, 150);
        searchPositionDialog.setLayout(new FlowLayout());

        JLabel letterLabel = new JLabel("Enter Letter:");
        JTextField letterField = new JTextField(15);
        letterField.setToolTipText("Enter a letter to search for positions");

        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");

        searchPositionDialog.add(letterLabel);
        searchPositionDialog.add(letterField);
        searchPositionDialog.add(cancelButton);
        searchPositionDialog.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String letter = letterField.getText();

                try {
                    List<Position> positions = positionDAO.getPositionsStartingWith(letter);
                    StringBuilder result = new StringBuilder("Positions containing '" + letter + "':\n");
                    for (Position position : positions) {
                        result.append(position.getPositionName()).append(" - Position Status: ").append(position.getPositionStatus()).append("\n");
                    }
                    JOptionPane.showMessageDialog(searchPositionDialog, result.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(searchPositionDialog, "Error searching currencies.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPositionDialog.dispose();
            }
        });

        searchPositionDialog.setVisible(true);
    }

    private static void showSuppliersActions(JFrame adminFrame) {
        JDialog suppliersDialog = new JDialog(adminFrame, "Suppliers Actions", true);
        suppliersDialog.setSize(400, 300);
        suppliersDialog.setLayout(new GridLayout(4, 1));

        JButton addButton = new JButton("Add Supplier");
        JButton editButton = new JButton("Edit Supplier");
        JButton deleteButton = new JButton("Delete Supplier");
        JButton searchButton = new JButton("Search Button");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddSuppliersDialog(suppliersDialog);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEditSuppliersDialog(suppliersDialog);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteSuppliersDialog(suppliersDialog);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchSuppliersDialog(suppliersDialog);
            }
        });

        suppliersDialog.add(addButton);
        addButton.setBackground(new Color(0x000000));
        addButton.setForeground(new Color(0xffffff));
        suppliersDialog.add(editButton);
        editButton.setBackground(new Color(0x000000));
        editButton.setForeground(new Color(0xffffff));
        suppliersDialog.add(deleteButton);
        deleteButton.setBackground(new Color(0x000000));
        deleteButton.setForeground(new Color(0xffffff));
        suppliersDialog.add(searchButton);
        searchButton.setBackground(new Color(0x000000));
        searchButton.setForeground(new Color(0xffffff));

        suppliersDialog.setVisible(true);
    }
    private static void showAddSuppliersDialog(JDialog parentDialog) {
        JDialog addSupplierDialog = new JDialog(parentDialog, "Add Supplier", true);
        addSupplierDialog.setSize(300, 200);
        addSupplierDialog.setLayout(new GridLayout(5, 2));

        JLabel accountLabel = new JLabel("Account:");
        JTextField accountField = new JTextField();
        accountField.setToolTipText("Enter supplier account");

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setToolTipText("Enter supplier phone number");

        JLabel contactPersonLabel = new JLabel("Contact Person:");
        JTextField contactPersonField = new JTextField();
        contactPersonField.setToolTipText("Enter supplier contact person");

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        emailField.setToolTipText("Enter supplier email");

        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        addSupplierDialog.add(accountLabel);
        addSupplierDialog.add(accountField);
        addSupplierDialog.add(phoneNumberLabel);
        addSupplierDialog.add(phoneNumberField);
        addSupplierDialog.add(contactPersonLabel);
        addSupplierDialog.add(contactPersonField);
        addSupplierDialog.add(emailLabel);
        addSupplierDialog.add(emailField);
        addSupplierDialog.add(cancelButton);
        addSupplierDialog.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText();
                String phoneNumber = phoneNumberField.getText();
                String contactPerson = contactPersonField.getText();
                String email = emailField.getText();

                try {
                    Suppliers supplier = new Suppliers(account, phoneNumber, contactPerson, email);
                    suppliersDAO.insertSupplier(supplier);
                    JOptionPane.showMessageDialog(addSupplierDialog, "Supplier added successfully.");
                    addSupplierDialog.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(addSupplierDialog, "Error adding supplier.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSupplierDialog.dispose();
            }
        });

        addSupplierDialog.setVisible(true);
    }

    private static void showEditSuppliersDialog(JDialog parentDialog) {
        JDialog editSupplierDialog = new JDialog(parentDialog, "Edit Supplier", true);
        editSupplierDialog.setSize(300, 200);
        editSupplierDialog.setLayout(new GridLayout(5, 2));

        JLabel accountLabel = new JLabel("Supplier Account:");
        JTextField accountField = new JTextField();
        accountField.setToolTipText("Enter supplier account");

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setToolTipText("Enter new phone number");

        JLabel contactPersonLabel = new JLabel("Contact Person:");
        JTextField contactPersonField = new JTextField();
        contactPersonField.setToolTipText("Enter new contact person");

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        emailField.setToolTipText("Enter new email");

        JButton editButton = new JButton("Edit");
        JButton cancelButton = new JButton("Cancel");

        editSupplierDialog.add(accountLabel);
        editSupplierDialog.add(accountField);
        editSupplierDialog.add(phoneNumberLabel);
        editSupplierDialog.add(phoneNumberField);
        editSupplierDialog.add(contactPersonLabel);
        editSupplierDialog.add(contactPersonField);
        editSupplierDialog.add(emailLabel);
        editSupplierDialog.add(emailField);
        editSupplierDialog.add(cancelButton);
        editSupplierDialog.add(editButton);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText();
                String phoneNumber = phoneNumberField.getText();
                String contactPerson = contactPersonField.getText();
                String email = emailField.getText();

                try {
                    Suppliers supplier = new Suppliers(account, phoneNumber, contactPerson, email);
                    suppliersDAO.updateSupplier(supplier);
                    JOptionPane.showMessageDialog(editSupplierDialog, "Supplier updated successfully.");
                    editSupplierDialog.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(editSupplierDialog, "Error updating supplier.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSupplierDialog.dispose();
            }
        });

        editSupplierDialog.setVisible(true);
    }

    private static void showDeleteSuppliersDialog(JDialog parentDialog) {
        JDialog deleteSupplierDialog = new JDialog(parentDialog, "Delete Supplier", true);
        deleteSupplierDialog.setSize(300, 100);
        deleteSupplierDialog.setLayout(new GridLayout(2, 2));

        JLabel accountLabel = new JLabel("Supplier Account:");
        JTextField accountField = new JTextField();
        accountField.setToolTipText("Enter supplier account");

        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        deleteSupplierDialog.add(accountLabel);
        deleteSupplierDialog.add(accountField);
        deleteSupplierDialog.add(cancelButton);
        deleteSupplierDialog.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText();

                try {
                    suppliersDAO.deleteSupplier(account);
                    JOptionPane.showMessageDialog(deleteSupplierDialog, "Supplier deleted successfully.");
                    deleteSupplierDialog.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(deleteSupplierDialog, "Error deleting supplier.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSupplierDialog.dispose();
            }
        });

        deleteSupplierDialog.setVisible(true);
    }

    private static void showSearchSuppliersDialog(JDialog parentDialog) {
        JDialog searchSupplierDialog = new JDialog(parentDialog, "Search Supplier", true);
        searchSupplierDialog.setSize(300, 150);
        searchSupplierDialog.setLayout(new FlowLayout());

        JLabel letterLabel = new JLabel("Enter Letter:");
        JTextField letterField = new JTextField(15);
        letterField.setToolTipText("Enter a letter to search for suppliers");

        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");

        searchSupplierDialog.add(letterLabel);
        searchSupplierDialog.add(letterField);
        searchSupplierDialog.add(cancelButton);
        searchSupplierDialog.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String letter = letterField.getText();

                try {
                    List<Suppliers> suppliers = suppliersDAO.searchSuppliersByStartingLetter(letter);
                    StringBuilder result = new StringBuilder("Contact Person containing '" + letter + "':\n");
                    for (Suppliers suppliers1 : suppliers) {
                        result.append(suppliers1.getContactPerson()).append(" - Account: ").append(suppliers1.getAccount()).append("Phone Number:").append(suppliers1.getPhoneNumber()).append("Email:").append("\n");
                    }
                    JOptionPane.showMessageDialog(searchSupplierDialog, result.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(searchSupplierDialog, "Error searching currencies.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchSupplierDialog.dispose();
            }
        });

        searchSupplierDialog.setVisible(true);
    }

    private static void showTransactionsActions(JFrame adminFrame) {
        JDialog transactionsDialog = new JDialog(adminFrame, "Transaction Actions", true);
        transactionsDialog.setSize(400, 300);
        transactionsDialog.setLayout(new GridLayout(4, 1));

        JButton addButton = new JButton("Add Transaction");
        JButton editButton = new JButton("Edit Transaction");
        JButton deleteButton = new JButton("Delete Transaction");
        JButton searchButton = new JButton("Search Transactions");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddTransactionsDialog(transactionsDialog);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEditTransactionsDialog(transactionsDialog);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteTransactionsDialog(transactionsDialog);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransactionSearchDialog(transactionsDialog);
            }
        });

        transactionsDialog.add(addButton);
        addButton.setBackground(new Color(0x000000));
        addButton.setForeground(new Color(0xffffff));
        transactionsDialog.add(editButton);
        editButton.setBackground(new Color(0x000000));
        editButton.setForeground(new Color(0xffffff));
        transactionsDialog.add(deleteButton);
        deleteButton.setBackground(new Color(0x000000));
        deleteButton.setForeground(new Color(0xffffff));
        transactionsDialog.add(searchButton);
        searchButton.setBackground(new Color(0x000000));
        searchButton.setForeground(new Color(0xffffff));

        transactionsDialog.setVisible(true);
    }

    private static void showAddTransactionsDialog(JDialog parentDialog) {
        JDialog addTransactionDialog = new JDialog(parentDialog, "Add Transaction", true);
        addTransactionDialog.setSize(400, 300);
        addTransactionDialog.setLayout(new GridLayout(6, 2));

        JLabel transactionCodeLabel = new JLabel("Transaction Code:");
        JTextField transactionCodeField = new JTextField();
        transactionCodeField.setToolTipText("Enter transaction code (integer)");

        JLabel employeeNameLabel = new JLabel("Employee Name:");
        JTextField employeeNameField = new JTextField();
        employeeNameField.setToolTipText("Enter employee name");

        JLabel currencyNameLabel = new JLabel("Currency Name:");
        JTextField currencyNameField = new JTextField();
        currencyNameField.setToolTipText("Enter currency name");

        JLabel transactionDateLabel = new JLabel("Transaction Date:");
        JTextField transactionDateField = new JTextField();
        transactionDateField.setToolTipText("Enter transaction date (YYYY-MM-DD)");

        JLabel transactionTimeLabel = new JLabel("Transaction Time:");
        JTextField transactionTimeField = new JTextField();
        transactionTimeField.setToolTipText("Enter transaction time (HH:MM:SS)");

        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        addTransactionDialog.add(transactionCodeLabel);
        addTransactionDialog.add(transactionCodeField);
        addTransactionDialog.add(employeeNameLabel);
        addTransactionDialog.add(employeeNameField);
        addTransactionDialog.add(currencyNameLabel);
        addTransactionDialog.add(currencyNameField);
        addTransactionDialog.add(transactionDateLabel);
        addTransactionDialog.add(transactionDateField);
        addTransactionDialog.add(transactionTimeLabel);
        addTransactionDialog.add(transactionTimeField);
        addTransactionDialog.add(cancelButton);
        addTransactionDialog.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int transactionCode = Integer.parseInt(transactionCodeField.getText());
                    String employeeName = employeeNameField.getText();
                    String currencyName = currencyNameField.getText();
                    Date transactionDate = Date.valueOf(transactionDateField.getText());
                    Time transactionTime = Time.valueOf(transactionTimeField.getText());

                    Transactions transaction = new Transactions(transactionCode, employeeName, currencyName, transactionDate, transactionTime);
                    transactionsDAO.insertTransaction(transaction);
                    JOptionPane.showMessageDialog(addTransactionDialog, "Transaction added successfully.");
                    addTransactionDialog.dispose();
                } catch (SQLException | IllegalArgumentException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(addTransactionDialog, "Error adding transaction: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTransactionDialog.dispose();
            }
        });

        addTransactionDialog.setVisible(true);
    }

    private static void showEditTransactionsDialog(JDialog parentDialog) {
        JDialog editTransactionDialog = new JDialog(parentDialog, "Edit Transaction", true);
        editTransactionDialog.setSize(400, 300);
        editTransactionDialog.setLayout(new GridLayout(6, 2));

        JLabel transactionCodeLabel = new JLabel("Transaction Code:");
        JTextField transactionCodeField = new JTextField();
        transactionCodeField.setToolTipText("Enter transaction code (integer)");

        JLabel employeeNameLabel = new JLabel("Employee Name:");
        JTextField employeeNameField = new JTextField();
        employeeNameField.setToolTipText("Enter new employee name");

        JLabel currencyNameLabel = new JLabel("Currency Name:");
        JTextField currencyNameField = new JTextField();
        currencyNameField.setToolTipText("Enter new currency name");

        JLabel transactionDateLabel = new JLabel("Transaction Date:");
        JTextField transactionDateField = new JTextField();
        transactionDateField.setToolTipText("Enter new transaction date (YYYY-MM-DD)");

        JLabel transactionTimeLabel = new JLabel("Transaction Time:");
        JTextField transactionTimeField = new JTextField();
        transactionTimeField.setToolTipText("Enter new transaction time (HH:MM:SS)");

        JButton editButton = new JButton("Edit");
        JButton cancelButton = new JButton("Cancel");

        editTransactionDialog.add(transactionCodeLabel);
        editTransactionDialog.add(transactionCodeField);
        editTransactionDialog.add(employeeNameLabel);
        editTransactionDialog.add(employeeNameField);
        editTransactionDialog.add(currencyNameLabel);
        editTransactionDialog.add(currencyNameField);
        editTransactionDialog.add(transactionDateLabel);
        editTransactionDialog.add(transactionDateField);
        editTransactionDialog.add(transactionTimeLabel);
        editTransactionDialog.add(transactionTimeField);
        editTransactionDialog.add(cancelButton);
        editTransactionDialog.add(editButton);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int transactionCode = Integer.parseInt(transactionCodeField.getText());
                    String employeeName = employeeNameField.getText();
                    String currencyName = currencyNameField.getText();
                    Date transactionDate = Date.valueOf(transactionDateField.getText());
                    Time transactionTime = Time.valueOf(transactionTimeField.getText());

                    Transactions transaction = new Transactions(transactionCode, employeeName, currencyName, transactionDate, transactionTime);
                    transactionsDAO.updateTransaction(transaction);
                    JOptionPane.showMessageDialog(editTransactionDialog, "Transaction updated successfully.");
                    editTransactionDialog.dispose();
                } catch (SQLException | IllegalArgumentException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(editTransactionDialog, "Error updating transaction: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTransactionDialog.dispose();
            }
        });

        editTransactionDialog.setVisible(true);
    }

    private static void showDeleteTransactionsDialog(JDialog parentDialog) {
        JDialog deleteTransactionDialog = new JDialog(parentDialog, "Delete Transaction", true);
        deleteTransactionDialog.setSize(300, 100);
        deleteTransactionDialog.setLayout(new GridLayout(2, 2));

        JLabel transactionCodeLabel = new JLabel("Transaction Code:");
        JTextField transactionCodeField = new JTextField();
        transactionCodeField.setToolTipText("Enter transaction code (integer)");

        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        deleteTransactionDialog.add(transactionCodeLabel);
        deleteTransactionDialog.add(transactionCodeField);
        deleteTransactionDialog.add(cancelButton);
        deleteTransactionDialog.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int transactionCode = Integer.parseInt(transactionCodeField.getText());
                    transactionsDAO.deleteTransaction(transactionCode);
                    JOptionPane.showMessageDialog(deleteTransactionDialog, "Transaction deleted successfully.");
                    deleteTransactionDialog.dispose();
                } catch (SQLException | IllegalArgumentException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(deleteTransactionDialog, "Error deleting transaction: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTransactionDialog.dispose();
            }
        });

        deleteTransactionDialog.setVisible(true);
    }

    private static void showTransactionSearchDialog(JDialog parentDialog) {
        JDialog searchTransactionDialog = new JDialog(parentDialog, "Search Transactions", true);
        searchTransactionDialog.setSize(300, 150);
        searchTransactionDialog.setLayout(new FlowLayout());

        JLabel letterLabel = new JLabel("Enter Number:");
        JTextField letterField = new JTextField(15);
        letterField.setToolTipText("Enter a number to search for transactions");

        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");

        searchTransactionDialog.add(letterLabel);
        searchTransactionDialog.add(letterField);
        searchTransactionDialog.add(cancelButton);
        searchTransactionDialog.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String letter = letterField.getText();

                try {
                    List<Transactions> transactions = transactionsDAO.getTransactionsStartingWith(letter);
                    StringBuilder result = new StringBuilder("Transactions containing '" + letter + "':\n");
                    for (Transactions transactions1 : transactions) {
                        result.append(transactions1.getTransactionCode()).append(" - Transaction Date: ").append(transactions1.getTransactionDate())
                                .append(", Transaction Time: ").append(transactions1.getTransactionTime()).append("\n");
                    }
                    JOptionPane.showMessageDialog(searchTransactionDialog, result.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(searchTransactionDialog, "Error searching transactions.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTransactionDialog.dispose();
            }
        });

        searchTransactionDialog.setVisible(true);
    }

    private static void showEmployeesActions(JFrame adminFrame) {
        JDialog employeesDialog = new JDialog(adminFrame, "Employees Actions", true);
        employeesDialog.setSize(400, 300);
        employeesDialog.setLayout(new GridLayout(4, 1));

        JButton addButton = new JButton("Add Employees");
        JButton editButton = new JButton("Edit Employees");
        JButton deleteButton = new JButton("Delete Employees");
        JButton searchButton = new JButton("Search Employees");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddEmployeesDialog(employeesDialog);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEditEmployeesDialog(employeesDialog);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteEmployeesDialog(employeesDialog);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchEmployeesDialog(employeesDialog);
            }
        });

        employeesDialog.add(addButton);
        addButton.setBackground(new Color(0x000000));
        addButton.setForeground(new Color(0xffffff));
        employeesDialog.add(editButton);
        editButton.setBackground(new Color(0x000000));
        editButton.setForeground(new Color(0xffffff));
        employeesDialog.add(deleteButton);
        deleteButton.setBackground(new Color(0x000000));
        deleteButton.setForeground(new Color(0xffffff));
        employeesDialog.add(searchButton);
        searchButton.setBackground(new Color(0x000000));
        searchButton.setForeground(new Color(0xffffff));

        employeesDialog.setVisible(true);
    }

    private static void showAddEmployeesDialog(JDialog parentDialog) {
        JDialog addEmployeeDialog = new JDialog(parentDialog, "Add Employee", true);
        addEmployeeDialog.setSize(400, 300);
        addEmployeeDialog.setLayout(new GridLayout(4, 1));

        JLabel employeeNameLabel = new JLabel("Employee Name:");
        JTextField employeeNameField = new JTextField();
        employeeNameField.setToolTipText("Enter employee name");

        JLabel dateOfBirthLabel = new JLabel("Date of Birth:");
        JTextField dateOfBirthField = new JTextField();
        dateOfBirthField.setToolTipText("Enter date of birth (yyyy-mm-dd)");

        JLabel positionNameLabel = new JLabel("Position Name:");
        JTextField positionNameField = new JTextField();
        positionNameField.setToolTipText("Enter position name");

        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        addEmployeeDialog.add(employeeNameLabel);
        addEmployeeDialog.add(employeeNameField);
        addEmployeeDialog.add(dateOfBirthLabel);
        addEmployeeDialog.add(dateOfBirthField);
        addEmployeeDialog.add(positionNameLabel);
        addEmployeeDialog.add(positionNameField);
        addEmployeeDialog.add(cancelButton);
        addEmployeeDialog.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String employeeName = employeeNameField.getText();
                    Date dateOfBirth = Date.valueOf(dateOfBirthField.getText());
                    String positionName = positionNameField.getText();

                    Employees employee = new Employees(employeeName, dateOfBirth, positionName);
                    employeesDAO.insertEmployee(employee);
                    JOptionPane.showMessageDialog(addEmployeeDialog, "Employee added successfully.");
                    addEmployeeDialog.dispose();
                } catch (SQLException | IllegalArgumentException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(addEmployeeDialog, "Error adding employee: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployeeDialog.dispose();
            }
        });

        addEmployeeDialog.setVisible(true);
    }

    private static void showEditEmployeesDialog(JDialog parentDialog) {
        JDialog editEmployeeDialog = new JDialog(parentDialog, "Edit Employee", true);
        editEmployeeDialog.setSize(400, 300);
        editEmployeeDialog.setLayout(new GridLayout(4, 2));

        JLabel employeeNameLabel = new JLabel("Employee Name:");
        JTextField employeeNameField = new JTextField();
        employeeNameField.setToolTipText("Enter new employee name");

        JLabel dateOfBirthLabel = new JLabel("Date of Birth:");
        JTextField dateOfBirthField = new JTextField();
        dateOfBirthField.setToolTipText("Enter new date of birth (yyyy-mm-dd)");

        JLabel positionNameLabel = new JLabel("Position Name:");
        JTextField positionNameField = new JTextField();
        positionNameField.setToolTipText("Enter new position name");

        JButton editButton = new JButton("Edit");
        JButton cancelButton = new JButton("Cancel");

        editEmployeeDialog.add(employeeNameLabel);
        editEmployeeDialog.add(employeeNameField);
        editEmployeeDialog.add(dateOfBirthLabel);
        editEmployeeDialog.add(dateOfBirthField);
        editEmployeeDialog.add(positionNameLabel);
        editEmployeeDialog.add(positionNameField);
        editEmployeeDialog.add(cancelButton);
        editEmployeeDialog.add(editButton);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String employeeName = employeeNameField.getText();
                    Date dateOfBirth = Date.valueOf(dateOfBirthField.getText());
                    String positionName = positionNameField.getText();

                    Employees employee = new Employees(employeeName, dateOfBirth, positionName);
                    employeesDAO.updateEmployee(employee);
                    JOptionPane.showMessageDialog(editEmployeeDialog, "Employee updated successfully.");
                    editEmployeeDialog.dispose();
                } catch (SQLException | IllegalArgumentException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(editEmployeeDialog, "Error updating employee: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editEmployeeDialog.dispose();
            }
        });

        editEmployeeDialog.setVisible(true);
    }

    private static void showDeleteEmployeesDialog(JDialog parentDialog) {
        JDialog deleteEmployeeDialog = new JDialog(parentDialog, "Delete Employee", true);
        deleteEmployeeDialog.setSize(300, 100);
        deleteEmployeeDialog.setLayout(new GridLayout(2, 2));

        JLabel employeeNameLabel = new JLabel("Employee Name:");
        JTextField employeeNameField = new JTextField();
        employeeNameField.setToolTipText("Enter employee name");

        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        deleteEmployeeDialog.add(employeeNameLabel);
        deleteEmployeeDialog.add(employeeNameField);
        deleteEmployeeDialog.add(cancelButton);
        deleteEmployeeDialog.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String employeeName = employeeNameField.getText();
                    employeesDAO.deleteEmployee(employeeName);
                    JOptionPane.showMessageDialog(deleteEmployeeDialog, "Employee deleted successfully.");
                    deleteEmployeeDialog.dispose();
                } catch (SQLException | IllegalArgumentException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(deleteEmployeeDialog, "Error deleting employee: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployeeDialog.dispose();
            }
        });

        deleteEmployeeDialog.setVisible(true);
    }
    private static void showSearchEmployeesDialog(JDialog parentDialog) {
        JDialog searchEmployeeDialog = new JDialog(parentDialog, "Search Employee", true);
        searchEmployeeDialog.setSize(300, 100);
        searchEmployeeDialog.setLayout(new FlowLayout());

        JLabel letterLabel = new JLabel("Enter Letter:");
        JTextField letterField = new JTextField(15);
        letterField.setToolTipText("Enter letter to search employees");

        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");

        searchEmployeeDialog.add(letterLabel);
        searchEmployeeDialog.add(letterField);
        searchEmployeeDialog.add(cancelButton);
        searchEmployeeDialog.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String letter = letterField.getText();
                try {
                    List<Employees> employeesList = employeesDAO.searchEmployeesByLetter(letter);
                    StringBuilder result = new StringBuilder("Employees whose name contain letter '" + letter + "':\n");
                    for (Employees employee : employeesList) {
                        result.append("Name: ").append(employee.getEmployeeName()).append(", Date of Birth: ").append(employee.getDateOfBirth()).append(", Position: ").append(employee.getPositionName()).append("\n");
                    }
                    JOptionPane.showMessageDialog(searchEmployeeDialog, result.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(searchEmployeeDialog, "Error searching employees: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployeeDialog.dispose();
            }
        });

        searchEmployeeDialog.setVisible(true);
    }
}