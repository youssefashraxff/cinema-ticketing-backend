package com.example.cinematicketingbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.cinematicketingbackend.model.Booking;
import com.example.cinematicketingbackend.model.Customer;
import com.example.cinematicketingbackend.repository.CustomerRepository;

public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService() {
        this.customerRepository = CustomerRepository.getInstance();
    }

    public Customer createCustomer(String name, String email, String phone) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer email cannot be null or empty");
        }

        // Check for duplicate email
        Optional<Customer> existingCustomer = customerRepository.findByEmail(email);
        if (existingCustomer.isPresent()) {
            throw new IllegalArgumentException("Customer with email " + email + " already exists");
        }

        // Generate new customer ID
        List<Customer> allCustomers = customerRepository.findAll();
        int newCustomerId = 1;
        if (!allCustomers.isEmpty()) {
            newCustomerId = allCustomers.stream()
                    .mapToInt(Customer::getCustomerId)
                    .max()
                    .orElse(0) + 1;
        }

        Customer customer = new Customer(newCustomerId, name.trim(), email.trim().toLowerCase(), 
                                       phone != null ? phone.trim() : null);
        
        // Save using repository
        customerRepository.save(customer);
        
        return customer;
    }

    public Customer getCustomer(int customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        return customerOpt.orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByEmail(String email) {
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        return customerOpt.orElse(null);
    }

    public List<Customer> searchCustomersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchName = name.trim().toLowerCase();
        List<Customer> allCustomers = customerRepository.findAll();
        List<Customer> result = new ArrayList<>();
        
        for (Customer customer : allCustomers) {
            if (customer.getName() != null &&
                customer.getName().toLowerCase().contains(searchName)) {
                result.add(customer);
            }
        }

        return result;
    }

    public Customer updateCustomer(int customerId, String name, String email, String phone) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " not found");
        }

        Customer customer = customerOpt.get();
        
        if (name != null && !name.trim().isEmpty()) {
            customer.setName(name.trim());
        }

        if (email != null && !email.trim().isEmpty()) {
            String normalizedEmail = email.trim().toLowerCase();
            // Check if another customer already has this email
            Customer existingCustomer = getCustomerByEmail(normalizedEmail);
            if (existingCustomer != null && existingCustomer.getCustomerId() != customerId) {
                throw new IllegalArgumentException("Another customer with email " + email + " already exists");
            }
            customer.setEmail(normalizedEmail);
        }

        if (phone != null) {
            customer.setPhone(phone.trim());
        }

        // Save updated customer
        customerRepository.save(customer);
        return customer;
    }

    public void deleteCustomer(int customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " not found");
        }

        Customer customer = customerOpt.get();
        
        // Check if customer has active bookings
        if (customer.getBookings() != null && !customer.getBookings().isEmpty()) {
            long activeBookings = customer.getBookings().stream()
                    .filter(booking -> "CONFIRMED".equals(booking.getStatus()))
                    .count();
            if (activeBookings > 0) {
                throw new IllegalStateException("Cannot delete customer with active bookings");
            }
        }

        // Delete customer
        customerRepository.deleteById(customerId);
    }

    public List<Booking> getCustomerBookings(int customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " not found");
        }

        Customer customer = customerOpt.get();
        return new ArrayList<>(customer.getBookings() != null ? customer.getBookings() : new ArrayList<>());
    }

    public void addBookingToCustomer(int customerId, Booking booking) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " not found");
        }

        Customer customer = customerOpt.get();
        customer.addBooking(booking);
        
        // Save updated customer
        customerRepository.save(customer);
    }

    public void removeBookingFromCustomer(int customerId, Booking booking) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " not found");
        }

        Customer customer = customerOpt.get();
        customer.removeBooking(booking);
        
        // Save updated customer
        customerRepository.save(customer);
    }
}