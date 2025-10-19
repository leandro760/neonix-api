package com.neonix.api.ecomerce.controller;

import com.neonix.api.ecomerce.models.Address;
import com.neonix.api.ecomerce.models.User;
import com.neonix.api.ecomerce.repository.AddressRepository;
import com.neonix.api.ecomerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        // Asegúrate de que el usuario asociado exista
        if (address.getUser() == null || address.getUser().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<User> user = userRepository.findById(address.getUser().getId());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // User not found
        }
        address.setUser(user.get());
        address.setCreatedAt(LocalDateTime.now());
        Address savedAddress = addressRepository.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Integer id, @RequestBody Address addressDetails) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            Address existingAddress = address.get();
            existingAddress.setStreet(addressDetails.getStreet());
            existingAddress.setCity(addressDetails.getCity());
            existingAddress.setState(addressDetails.getState());
            existingAddress.setZipCode(addressDetails.getZipCode());
            existingAddress.setCountry(addressDetails.getCountry());
            existingAddress.setAddressType(addressDetails.getAddressType());
            existingAddress.setIsDefault(addressDetails.getIsDefault());
            // No actualizar created_at
            Address updatedAddress = addressRepository.save(existingAddress);
            return ResponseEntity.ok(updatedAddress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public List<Address> getAddressesByUserId(@PathVariable Integer userId) {
        return addressRepository.findByUserId(userId);
    }
}