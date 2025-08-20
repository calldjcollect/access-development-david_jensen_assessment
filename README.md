# Product Inventory Tracker

## Task: Implement Filtering Methods in the Service Layer

### Overview

You are required to enhance the functionality of our Product Inventory Tracker by adding features that allow users to search for products based on specific criteria. Your task involves implementing methods in the service layer (`ProductService`) to enable filtering of product entries by various product attributes.

### Requirements

**Coding Standards:**

- Uphold clean code principles to ensure your code is both readable and maintainable, such as:
  - **Proper naming conventions:** Choose clear and descriptive names for methods and parameters.
  - **Usage of Java’s Optional:** Apply Java’s Optional to gracefully handle potential null values.
  - **Efficient use of functional interfaces and stream operations:** Utilize these features for effective data processing.

### Setup Instructions

**Project Requirements:**

- **Java 21**
- **Maven**

For setting up your development environment, we recommend using **SDKMAN!**, an excellent tool for managing parallel versions of multiple Software Development Kits on most Unix-based systems.

### Installing SDKMAN! and Required Software

1. **Install SDKMAN!:**
   First, open your terminal and install SDKMAN! by running:

   ```bash
   curl -s "https://get.sdkman.io" | bash
   ```

   After installation, start a new terminal session or enter:

   ```bash
   source "$HOME/.sdkman/bin/sdkman-init.sh"
   ```

   You can verify the installation with:

   ```bash
   sdk version
   ```

2. **Install Java 21 and Maven Using SDKMAN!:**
   - **Java:**

     ```bash
     sdk install java 21.0.0-zulu
     ```

   - **Maven:**

     ```bash
     sdk install maven
     ```

Make sure to use Java 21 and Maven for building this project by checking the versions:

```bash
java -version
mvn -version
```

### Setting Up the Project

Once Java and Maven are installed, you can set up your project environment:

1. **Clone the project repository** from GitHub (using the link provided earlier).
1. Create a new branch for your work.
1. Navigate to the project directory where the `pom.xml` file is located.
1. Run `mvn clean install` to build the project and ensure all dependencies are correctly installed.

Upon completion of these steps, your development environment will be ready for you to begin the technical assessment. If you encounter any issues with setting up SDKMAN!, Java, or Maven, feel free to reach out for assistance.

### Workflow Instructions

To successfully complete your assessment for the Product Inventory Tracker, please follow the steps outlined below. These steps are crafted to evaluate your coding, testing, and problem-solving skills using Java and Spring Boot.

1. **Create a new branch:**:
   - Start by creating a new branch in your local repository for this assessment. This will help keep your changes organized and separate from the main codebase. You will create a pull request once you're ready to submit your work.
1. **Review Use Cases:**
   - Begin by examining the three provided use cases. Each describes a specific requirement for the `ProductService` class.
1. **Identify and Fix Existing Bug:**
   - **Important:** There is a subtle bug in the existing codebase that will affect the filtering functionality. As part of this assessment, you must identify and fix this bug before implementing the filtering methods. The bug is related to data transformation and will cause inconsistent behavior in category filtering.
   - Write a test that demonstrates the bug, then fix the issue.
1. **Implement Service Methods:**
   - Code the service methods as specified in `ProductService.java`, adhering to clean code principles and efficient data processing using Java Stream API.
1. **Write Unit Tests:**
   - For each service method, compose a unit test in `ProductServiceTest.java`. Ensure each test robustly verifies the functionality of the respective service method, including edge cases.
1. **Run Unit Tests:**
   - Execute the unit tests to confirm they pass. Focus solely on your testing; running the entire application isn't necessary for this assessment. Make sure your tests are comprehensive and consider various scenarios, including edge cases.
1. **Commit Your Code:**
   - After identifying and fixing the bug, developing the service methods, and confirming that all tests pass, commit your code and push it to your assigned branch for review.
1. **Create a Pull Request:**
   - Once your code is pushed, create a pull request in the repository. In the pull request description, provide a brief overview of the changes made, including the bug fix and the implemented service methods.

### Mock Data Setup

A method creating mock `Product` data and configuring `findAll()` to return this data will be provided in the `ProductServiceTest.java` class. This setup allows you to focus primarily on crafting the business logic and testing.

### Use Cases

1. **Price Range Filter:**
   - **Task:** Implement a method to return a list of `ProductDTO` objects that fall within a specified price range.
   - **Testing:** Ensure your unit test verifies that only products within the price range are returned and handles edge cases like no products within the range.
  
2. **Category Filter:**
   - **Task:** Write a service method to return a list of `ProductDTO` objects matching a specified category.
   - **Testing:** Verify through testing that the method correctly filters products by category, and include a scenario where the specified category is absent in the data.

3. **Availability Filter:**
   - **Task:** Craft a method that returns a list of `ProductDTO` objects based on their availability status.
   - **Testing:** Develop a unit test that ensures products are accurately filtered by their availability status and consider scenarios where products are both available and unavailable.

### Important Notes

**AI-Generated Code:** Please note that portions of this codebase were generated using AI tools. This includes some of the boilerplate code, initial class structures, and basic implementations. As a candidate, you should review all code critically and not assume it follows best practices.

**Feel Free to Improve:** You are encouraged and expected to fix, optimize, or refactor any code you encounter that could be improved. This includes but is not limited to:
- Code style and formatting issues
- Performance optimizations
- Better error handling
- More robust validation
- Improved naming conventions
- Enhanced code organization

Don't limit yourself to just the specific requirements - demonstrating your ability to recognize and improve suboptimal code is part of the assessment.

### Summary

This workflow and the associated tasks are designed to assess your technical skills in Java backend services development, debugging, and effective unit testing. The assessment includes:

- **Bug identification and resolution** - Testing your ability to identify and fix existing issues
- **Service implementation** - Building filtering functionality with clean, efficient code  
- **Comprehensive testing** - Writing robust unit tests that cover various scenarios
- **Code quality improvement** - Recognizing and enhancing any suboptimal code you encounter

Please complete each part of the task as described while maintaining clean and readable code throughout your implementation.
