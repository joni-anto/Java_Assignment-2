# Java_Assignment-2
# Digital Wallet & Transfers

## Project Overview

The **Digital Wallet & Transfers** project is a Java console-based application that simulates basic digital wallet operations. It allows users to create wallets, add money, transfer funds between wallets, make merchant payments, process refunds, and view transaction statements.

The application demonstrates object-oriented programming, modular code organization, transaction validation, and exception handling in Java.

## Student Details

- **Name:** Jonish Antony A
- **Register Number:** 717825I125
- **Project Title:** Digital Wallet & Transfers
- **Programming Language:** Java
- **IDE:** Eclipse

## Features

- **User Management:** Add and manage wallet users.
- **Wallet Creation:** Create a wallet for a user.
- **Top-Up:** Add funds to a wallet.
- **Fund Transfer:** Transfer money between wallets.
- **Merchant Payment:** Simulate payments to merchants.
- **Refund Processing:** Process refunds for eligible transactions.
- **Transaction Statement:** Display transaction history and running balances.
- **Insufficient-Funds Validation:** Prevent transactions that exceed the available balance.
- **Exception Handling:** Handle invalid operations and transaction errors.

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- Eclipse IDE
- Git and GitHub

## Project Structure

```text
DigitalWalletTransfers/
├── src/
│   ├── main/
│   │   └── WalletApplication.java
│   ├── model/
│   │   ├── User.java
│   │   ├── Wallet.java
│   │   ├── StatementEntry.java
│   │   ├── TopUp.java
│   │   ├── Transfer.java
│   │   ├── Payment.java
│   │   └── Refund.java
│   ├── service/
│   │   └── WalletService.java
│   ├── util/
│   │   └── InputUtil.java
│   └── exception/
│       └── WalletException.java
├── README.md
└── .gitignore
```

## How to Run the Project

1. Install the Java Development Kit (JDK) and Eclipse IDE.
2. Clone or download this repository.
3. Open Eclipse and select **File → Import → Existing Projects into Workspace**.
4. Select the downloaded project folder and finish the import.
5. Ensure the project has the correct JDK configured in its Java Build Path.
6. Open `WalletApplication.java` in the `main` package.
7. Right-click the file and select **Run As → Java Application**.
8. Follow the instructions displayed in the Eclipse Console.

## Sample Operations to Test

1. Create a user and wallet.
2. Add funds to the wallet.
3. Transfer funds between two wallets.
4. Make a merchant payment.
5. Process a refund.
6. View the wallet transaction statement.
7. Attempt a transfer or payment greater than the available balance and verify that the application rejects it.

## Learning Outcomes

- Understanding Java classes, objects, and packages.
- Applying object-oriented programming principles.
- Implementing wallet and transaction operations.
- Validating transaction amounts and wallet balances.
- Handling errors and exceptions.
- Organizing a Java project into separate modules.
- Using Eclipse for development and GitHub for source-code management.

## Future Enhancements

- Database integration for persistent user and transaction data.
- User authentication and authorization.
- Improved transaction reporting.
- A graphical user interface or web-based interface.
- Additional security and transaction-audit features.

## Disclaimer

This project is intended for educational purposes only. It simulates digital wallet operations and is not a real banking or payment system.

## Author

**Jonish Antony A**  
Register Number: 717825I125
