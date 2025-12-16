package com.example.cinematicketingbackend.repository;

import com.example.cinematicketingbackend.model.Customer;
import com.example.cinematicketingbackend.util.FileHandlerManager;
import com.fasterxml.jackson.core.type.TypeReference;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository {
    private static CustomerRepository instance;
    private List<Customer> customers;
    private final FileHandlerManager fileHandler;
    private final Path customersFilePath;

    private CustomerRepository() {
        this.fileHandler = FileHandlerManager.getInstance();
        this.customersFilePath = Paths.get("src/main/resources/data/customers.json");
        loadCustomersFromJson();
    }

    public static synchronized CustomerRepository getInstance() {
        if (instance == null) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    private void loadCustomersFromJson() {
        try {
            customers = fileHandler.readList(customersFilePath, new TypeReference<List<Customer>>() {});
        } catch (Exception e) {
            System.err.println("Error loading customers from file: " + e.getMessage());
            customers = new ArrayList<>();
        }
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }

    public Optional<Customer> findById(int customerId) {
        return customers.stream()
                .filter(customer -> customer.getCustomerId() == customerId)
                .findFirst();
    }

    public Optional<Customer> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        String normalizedEmail = email.trim().toLowerCase();
        return customers.stream()
                .filter(customer -> normalizedEmail.equals(customer.getEmail()))
                .findFirst();
    }

    public boolean existsById(int customerId) {
        return customers.stream().anyMatch(customer -> customer.getCustomerId() == customerId);
    }

    public boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String normalizedEmail = email.trim().toLowerCase();
        return customers.stream().anyMatch(customer -> normalizedEmail.equals(customer.getEmail()));
    }

    public List<Customer> findByNameContaining(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String searchName = name.trim().toLowerCase();
        List<Customer> result = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getName() != null && 
                customer.getName().toLowerCase().contains(searchName)) {
                result.add(customer);
            }
        }
        return result;
    }

    public long count() {
        return customers.size();
    }

    // FIXED: Save a single customer
    public void save(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        // Check for duplicate email (if updating, allow same customer's email)
        Optional<Customer> existingCustomer = findById(customer.getCustomerId());
        if (!existingCustomer.isPresent()) {
            // New customer - check email uniqueness
            if (existsByEmail(customer.getEmail())) {
                throw new IllegalArgumentException("Customer with email " + customer.getEmail() + " already exists");
            }
        } else {
            // Existing customer - check email uniqueness for other customers
            Customer current = existingCustomer.get();
            if (!current.getEmail().equalsIgnoreCase(customer.getEmail())) {
                if (existsByEmail(customer.getEmail())) {
                    throw new IllegalArgumentException("Another customer with email " + customer.getEmail() + " already exists");
                }
            }
        }

        // Remove existing customer with same ID
        customers.removeIf(c -> c.getCustomerId() == customer.getCustomerId());
        
        // Add the customer
        customers.add(customer);
        
        saveAllToFile();
    }

    // FIXED: Save all customers properly
    public void saveAll(List<Customer> customersToSave) {
        if (customersToSave == null || customersToSave.isEmpty()) {
            return;
        }

        // Validate email uniqueness first
        for (Customer customer : customersToSave) {
            // Skip validation for existing customers being updated
            Optional<Customer> existing = findById(customer.getCustomerId());
            if (!existing.isPresent()) {
                // New customer - check email
                if (existsByEmail(customer.getEmail())) {
                    throw new IllegalArgumentException("Customer with email " + customer.getEmail() + " already exists");
                }
            }
        }

        for (Customer customer : customersToSave) {
            // Remove existing customer with same ID
            customers.removeIf(c -> c.getCustomerId() == customer.getCustomerId());
            // Add the customer
            customers.add(customer);
        }
        
        saveAllToFile();
    }

    private void saveAllToFile() {
        try {
            fileHandler.write(customersFilePath, customers);
        } catch (Exception e) {
            System.err.println("Error saving customers to file: " + e.getMessage());
            throw new RuntimeException("Failed to save customers", e);
        }
    }

    public boolean deleteById(int customerId) {
        boolean removed = customers.removeIf(customer -> customer.getCustomerId() == customerId);
        if (removed) {
            saveAllToFile();
        }
        return removed;
    }
}